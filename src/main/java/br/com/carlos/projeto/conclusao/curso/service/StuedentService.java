package br.com.carlos.projeto.conclusao.curso.service;

import br.com.carlos.projeto.conclusao.curso.model.StudentModel;
import br.com.carlos.projeto.conclusao.curso.repository.StudentRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic for student's crud.
 *
 * @author Carlos H
 */
@Service
public class StuedentService
{

	@Autowired
	private StudentRepository studentRepository;

	public long countAll()
	{
		return studentRepository.count();
	}

	public List<StudentModel> findAll()
	{
		return (List<StudentModel>) studentRepository.findAll();
	}

	public StudentModel findById(Long id) throws EntityNotFoundException
	{
		return studentRepository.findById(id).get();
	}

	public StudentModel findByEmail(String email)
	{
		return studentRepository.findByUsername(email);
	}

	public StudentModel save(StudentModel aluno)
	{
		return studentRepository.save(aluno);
	}

	public void remove(Long id)
	{
		studentRepository.deleteById(id);
	}

}
