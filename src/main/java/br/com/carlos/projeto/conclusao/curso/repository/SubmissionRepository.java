package br.com.carlos.projeto.conclusao.curso.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.carlos.projeto.conclusao.curso.model.CourseModel;
import br.com.carlos.projeto.conclusao.curso.model.SubmissionModel;

@Transactional
public interface SubmissionRepository extends JpaRepository<SubmissionModel, Long>
{

	public List<SubmissionModel> findAllByStudent_StudentClass_Course(CourseModel curso);

	public List<SubmissionModel> findAllByStudent_StudentClass_InitialYear(int anoInicial);

}
