package br.com.carlos.projeto.conclusao.curso.components;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.carlos.projeto.conclusao.curso.model.common.StudentModel;

@Component
public class XMLConversor
{

	private Logger LOG = LoggerFactory.getLogger(XMLConversor.class);

	
	@Value("${application.xsl.filepath}")
	private String xslFilePath;

	public String converteXML(String path, FileManager fileManager, StudentModel student) throws TransformerConfigurationException, TransformerException, MalformedURLException, IOException
	{
		try
		{
			File xslFile = new File(xslFilePath);

			StreamSource xslSource = new StreamSource(xslFile);

			String[] paths = fileManager.pathBuilder(student);
			InputStream xml = new FileInputStream(new File(paths[1] + "submissao.xml"));

			StreamSource xmlSource = new StreamSource(xml);

			String htmlPath = path.replace("submissao.xml", "") + "submissao.html";
			StreamResult output = new StreamResult(htmlPath);

			Transformer transformer = TransformerFactory.newInstance().newTransformer(xslSource);

			transformer.transform(xmlSource, output);

			return htmlPath;
		} 
		catch (FileNotFoundException ex)
		{
			LOG.error("Error while applying xsl over xml file.", ex);
			return null;
		}
	}

}
