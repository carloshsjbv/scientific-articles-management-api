package br.com.carlos.projeto.conclusao.curso.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.carlos.projeto.conclusao.curso.model.StudentClassModel;

@Transactional
public interface StudentClassRepository extends JpaRepository<StudentClassModel, Long>
{

	public List<StudentClassModel> findByCourseId(Long cursoId);

}
