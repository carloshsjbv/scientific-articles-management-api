package br.com.carlos.projeto.conclusao.curso.components;

import br.com.carlos.projeto.conclusao.curso.model.entity.AlunoEntity;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Classe responsável pela gravação das submissões no sistema de arquivos.
 *
 * @author Carlos H
 */
@Component
public class ManipuladorDeArquivos {

    /**
     * Obtenção do diretório home padrão, independente do sistema operacional.
     */
    private static final String CONTEXT_PATH = System.getProperty("user.dir");

    /**
     * Obtenção o nome da instituição, definido na seção APPLICATION KEYS, do
     * arquivo application.properties.
     */
    @Value("${app.institution}")
    private String APP_INSTITUTION;

    /**
     * Obtenção a abreviação do sistema, definido na seção APPLICATION KEYS, do
     * arquivo application.properties.
     */
    @Value("${app.abbrev}")
    private String APP_ABBREV;

    /**
     * Método responsável retornar um array de paths.
     *
     * @param aluno = aluno que compõe o path
     * @return = path abreviado do diretório de arquivamento da submissão, que
     * será posteriormente armazenado no banco de dados.
     *
     * @throws java.io.FileNotFoundException
     */
    public String[] getPaths(AlunoEntity aluno) throws FileNotFoundException, IOException {

        String[] paths = new String[2];

        // Separador de diretorios, independente de arquitetura de S.O
        String systemFileSeparator = System.getProperty("file.separator");

        String absolutePath = CONTEXT_PATH + systemFileSeparator;

        // Composição do path abreviado
        paths[0] = APP_INSTITUTION + systemFileSeparator
                + APP_ABBREV + systemFileSeparator
                + aluno.getTurma().getCurso().getAcronimo() + systemFileSeparator
                + aluno.getTurma().getAnoInicial() + systemFileSeparator
                + aluno.getRa() + systemFileSeparator;

        paths[1] = absolutePath.concat(systemFileSeparator).concat(paths[0]);

        File path = new File(paths[1]);
        if (!path.exists()) {
            path.mkdirs();
        }

        return paths;
    }

    /**
     * Método responsável por gravar o arquivo vindo da submissão, no sistema de
     * arqivos do sitema operacional.
     *
     * @param paths
     * @param file = arquivo a ser salvo
     *
     *
     * @return = path abreviado do diretório de arquivamento da submissão, que
     * será posteriormente armazenado no banco de dados.
     *
     * @throws java.io.FileNotFoundException
     */
    public String salvaArquivo(String[] paths, MultipartFile file) throws FileNotFoundException, IOException {
        escreveArquivo(new FileOutputStream(paths[1] + file.getOriginalFilename()), file.getInputStream());
        return paths[0] + file.getOriginalFilename();
    }

    public void escreveArquivo(FileOutputStream fileOut, InputStream inputStream) throws IOException, FileNotFoundException {
        // bufferedStream do arquivo de entrada
        BufferedInputStream in = new BufferedInputStream(inputStream);

        // Buffer utilizado para a gravação
        BufferedOutputStream out = new BufferedOutputStream(fileOut);

        byte[] buffer = new byte[20000];
        int len = 0;

        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }

        in.close();
        out.close();

    }

}
