package br.com.carlos.projeto.conclusao.curso.controller;

import br.com.carlos.projeto.conclusao.curso.model.StudentClassModel;
import br.com.carlos.projeto.conclusao.curso.model.CourseModel;
import br.com.carlos.projeto.conclusao.curso.model.dtos.CourseDTO;
import br.com.carlos.projeto.conclusao.curso.service.CourseService;
import br.com.carlos.projeto.conclusao.curso.service.StudentClassService;

import java.net.URI;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Controlador Cursos
 *
 * @author Carlos H
 */
@RestController
@RequestMapping(path = "/cursos")
public class CourseController
{

	@Autowired
	private CourseService courseService;

	@Autowired
	private StudentClassService studentClassService;

	@GetMapping
	public List<CourseModel> findAll()
	{
		return courseService.findAll();
	}

	@GetMapping(value = "/{id}")
	public CourseModel listarCursosPorId(@PathVariable("id") Long id) throws EntityNotFoundException
	{
		try
		{
			return courseService.findById(id);
		} catch (EntityNotFoundException e)
		{
			throw new EntityNotFoundException(e.getMessage() + id.toString());
		}
	}

	@GetMapping(value = "/studentclass/{acronym}")
	public List<StudentClassModel> findStudentClassByCourse(@PathVariable("acronimo") String acronym) throws Exception
	{

		try
		{
			CourseModel curso = courseService.findByAcronym(acronym.toUpperCase());
			List<StudentClassModel> turma;
			try
			{
				turma = studentClassService.findByCourseId(curso.getId());
			} catch (Exception e)
			{
				throw new Exception(e);
			}
			return turma;
		} catch (Exception e)
		{
			throw new Exception();
		}
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid CourseDTO course, UriComponentsBuilder uriBuilder)
	{

		CourseModel savedCourse = courseService.save(course);

		URI uri = uriBuilder.path("/course/{id}").buildAndExpand(savedCourse.getId()).toUri();
		return ResponseEntity.created(uri).body(savedCourse);

	}

	@PutMapping
	public ResponseEntity<?> alteraCurso(@RequestBody @Valid CourseDTO curso)
	{
		return new ResponseEntity<>(courseService.alteraCurso(curso), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public void removeCurso(@PathVariable Long id)
	{
		courseService.removeCurso(id);
	}

}
