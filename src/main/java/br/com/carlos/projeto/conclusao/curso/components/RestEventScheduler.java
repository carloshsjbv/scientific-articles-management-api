package br.com.carlos.projeto.conclusao.curso.components;

import br.com.carlos.projeto.conclusao.curso.ProjetoConclusaoCursoApplication;
import br.com.carlos.projeto.conclusao.curso.model.entity.AlunoEntity;
import br.com.carlos.projeto.conclusao.curso.model.entity.FilaSubmissoes;
import br.com.carlos.projeto.conclusao.curso.model.entity.SubmissaoEntity;
import br.com.carlos.projeto.conclusao.curso.repository.FilaSubmissoesRepository;
import br.com.carlos.projeto.conclusao.curso.repository.SubmissaoRepository;
import br.com.carlos.projeto.conclusao.curso.model.integrationmodel.JsonGETStatusResponse;
import br.com.carlos.projeto.conclusao.curso.model.integrationmodel.JsonPOSTResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.xml.transform.TransformerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * Componente responsável por ler a fila de submissões, e enviar os arquivos no
 * formato PDF para a aplicação OTS.
 *
 * @author Carlos H
 */
@Component
@EnableScheduling
public class RestEventScheduler {

    @Autowired
    private FilaSubmissoesRepository filaSubmissoesRepository;

    @Autowired
    private SubmissaoRepository submissaoRepository;

    @Autowired
    private XMLConversor conversor;

    @Autowired
    private ManipuladorDeArquivos manipuladorDeArquivos;

    private static final String TIME_ZONE = "America/Sao_Paulo";

    private static final String EMAIL = "carloshsjbv@gmail.com";

    private static final String OTS_URL = "http://pkp-xml-demo.lib.sfu.ca/api/job/";

    private static final String OTS_AUTH_TOKEN = "9f392da47a631ac2fe5ec2440e2ba4c36bca1d38";

    private static final String CONTEXT_PATH = System.getProperty("user.dir");

    private static final Logger LOG = LoggerFactory.getLogger(ProjetoConclusaoCursoApplication.class);

    /**
     * Método de agendamento de execução de rotina de envio à plataforma OTS.
     *
     * @throws java.lang.Exception
     * @throws
     * br.com.carlos.projeto.conclusao.curso.exceptions.TratadorRequisicoesRest
     */
    //@Scheduled(cron = "0 0 1 * * *", zone = TIME_ZONE) Utilizar esta anotação quando tudo estiver pronta
    //@Scheduled(fixedDelay = 1000)
    public void enviaArtigo() throws Exception {

        // Define path final para post
        String postURL = OTS_URL + "submit";

        RestTemplate restTemplateToPost = new RestTemplate();

        try {
            // Busca submissões que estão na tabela de fila de processamento.
            Iterable<FilaSubmissoes> fila = filaSubmissoesRepository.findAllByEnviado(false);

            // Para cada item da fila, é recuperada a submissão.
            for (FilaSubmissoes itemFila : fila) {

                SubmissaoEntity submissaoParaEnviar = itemFila.getSubmissaoEntity();

                if (submissaoParaEnviar != null) {

                    System.out.println("Begining /POST request!");

                    // Headers da requisição
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                    // Corpo da requisição
                    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

                    // Obtém path do banco de dados, e transforma em bytes
                    File arquivo = new File(submissaoParaEnviar.getPathDocumentoOriginal());
                    byte[] fileContent = Files.readAllBytes(arquivo.toPath());

                    // TODO: analisar viabilidade
                    File jsonMetadata = new File(CONTEXT_PATH + System.getProperty("file.separator") + "UNIFAE-METADATA.json");
                    byte[] fileMetadata = Files.readAllBytes(jsonMetadata.toPath());

                    // Montagem do corpo da requisição
                    body.add("email", EMAIL);
                    body.add("access_token", OTS_AUTH_TOKEN);
                    body.add("fileName", arquivo.getName());
                    body.add("citationStyleHash", "3f0f7fede090f24cc71b7281073996be");
                    body.add("fileContent", fileContent);
                    //body.add("fileMetadata", fileMetadata);

                    // Adiciona os headers e body à requisição
                    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

                    // Executa requisição
                    ResponseEntity<?> postForObject = restTemplateToPost.postForEntity(postURL, requestEntity, String.class);

                    ObjectMapper mapper = new ObjectMapper();
                    JsonPOSTResponse response = mapper.readValue((String) postForObject.getBody(), JsonPOSTResponse.class);

                    if (postForObject.getBody().toString().contains("success")) {
                        // Atualização do campo OTSID
                        submissaoParaEnviar.setOtsId(Long.parseLong(response.getId()));
                        submissaoParaEnviar.setAutorizado(true);

                        itemFila.setEnviado(true);

                        System.out.println(postForObject);
                        System.out.println(response);

                        // Salva campo atualizado
                        filaSubmissoesRepository.save(itemFila);
                        // Salva campo atualizado
                        submissaoRepository.save(submissaoParaEnviar);
                    }
                }
            }
        } catch (IOException | RestClientException e) {
            throw new Exception(e);
        }

    }

