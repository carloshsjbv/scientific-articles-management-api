package br.com.carlos.projeto.conclusao.curso.components;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import br.com.carlos.projeto.conclusao.curso.model.StudentModel;

@Component
public class XMLConversor
{

	public String converteXML(String path, FileManager manipuladorDeArquivos, StudentModel aluno)
			throws TransformerConfigurationException, TransformerException, MalformedURLException, IOException
	{

		try
		{
			File xslFile = new File("transform.xsl");

			StreamSource xslSource = new StreamSource(xslFile);

			String[] paths = manipuladorDeArquivos.pathBuilder(aluno);
			InputStream xml = new FileInputStream(new File(paths[1] + "submissao.xml"));

			StreamSource xmlSource = new StreamSource(xml);

			String htmlPath = path.replace("submissao.xml", "") + "submissao.html";
			StreamResult saida = new StreamResult(htmlPath);

			Transformer transformer = TransformerFactory.newInstance().newTransformer(xslSource);

			transformer.transform(xmlSource, saida);

			return htmlPath;
		} catch (FileNotFoundException ex)
		{
			Logger.getLogger(XMLConversor.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

}
