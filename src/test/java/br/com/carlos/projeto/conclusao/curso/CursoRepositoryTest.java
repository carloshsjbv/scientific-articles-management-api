package br.com.carlos.projeto.conclusao.curso;

import br.com.carlos.projeto.conclusao.curso.model.entity.CursoEntity;
import br.com.carlos.projeto.conclusao.curso.repository.CursoRepository;
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
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Rule
    public ExpectedException retorno = ExpectedException.none();

    @Test
    public void criacaoDevePersistirDados() {
        CursoEntity curso = new CursoEntity(0, "Engenharia de Software", "ES", "Tecnologia");

        this.cursoRepository.save(curso);

        Assertions.assertThat(curso.getId()).isNotNull();
        Assertions.assertThat(curso.getNome()).isEqualTo("Engenharia de Software");
        Assertions.assertThat(curso.getAcronimo()).isEqualTo("ES");
        Assertions.assertThat(curso.getArea()).isEqualTo("Tecnologia");
    }

    @Test
    public void deleteDeveRemoverCurso() {
        CursoEntity curso = new CursoEntity(0, "Engenharia de Software", "ES", "Tecnologia");
        this.cursoRepository.save(curso);
        this.cursoRepository.delete(curso);

        Assertions.assertThat(this.cursoRepository.findById(curso.getId())).isEmpty();
    }

    @Test
    public void updateDeveAtualizarPersistirCurso() {
        CursoEntity curso = new CursoEntity(0, "Engenharia de Software", "ES", "Tecnologia");
        this.cursoRepository.save(curso);

        System.out.println(curso.toString());

        curso.setNome("Engenharia de Computação");
        curso.setAcronimo("EC");

        curso = this.cursoRepository.save(curso);
        curso = this.cursoRepository.findById(curso.getId()).get();

        System.out.println(curso.toString());

        Assertions.assertThat(curso.getNome()).isEqualTo("Engenharia de Computação");
        Assertions.assertThat(curso.getAcronimo()).isEqualTo("EC");
    }

    @Test
    public void deveRetornarAcronimoCurso() {
        CursoEntity curso = new CursoEntity(0, "Engenharia de Software", "ES", "Tecnologia");
        this.cursoRepository.save(curso);

        System.out.println(curso.toString());

        curso = this.cursoRepository.findByAcronimoIgnoreCase(curso.getAcronimo());

        System.out.println(curso.toString());

        Assertions.assertThat(curso.getAcronimo()).isEqualTo("ES");
    }

    // @Test
    // public void createDeveRetornarConstraintViolationExceptionQuandoNomeNull() {
    //     retorno.expect(ConstraintViolationException.class);
    //     //retorno.expectMessage("Não pode ser null");

    //     CursoEntity curso = new CursoEntity(0, null, "ES", "Tecnologia");
    //     this.cursoRepository.save(curso);

    // }

}
