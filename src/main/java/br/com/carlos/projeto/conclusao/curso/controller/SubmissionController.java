package br.com.carlos.projeto.conclusao.curso.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carlos.projeto.conclusao.curso.model.SubmissionModel;
import br.com.carlos.projeto.conclusao.curso.model.dtos.SubmissionDTO;
import br.com.carlos.projeto.conclusao.curso.service.SubmissionService;

/**
 * Submission Controler
 *
 * @author Carlos H
 */
@RestController
@RequestMapping(path = "/submissoes")
public class SubmissionController
{

	@Autowired
	private SubmissionService submissionService;

	@GetMapping(value = "/count")
	public long count()
	{
		return submissionService.countAll();
	}

	@GetMapping
	public List<SubmissionModel> findAll()
	{
		return submissionService.findAll();
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<SubmissionModel> findSubmissionsById(@PathVariable("id") Long id)
			throws EntityNotFoundException, IOException
	{
		SubmissionModel submissionModel = submissionService.findById(id);

		if (submissionModel != null)
		{
			return ResponseEntity.ok(submissionModel);
		}

		return ResponseEntity.notFound().build();

	}

	@GetMapping(value = "list/{acronimo}")
	public List<SubmissionModel> findByStudentClassAndCourse(@PathVariable("acronimo") String acronimo)
	{
		List<SubmissionModel> submissoes = (List<SubmissionModel>) submissionService.findAllByCourse(acronimo);
		return submissoes.isEmpty() ? null : submissoes;
	}

	@GetMapping(value = "list/{acronimo}/{anoInicial}")
	public List<SubmissionModel> listarSubmissoesPorAlunoTurmaAnoInicial(@PathVariable("acronimo") String acronimo,
			@PathVariable("anoInicial") int anoInicial)
	{
		List<SubmissionModel> submissoes = (List<SubmissionModel>) submissionService.findAllByTurmaAnoCurso(anoInicial);
		return submissoes.isEmpty() ? null : submissoes;
	}

	@PostMapping(headers = "content-type=multipart/form-data", consumes =
	{ MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> save(@ModelAttribute @Valid SubmissionDTO submissao) throws Exception
	{
		try
		{
			submissionService.save(submissao);
			return (ResponseEntity<?>) ResponseEntity.ok(submissao);
		} catch (IOException | SQLException e)
		{
			throw new Exception(e);
		}
	}
}
