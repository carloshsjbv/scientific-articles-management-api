package br.com.carlos.projeto.conclusao.curso.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.carlos.projeto.conclusao.curso.model.common.CourseModel;
import br.com.carlos.projeto.conclusao.curso.model.common.StudentClassModel;
import br.com.carlos.projeto.conclusao.curso.model.dtos.ClassDTO;
import br.com.carlos.projeto.conclusao.curso.service.CourseService;
import br.com.carlos.projeto.conclusao.curso.service.StudentClassService;

/**
 * Student Class Controller
 *
 * @author Carlos H
 */
@RestController
@RequestMapping("/studentclass")
public class StudentClassController
{

	@Autowired
	private StudentClassService studentClassService;

	@Autowired
	private CourseService courseService;

	@GetMapping
	public List<StudentClassModel> findAll()
	{

		return studentClassService.findAll();
	}

	@GetMapping(value = "/{id}")
	public StudentClassModel findById(@PathVariable("id") Long id)
	{
		return studentClassService.findById(id).get();
	}

	@GetMapping(value = "curso/{cursoId}")
	public ResponseEntity<List<StudentClassModel>> findStudentClassByCourseId(@PathVariable("courseId") Long cursoId)
	{
		List<StudentClassModel> studentClass = studentClassService.findByCourseId(cursoId);

		if (studentClass != null && !studentClass.isEmpty())
		{
			return ResponseEntity.ok(studentClass);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<StudentClassModel> save(@RequestBody @Valid ClassDTO studentClassForm,
			UriComponentsBuilder uriBuilder)
	{
		CourseModel course = courseService.findById(studentClassForm.getCourse());

		StudentClassModel studentClass = new StudentClassModel();

		studentClass.setInitialYear(studentClassForm.getInitialYear());
		studentClass.setCourse(course);

		StudentClassModel savedStudentClass = studentClassService.save(studentClass);

		URI uri = uriBuilder.path("/studentclass/{id}").buildAndExpand(savedStudentClass.getId()).toUri();
		return ResponseEntity.created(uri).body(savedStudentClass);

	}

	@DeleteMapping
	public void delete(@RequestBody @Valid StudentClassModel studentClass)
	{
		studentClassService.delete(studentClass.getId());
	}
}
