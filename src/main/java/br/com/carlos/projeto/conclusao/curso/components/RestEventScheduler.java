package br.com.carlos.projeto.conclusao.curso.components;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.carlos.projeto.conclusao.curso.ProjetoConclusaoCursoApplication;
import br.com.carlos.projeto.conclusao.curso.model.StudentModel;
import br.com.carlos.projeto.conclusao.curso.model.SubmissionModel;
import br.com.carlos.projeto.conclusao.curso.model.SubmissionsQueue;
import br.com.carlos.projeto.conclusao.curso.model.integrationmodel.JsonGETStatusResponse;
import br.com.carlos.projeto.conclusao.curso.model.integrationmodel.JsonPOSTResponse;
import br.com.carlos.projeto.conclusao.curso.repository.SubmissionsQueueRepository;
import br.com.carlos.projeto.conclusao.curso.repository.SubmissionRepository;

/**
 * Component responsible for sending an HTTP request to OTS api in a scheduled
 * time.
 *
 * @author Carlos H
 */
@Component
@EnableScheduling
public class RestEventScheduler
{

	@Autowired
	private SubmissionsQueueRepository filaSubmissoesRepository;

	@Autowired
	private SubmissionRepository submissaoRepository;

	@Autowired
	private XMLConversor conversor;

	@Autowired
	private FileManager manipuladorDeArquivos;

	private static final String TIME_ZONE = "America/Sao_Paulo";

	private static final String EMAIL = "carloshsjbv@gmail.com";

	private static final String OTS_URL = "http://pkp-xml-demo.lib.sfu.ca/api/job/";

	private static final String OTS_AUTH_TOKEN = "9f392da47a631ac2fe5ec2440e2ba4c36bca1d38";

	private static final String CONTEXT_PATH = System.getProperty("user.dir");

	private static final Logger LOG = LoggerFactory.getLogger(ProjetoConclusaoCursoApplication.class);

	/**
	 * Scheduler method
	 *
	 * @throws java.lang.Exception
	 * @throws br.com.carlos.projeto.conclusao.curso.exceptions.TratadorRequisicoesRest
	 */
	@Scheduled(cron = "0 0 1 * * *", zone = TIME_ZONE)
	public void enviaArtigo() throws Exception
	{

		String postURL = OTS_URL + "submit";

		RestTemplate restTemplateToPost = new RestTemplate();

		try
		{
			Iterable<SubmissionsQueue> fila = filaSubmissoesRepository.findAllBySent(false);

			for (SubmissionsQueue itemFila : fila)
			{

				SubmissionModel submissaoParaEnviar = itemFila.getSubmissionModel();

				if (submissaoParaEnviar != null)
				{

					System.out.println("Begining /POST request!");

					HttpEntity<MultiValueMap<String, Object>> requestEntity = buildRequest(submissaoParaEnviar);

					ResponseEntity<?> postForObject = restTemplateToPost.postForEntity(postURL, requestEntity,
							String.class);

					ObjectMapper mapper = new ObjectMapper();

					JsonPOSTResponse response = mapper.readValue((String) postForObject.getBody(),
							JsonPOSTResponse.class);

					if (postForObject.getBody().toString().contains("success"))
					{
						// Atualização do campo OTSID
						submissaoParaEnviar.setOtsId(Long.parseLong(response.getId()));
						submissaoParaEnviar.setAuthorized(true);

						itemFila.setSent(true);

						System.out.println(postForObject);
						System.out.println(response);

						filaSubmissoesRepository.save(itemFila);
						submissaoRepository.save(submissaoParaEnviar);
					}

				}
			}
		} catch (IOException | RestClientException e)
		{
			throw new Exception(e);
		}

	}

	/**
	 * Build http request before sending to OTS API.
	 * 
	 * @param submissionToSend
	 * @return
	 * @throws IOException
	 */
	private HttpEntity<MultiValueMap<String, Object>> buildRequest(SubmissionModel submissionToSend)
			throws IOException
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

		File arquivo = new File(submissionToSend.getOriginalDocumentPath());
		byte[] fileContent = Files.readAllBytes(arquivo.toPath());

		File jsonMetadata = new File(CONTEXT_PATH + System.getProperty("file.separator") + "UNIFAE-METADATA.json");
		
		byte[] fileMetadata = Files.readAllBytes(jsonMetadata.toPath());

