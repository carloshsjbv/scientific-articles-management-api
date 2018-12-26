package br.com.carlos.projeto.conclusao.curso.components;

import br.com.carlos.projeto.conclusao.curso.model.entity.AlunoEntity;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Carlos H
 */
@Component
public class XMLConversor {

    public String converteXML(String path, ManipuladorDeArquivos manipuladorDeArquivos, AlunoEntity aluno) throws TransformerConfigurationException, TransformerException, MalformedURLException, IOException {

        try {
            String fileContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\"><xsl:output method=\"html\"/><xsl:template match=\"body\"><div class=\"container\"><xsl:apply-templates/></div></xsl:template><xsl:template match=\"back\"></xsl:template><xsl:template match=\"front\"></xsl:template><xsl:template match=\"aff\"><p><xsl:apply-templates/></p></xsl:template><xsl:template match=\"pub-date\"><p>Publicado em: \n"
                    + "			<xsl:apply-templates/></p></xsl:template><xsl:template match=\"abstract\"><h3>RESUMO</h3><p><xsl:apply-templates/></p><hr></hr></xsl:template><xsl:template match=\"sec\"><section><xsl:apply-templates/></section></xsl:template><xsl:template match=\"sec/title\"><h3><xsl:apply-templates/></h3></xsl:template><xsl:template match=\"sec/sec/title\"><h4><xsl:apply-templates/></h4></xsl:template><xsl:template match=\"sec/sec/sec/title\"><h5><xsl:apply-templates/></h5></xsl:template><xsl:template match=\"p\"><p><xsl:apply-templates/></p></xsl:template><xsl:template match=\"fig\"></xsl:template><xsl:template match=\"xref\"><i><xsl:apply-templates/></i></xsl:template></xsl:stylesheet>";

            byte[] bytes = fileContent.getBytes();
            File xsl = new File("xml-conversor.xsl");
            FileWriter fr = new FileWriter(xsl);
            for (byte aByte : bytes) {
                fr.write(aByte);
            }
            fr.close();

            StreamSource xslSource = new StreamSource(xsl);

            String[] paths = manipuladorDeArquivos.getPaths(aluno);
            InputStream xml = new FileInputStream(new File(paths[1] + "submissao.xml"));

            StreamSource xmlSource = new StreamSource(xml);

            String htmlPath = path.replace("submissao.xml", "") + "submissao.html";
            StreamResult saida = new StreamResult(htmlPath);

            Transformer transformer = TransformerFactory.newInstance().newTransformer(xslSource);

            transformer.transform(xmlSource, saida);

            return htmlPath;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XMLConversor.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
