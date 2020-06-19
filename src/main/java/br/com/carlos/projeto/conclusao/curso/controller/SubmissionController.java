package br.com.carlos.projeto.conclusao.curso.controller;

import java.io.IOException;
import java.net.URI;
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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.carlos.projeto.conclusao.curso.model.common.SubmissionModel;
import br.com.carlos.projeto.conclusao.curso.model.dtos.SubmissionDTO;
import br.com.carlos.projeto.conclusao.curso.service.SubmissionService;

/**
 * Submission Controler
 *
 * @author Carlos H
 */
@RestController
@RequestMapping(path = "/submissions")
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

	@GetMapping(value = "/list/{acronym}")
	public ResponseEntity<List<SubmissionModel>> findByStudentClassAndCourse(@PathVariable("acronym") String acronym)
	{
		List<SubmissionModel> submissions = submissionService.findAllByCourse(acronym);

		if (submissions != null && !submissions.isEmpty())
		{
			return ResponseEntity.ok(submissions);
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/list/{acronym}/{initialYear}")
	public ResponseEntity<List<SubmissionModel>> listarSubmissoesPorAlunoTurmaAnoInicial(
			@PathVariable("acronym") String acronym, @PathVariable("initialYear") int initialYear)
	{
		List<SubmissionModel> submissions = submissionService.findAllByTurmaAnoCurso(initialYear);

		if (submissions != null && !submissions.isEmpty())
		{
			return ResponseEntity.ok(submissions);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping(headers = "content-type=multipart/form-data", consumes =
	{ MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<SubmissionModel> save(@ModelAttribute @Valid SubmissionDTO submission,
			UriComponentsBuilder uriBuilder) throws Exception
	{
		SubmissionModel savedSubmission = submissionService.save(submission);

		URI uri = uriBuilder.path("/submission/{id}").buildAndExpand(savedSubmission.getId()).toUri();
		return ResponseEntity.created(uri).body(savedSubmission);

	}
}
