package br.com.carlos.projeto.conclusao.curso.service;

import br.com.carlos.projeto.conclusao.curso.model.common.StudentClassModel;
import br.com.carlos.projeto.conclusao.curso.repository.StudentClassRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic for student classes' crud.
 * @author Carlos H
 */
@Service
public class StudentClassService
{

	@Autowired
	private StudentClassRepository studentClassRepository;

	public List<StudentClassModel> findAll()
	{

		List<StudentClassModel> turmas = (List<StudentClassModel>) studentClassRepository.findAll();
		return turmas;
	}

	public Optional<StudentClassModel> findById(Long id) throws EntityNotFoundException
	{
		return studentClassRepository.findById(id);
	}

	public List<StudentClassModel> findByCourseId(Long cursoId) throws EntityNotFoundException
	{
		return studentClassRepository.findByCourseId(cursoId);
	}

	public List<StudentClassModel> findByCourseId(long id)
	{
		return studentClassRepository.findByCourseId(id);
	}

	public StudentClassModel save(StudentClassModel turma)
	{
		return studentClassRepository.save(turma);
	}

	public void delete(Long id)
	{
		studentClassRepository.deleteById(id);
	}
}
