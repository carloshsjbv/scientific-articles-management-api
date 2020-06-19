package br.com.carlos.projeto.conclusao.curso.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.carlos.projeto.conclusao.curso.model.common.CourseModel;

@Transactional
public interface CourseRepository extends JpaRepository<CourseModel, Long>
{

	public CourseModel findByAcronymIgnoreCase(String acronimo);

}
