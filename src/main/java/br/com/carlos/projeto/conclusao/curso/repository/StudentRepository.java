package br.com.carlos.projeto.conclusao.curso.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.carlos.projeto.conclusao.curso.model.StudentModel;

@Transactional
public interface StudentRepository extends JpaRepository<StudentModel, Long>
{

	public StudentModel findByUsername(String username);

}
