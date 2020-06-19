package br.com.carlos.projeto.conclusao.curso;

import br.com.carlos.projeto.conclusao.curso.model.common.CourseModel;
import br.com.carlos.projeto.conclusao.curso.repository.CourseRepository;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * Testes unitários da entidade curso.
 * 
 * @author Carlos H
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CursoRepositoryTest
{

	@Autowired
	private CourseRepository cursoRepository;

	@Rule
	public ExpectedException retorno = ExpectedException.none();

	@Test
	public void criacaoDevePersistirDados()
	{
		CourseModel curso = new CourseModel(0, "Engenharia de Software", "ES", "Tecnologia");

		this.cursoRepository.save(curso);

		Assertions.assertThat(curso.getId()).isNotNull();
		Assertions.assertThat(curso.getName()).isEqualTo("Engenharia de Software");
		Assertions.assertThat(curso.getAcronym()).isEqualTo("ES");
		Assertions.assertThat(curso.getArea()).isEqualTo("Tecnologia");
	}

	@Test
	public void deleteDeveRemoverCurso()
	{
		CourseModel curso = new CourseModel(0, "Engenharia de Software", "ES", "Tecnologia");
		this.cursoRepository.save(curso);
		this.cursoRepository.delete(curso);

		Assertions.assertThat(this.cursoRepository.findById(curso.getId())).isEmpty();
	}

	@Test
	public void updateDeveAtualizarPersistirCurso()
	{
		CourseModel curso = new CourseModel(0, "Engenharia de Software", "ES", "Tecnologia");
		this.cursoRepository.save(curso);

		System.out.println(curso.toString());

		curso.setName("Engenharia de Computação");
		curso.setAcronym("EC");

		curso = this.cursoRepository.save(curso);
		curso = this.cursoRepository.findById(curso.getId()).get();

		System.out.println(curso.toString());

		Assertions.assertThat(curso.getName()).isEqualTo("Engenharia de Computação");
		Assertions.assertThat(curso.getAcronym()).isEqualTo("EC");
	}

	@Test
	public void deveRetornarAcronimoCurso()
	{
		CourseModel curso = new CourseModel(0, "Engenharia de Software", "ES", "Tecnologia");
		this.cursoRepository.save(curso);

		System.out.println(curso.toString());

		curso = this.cursoRepository.findByAcronymIgnoreCase(curso.getAcronym());

		System.out.println(curso.toString());

		Assertions.assertThat(curso.getAcronym()).isEqualTo("ES");
	}

}