		// Montagem do corpo da requisição
		body.add("email", EMAIL);
		body.add("access_token", OTS_AUTH_TOKEN);
		body.add("fileName", arquivo.getName());
		body.add("citationStyleHash", "3f0f7fede090f24cc71b7281073996be");
		body.add("fileContent", fileContent);

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		return requestEntity;
	}

	/**
	 * Scheduler built to reconver processed files from OTS api
	 *
	 * There will be triggered three requests with the following indexes (according
	 * to OTS documentation).
	 *
	 * 8 - PDF file.
	 *
	 * 11 - EPUB File.
	 *
	 * 20 - XML file.
	 */
	@Scheduled(cron = "0 0 1 * * *", zone = TIME_ZONE)
	public void getRequest()
	{

		String getURL = OTS_URL + "status?email=" + EMAIL + "&access_token=" + OTS_AUTH_TOKEN;

		RestTemplate restTemplateToGet = new RestTemplate();

		try
		{
			LOG.debug("Starting file recovery from database...");

			Iterable<SubmissionsQueue> sentFilesQueue = filaSubmissoesRepository.findAllBySent(true);

			for (SubmissionsQueue queueItem : sentFilesQueue)
			{

				StudentModel aluno = queueItem.getSubmissionModel().getStudent();

				getURL += "&id=" + queueItem.getSubmissionModel().getOtsId();

				ResponseEntity<?> getObject = restTemplateToGet.getForEntity(getURL, JsonGETStatusResponse.class);

				JsonGETStatusResponse statusReponse = (JsonGETStatusResponse) getObject.getBody();

				if (statusReponse.getJobStatus().equals("2"))
				{

					getURL = getURL.replace("status", "retrieve");

					String pdfFilePath = processFile(executeRequest(restTemplateToGet, getURL, "8"), "pdf", aluno);

					String epubFilePath = processFile(executeRequest(restTemplateToGet, getURL, "11"), "epub", aluno);

					String xmlFilePath = processFile(executeRequest(restTemplateToGet, getURL, "20"), "xml", aluno);

					String htmlFilePath = conversor.converteXML(xmlFilePath, manipuladorDeArquivos, aluno);

					SubmissionModel submission = queueItem.getSubmissionModel();

					submission.setPdfPath(pdfFilePath);
					submission.setEpubPath(epubFilePath);
					submission.setXmlPath(xmlFilePath);
					submission.setHtmlPath(htmlFilePath);

					submissaoRepository.save(submission);

					filaSubmissoesRepository.delete(queueItem);
				}

			}

		} catch (RestClientException e)
		{
			LOG.error(e.getMessage());
		} catch (IOException e)
		{
			LOG.error(e.getMessage());
		} catch (TransformerException ex)
		{
			LOG.error(ex.getMessage());
		}
	}

	/**
	 * Execute GET request to OTS API.
	 * 
	 * @param restTemplate - Manages http request
	 * @param url          - Request URL.
	 * @param              idArquivoObtido- File id to be requested to OTS api.
	 * 
	 * @return = Byte array from file comming from OTS api.
	 * 
	 * @throws IOException
	 * @throws RestClientException
	 */
	protected byte[] executeRequest(RestTemplate restTemplate, String url, String fileId)
			throws IOException, RestClientException
	{

		ByteArrayHttpMessageConverter byteArrayMessageConverter = new ByteArrayHttpMessageConverter();

		List<MediaType> allowedMediaTypes = new ArrayList<>();

		List<MediaType> fileTypes = new ArrayList<>();
		fileTypes.add(new MediaType("application", "pdf"));
		fileTypes.add(new MediaType("application", "xml"));
		fileTypes.add(new MediaType("application", "epub+zip"));

		allowedMediaTypes.addAll(fileTypes);

		byteArrayMessageConverter.setSupportedMediaTypes(allowedMediaTypes);

		List<HttpMessageConverter<?>> httpMessageConverter = new ArrayList<>();
		httpMessageConverter.add(byteArrayMessageConverter);

		restTemplate.setMessageConverters(httpMessageConverter);

		HttpHeaders httpHeaders = new HttpHeaders();

		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

		ResponseEntity<byte[]> response = restTemplate.exchange(conversionUrlBuilder(url, fileId), HttpMethod.GET,
				entity, byte[].class);

		if (response.hasBody())
		{
			return response.getBody();
		}

		return null;
	}

	/**
	 * Conversion URL builder
	 *
	 * @param url
	 * @param conversionStage
	 * @return
	 */
	private String conversionUrlBuilder(String url, String conversionStage)
	{
		return url.concat("&conversionStage=").concat(conversionStage);
	}

	/**
	 * Aims to get files from file system before sending to OTS api.
	 *
	 * @param byteFile       - File ByteArray
	 * @param fileExtensinon - File extension
	 * @param student          = Student entity which will help to build file path
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private String processFile(byte[] byteFile, String fileExtensinon, StudentModel student)
			throws FileNotFoundException, IOException
	{

		InputStream byteArrayInputStream = new ByteArrayInputStream(byteFile);

		String[] paths = manipuladorDeArquivos.pathBuilder(student);

		manipuladorDeArquivos.writeFile(new FileOutputStream(new File(paths[0] + "submissao." + fileExtensinon)),
				byteArrayInputStream);

		return paths[0] + "submissao." + fileExtensinon;
	}

}
