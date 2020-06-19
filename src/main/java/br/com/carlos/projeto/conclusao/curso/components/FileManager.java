package br.com.carlos.projeto.conclusao.curso.components;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.carlos.projeto.conclusao.curso.model.common.StudentModel;

/**
 * Class responsible for saving submissions file into file system
 *
 * @author Carlos H
 */
@Component
public class FileManager
{

	private static final String CONTEXT_PATH = System.getProperty("user.dir");

	@Value("${application.institution}")
	private String institutionName;

	@Value("${application.abbrev}")
	private String applicationAbbrev;

	/**
	 * Responsible for building an array of paths which were build according
	 * student's attributes
	 *
	 * @param student - student responsible for the submission
	 * @return = abbreviated path to be saved as a reference at submissions table
	 *         (DB).
	 *
	 * @throws java.io.FileNotFoundException
	 */
	public String[] pathBuilder(StudentModel student) throws FileNotFoundException, IOException
	{

		String[] paths = new String[2];

		String fileSeparator = System.getProperty("file.separator");

		String absolutePath = CONTEXT_PATH + fileSeparator;

		StringBuilder abbrevPath = new StringBuilder();

		abbrevPath
				.append(institutionName)
				.append(fileSeparator).append(applicationAbbrev)
				.append(fileSeparator)
				.append(student.getStudentClass().getCourse().getAcronym())
				.append(fileSeparator)
				.append(student.getStudentClass().getInitialYear())
				.append(fileSeparator)
				.append(student.getScholarIdentification())
				.append(fileSeparator);

		paths[0] = abbrevPath.toString();

		paths[1] = absolutePath.concat(fileSeparator).concat(paths[0]);

		File path = new File(paths[1]);
		if (!path.exists())
		{
			path.mkdirs();
		}

		return paths;
	}

	/**
	 * Responsible for saving a submitted file into file system.
	 *
	 * @param paths
	 * @param file
	 *
	 * @return = abbreviated path which will be save at database as a reference.
	 *
	 * @throws java.io.FileNotFoundException
	 */
	public String saveFile(String[] paths, MultipartFile file) throws FileNotFoundException, IOException
	{
		writeFile(new FileOutputStream(paths[1] + file.getOriginalFilename()), file.getInputStream());

		return paths[0] + file.getOriginalFilename();
	}

	public void writeFile(FileOutputStream fileOut, InputStream inputStream) throws IOException, FileNotFoundException
	{
		BufferedInputStream in = new BufferedInputStream(inputStream);

		BufferedOutputStream out = new BufferedOutputStream(fileOut);

		IOUtils.copy(in, out);

		/*
		 * byte[] buffer = new byte[20000]; int len = 0;
		 * 
		 * while ((len = in.read(buffer)) > 0) { out.write(buffer, 0, len); }
		 */

		in.close();
		out.close();

	}

}
