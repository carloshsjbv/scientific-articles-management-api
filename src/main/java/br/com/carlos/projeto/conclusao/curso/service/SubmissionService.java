package br.com.carlos.projeto.conclusao.curso.service;

import br.com.carlos.projeto.conclusao.curso.model.common.StudentModel;
import br.com.carlos.projeto.conclusao.curso.model.common.SubmissionModel;
import br.com.carlos.projeto.conclusao.curso.model.common.SubmissionsQueue;
import br.com.carlos.projeto.conclusao.curso.model.common.UserModel;
import br.com.carlos.projeto.conclusao.curso.model.dtos.SubmissionDTO;
import br.com.carlos.projeto.conclusao.curso.repository.CourseRepository;
import br.com.carlos.projeto.conclusao.curso.repository.SubmissionsQueueRepository;
import br.com.carlos.projeto.conclusao.curso.repository.SubmissionRepository;
import br.com.carlos.projeto.conclusao.curso.components.FileManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic for submissions's crud.
 * 
 * @author Carlos H
 */
@Service
public class SubmissionService
{

	@Autowired
	private SubmissionRepository submissionRepository;

	@Autowired
	private SubmissionsQueueRepository submissionsQueueRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private FileManager fileSaver;

	public long countAll()
	{
		return submissionRepository.count();
	}

	public SubmissionModel findById(Long id) throws EntityNotFoundException, IOException
	{
		SubmissionModel submission = submissionRepository.findById(id).get();

		submission.setHtmlPath(System.getProperty("user.dir") + System.getProperty("file.separator")
				+ submission.getHtmlPath() + submission.getHtmlPath());

		return submission;
	}

	public List<SubmissionModel> findAll() throws EntityNotFoundException
	{
		List<SubmissionModel> submissoes = handlePath(submissionRepository.findAll());
		return submissoes;
	}

	public List<SubmissionModel> findAllByCourse(String acronym) throws EntityNotFoundException
	{
		List<SubmissionModel> submissoes = handlePath(submissionRepository
				.findAllByStudent_StudentClass_Course(courseRepository.findByAcronymIgnoreCase(acronym)));
		return submissoes;
	}

	public List<SubmissionModel> findAllByTurmaAnoCurso(int initialYear) throws EntityNotFoundException
	{
		return (List<SubmissionModel>) submissionRepository.findAllByStudent_StudentClass_InitialYear(initialYear);
	}

	public List<SubmissionModel> findAllByAcronym() throws EntityNotFoundException
	{
		return (List<SubmissionModel>) submissionRepository.findAll();
	}

	public SubmissionModel save(SubmissionDTO submissao) throws SQLException, IOException
	{

		UserModel user = userService.findByEmail(submissao.getEmail());

		StudentModel student = studentService.findById(user.getId());

		if (student != null)
		{

			if (student.getStudentClass() != null)
			{

				try
				{
					String[] paths = fileSaver.pathBuilder(student);

					String path = fileSaver.saveFile(paths, submissao.getFile());

					SubmissionModel submissionModel = new SubmissionModel();

					submissionModel.setArticleTitle(submissao.getArticleTitle());
					submissionModel.setArticleAbstract(submissao.getArticleAbstract());
					submissionModel.setKeywords(submissao.getKeywords());

					submissionModel.setStudent(student);
					submissionModel.setAuthorized(true);
					submissionModel.setSubmissionInstant(Calendar.getInstance(Locale.getDefault()));
					submissionModel.setOtsId(0);
					submissionModel.setOriginalDocumentPath(path);

					try
					{

						submissionRepository.save(submissionModel);

						insertIntoSubmissionsQueue(submissionModel);

						return submissionModel;
					} catch (Exception e)
					{
						throw new SQLException(e);
					}

				} catch (SQLException e)
				{
					throw e;
				}
			}

		}

		return null;
	}

	private void insertIntoSubmissionsQueue(SubmissionModel submissionModel)
	{
		SubmissionsQueue fila = new SubmissionsQueue();

		fila.setSubmissionModel(submissionModel);
		fila.setSent(false);

		submissionsQueueRepository.save(fila);
	}

	private List<SubmissionModel> handlePath(List<SubmissionModel> list)
	{
		List<SubmissionModel> lista = new ArrayList<>();

		String userDir = System.getProperty("user.dir");

		for (SubmissionModel submissaoEntity : list)
		{
			submissaoEntity.setOriginalDocumentPath(getPath(userDir, submissaoEntity.getOriginalDocumentPath()));

			submissaoEntity.setPdfPath(
					submissaoEntity.getPdfPath() != null ? getPath(userDir, submissaoEntity.getPdfPath()) : null);
			submissaoEntity.setXmlPath(
					submissaoEntity.getXmlPath() != null ? getPath(userDir, submissaoEntity.getXmlPath()) : null);
			submissaoEntity.setEpubPath(
					submissaoEntity.getEpubPath() != null ? getPath(userDir, submissaoEntity.getEpubPath()) : null);
			submissaoEntity.setHtmlPath(
					submissaoEntity.getHtmlPath() != null ? getPath(userDir, submissaoEntity.getHtmlPath()) : null);
			lista.add(submissaoEntity);
		}
		return lista;

	}

	private String getPath(String userDir, String path)
	{
		return userDir + System.getProperty("file.separator") + path;
	}
}