    /**
     * Requisição get para obter os arquivos submetidos à plataforma OTS.
     *
     * O Scheduler se encarregará de dispará-las todos os dias, em um horário
     * pré-determinado.
     *
     * Serão disparadas três requisições com os seguintes índices:
     *
     * 8 - Arquivo PDF.
     *
     * 11 - Arquivo EPUB.
     *
     * 20 - Arquivo XML.
     *
     * Se a submissão for recuperada, o conteúdo do diretório deve ser
     * atualizado com a adição dos arquivos no formato XML, PDF e EPUB.
     *
     * Os códigos utilizados para a obtenção dos determinados formatos de
     * arquivo foram obtidos na documentação da plataforma OTS, e podem ser
     * consultados através da URL: https://github.com/pkp/ots
     *
     *
     */
    //@Scheduled(cron = "0 0 1 * * *", zone = TIME_ZONE) Utilizar esta anotação quando tudo estiver pronta
    //@Scheduled(fixedDelay = 1000)
    public void getRequest() {

        String getURL = OTS_URL + "status?email=" + EMAIL + "&access_token=" + OTS_AUTH_TOKEN;

        RestTemplate restTemplateToGet = new RestTemplate();

        try {
            System.out.println("Iniciando recuperação de arquivos da base.");

            Iterable<FilaSubmissoes> itensFila = filaSubmissoesRepository.findAllByEnviado(true);

            for (FilaSubmissoes item : itensFila) {

                AlunoEntity aluno = item.getSubmissaoEntity().getAluno();

                getURL += "&id=" + item.getSubmissaoEntity().getOtsId();

                ResponseEntity<?> getObject = restTemplateToGet.getForEntity(getURL, JsonGETStatusResponse.class);

                JsonGETStatusResponse statusReponse = (JsonGETStatusResponse) getObject.getBody();

                if (statusReponse.getJobStatus().equals("2")) {

                    getURL = getURL.replace("status", "retrieve");

                    // Obtém, processa e salva arquivo PDF
                    String pdfFilePath = processaArquivo(executaRequisicao(restTemplateToGet, getURL, "8"), "pdf", aluno);

                    // Obtém, processa e salva arquivo Epub
                    String epubFilePath = processaArquivo(executaRequisicao(restTemplateToGet, getURL, "11"), "epub", aluno);

                    // Obtém processa e salva arquivo XML
                    String xmlFilePath = processaArquivo(executaRequisicao(restTemplateToGet, getURL, "20"), "xml", aluno);

                    String htmlFilePath = conversor.converteXML(xmlFilePath, manipuladorDeArquivos, aluno);

                    SubmissaoEntity submissao = item.getSubmissaoEntity();

                    submissao.setPathPDF(pdfFilePath);
                    submissao.setPathEpub(epubFilePath);
                    submissao.setPathXML(xmlFilePath);
                    submissao.setPathHTML(htmlFilePath);

                    // Arquivo processado, atualiza banco de dados
                    submissaoRepository.save(submissao);

                    // Arquivo processado, remove da fila
                    filaSubmissoesRepository.delete(item);
                }

            }

        } catch (RestClientException e) {
            java.util.logging.Logger.getLogger(RestEventScheduler.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            java.util.logging.Logger.getLogger(IOException.class.getName()).log(Level.SEVERE, null, e);
        } catch (TransformerException ex) {
            java.util.logging.Logger.getLogger(RestEventScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que gerencia configurações relacionadas aos tipos de arquivos
     * permitidos na requisição (List&ge;MediaType&le; fileTypes) e as utiliza
     * na execução da requisição GET enviada a plataforma OTS.
     *
     * @param restTemplateToGet = RestTemplate que gerencia a conexão via
     * protocolo HTTP.
     * @param getURL = URL utilizada na requisição.
     * @param idArquivoObtido = Id referente ao tipo do arquivo que será
     * requisitado à plataforma OTS
     * @return = Array de bytes correspodente ao arquivo recebido na requisição.
     * @throws IOException
     * @throws RestClientException
     */
    protected byte[] executaRequisicao(RestTemplate restTemplateToGet, String getURL, String idArquivoObtido) throws IOException, RestClientException {

        ByteArrayHttpMessageConverter byteArrayConversorDeMensagens = new ByteArrayHttpMessageConverter();

        List<MediaType> mediaTypesPermitidos = new ArrayList<>();

        List<MediaType> fileTypes = new ArrayList<>();
        fileTypes.add(new MediaType("application", "pdf"));
        fileTypes.add(new MediaType("application", "xml"));
        fileTypes.add(new MediaType("application", "epub+zip"));

        mediaTypesPermitidos.addAll(fileTypes);

        byteArrayConversorDeMensagens.setSupportedMediaTypes(mediaTypesPermitidos);

        List<HttpMessageConverter<?>> conversorDeMensagensHttp = new ArrayList<>();
        conversorDeMensagensHttp.add(byteArrayConversorDeMensagens);

        restTemplateToGet.setMessageConverters(conversorDeMensagensHttp);

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<byte[]> response = restTemplateToGet.exchange(
                buildConversionStageURL(getURL, idArquivoObtido),
                HttpMethod.GET,
                entity,
                byte[].class
        );

        // Verifica se há um corpo para a resposta, executa rotina de salvamento
        if (response.hasBody()) {
            return response.getBody();
        }

        return null;

    }

    /**
     * Construtor da URL a ser utilizada na requisição GET.
     *
     * @param url
     * @param conversionStage
     * @return
     */
    private String buildConversionStageURL(String url, String conversionStage) {
        return url.concat("&conversionStage=").concat(conversionStage);
    }

    /**
     * Rotina destinada ao envio do arquivo obtido da requisição GET ao sistema
     * de arquivos.
     *
     * @param byteFile = Array de bytes referente ao arquivo recebido na
     * requisição
     * @param fileExtensinon = Extensão do arquivo a ser processado.
     * @param aluno = Referência ao aluno, que será utilizada para compor o PATH
     * do diretório onde o arquivo será armazenado.
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    private String processaArquivo(byte[] byteFile, String fileExtensinon, AlunoEntity aluno) throws FileNotFoundException, IOException {

        InputStream byteArrayInputStream = new ByteArrayInputStream(byteFile);

        String[] paths = manipuladorDeArquivos.getPaths(aluno);

        manipuladorDeArquivos.escreveArquivo(new FileOutputStream(new File(paths[0] + "submissao." + fileExtensinon)), byteArrayInputStream);

        return paths[0] + "submissao." + fileExtensinon;
    }

}
