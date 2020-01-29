package br.com.carlos.projeto.conclusao.curso.controller;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.carlos.projeto.conclusao.curso.model.CourseModel;
import br.com.carlos.projeto.conclusao.curso.model.StudentClassModel;
import br.com.carlos.projeto.conclusao.curso.model.dtos.CourseDTO;
import br.com.carlos.projeto.conclusao.curso.service.CourseService;
import br.com.carlos.projeto.conclusao.curso.service.StudentClassService;

/**
 * Course controller
 *
 * @author Carlos H
 */
@RestController
@RequestMapping(path = "/courses")
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
	public ResponseEntity<CourseModel> findById(@PathVariable("id") Long id) throws EntityNotFoundException
	{
		CourseModel courseModel = courseService.findById(id);

		if (courseModel != null)
		{
			return ResponseEntity.ok(courseModel);
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/studentclass/{acronym}")
	public List<StudentClassModel> findStudentClassByCourse(@PathVariable("acronym") String acronym) throws Exception
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
	public ResponseEntity<CourseModel> save(@RequestBody @Valid CourseDTO course, UriComponentsBuilder uriBuilder)
	{

		CourseModel savedCourse = courseService.save(course);

		URI uri = uriBuilder.path("/course/{id}").buildAndExpand(savedCourse.getId()).toUri();
		return ResponseEntity.created(uri).body(savedCourse);

	}

	@PutMapping
	@Transactional
	public ResponseEntity<CourseModel> alteraCurso(@RequestBody @Valid CourseDTO course)
	{
		CourseModel updatedCourse = courseService.alteraCurso(course);

		if (updatedCourse != null)
		{
			return ResponseEntity.ok(updatedCourse);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> removeCurso(@PathVariable Long id)
	{
		CourseModel courseModel = courseService.findById(id);

		if (courseModel != null)
		{
			courseService.removeCurso(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();

	}

}
