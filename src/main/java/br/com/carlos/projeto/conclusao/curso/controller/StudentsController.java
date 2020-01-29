package br.com.carlos.projeto.conclusao.curso.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.carlos.projeto.conclusao.curso.model.Role;
import br.com.carlos.projeto.conclusao.curso.model.StudentClassModel;
import br.com.carlos.projeto.conclusao.curso.model.StudentModel;
import br.com.carlos.projeto.conclusao.curso.model.dtos.StudentDTO;
import br.com.carlos.projeto.conclusao.curso.service.StudentClassService;
import br.com.carlos.projeto.conclusao.curso.service.StuedentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/students")
@Api(value = "Students Controller")
public class StudentsController
{

	@Autowired
	private StuedentService studentService;

	@Autowired
	private StudentClassService studentClassService;

	@ApiOperation(value = "Get students count.")
	@GetMapping(value = "/count")
	public long countAll()
	{
		return studentService.countAll();
	}

	@ApiOperation(value = "Get students list.")
	@GetMapping
	public List<StudentModel> findAll()
	{
		return studentService.findAll();
	}

	@ApiOperation(value = "Get student by id.")
	@GetMapping(value = "/{id}")
	public ResponseEntity<StudentModel> findById(@PathVariable("id") Long id)
	{
		StudentModel studentModel = studentService.findById(id);

		if (studentModel != null)
		{
			return ResponseEntity.ok(studentModel);
		}

		return ResponseEntity.notFound().build();

	}

	@ApiOperation(value = "Save new student.")
	@PostMapping
	public ResponseEntity<StudentDTO> save(@RequestBody @Valid StudentDTO studentForm, UriComponentsBuilder uriBuilder)
	{

		StudentClassModel studentClass = studentClassService.findById(studentForm.getStudentClass()).get();

		if (studentClass != null)
		{
			ArrayList<Role> roles = new ArrayList<>();
			Role role = new Role("ROLE_USER");
			roles.add(role);

			StudentModel student = new StudentModel();
			student.setName(studentForm.getNome());
			student.setScholarIdentification(studentForm.getRa());
			student.setStudentClass(studentClass);
			student.setUsername(studentForm.getEmail());
			student.setPassword(new BCryptPasswordEncoder().encode(studentForm.getSenha()));
			student.setAuthority(roles);

			StudentModel savedStudent = studentService.save(student);

			URI uri = uriBuilder.path("/courses/{id}").buildAndExpand(savedStudent.getId()).toUri();
			return ResponseEntity.created(uri).body(studentForm);
		}

		return ResponseEntity.badRequest().build();
	}

}
