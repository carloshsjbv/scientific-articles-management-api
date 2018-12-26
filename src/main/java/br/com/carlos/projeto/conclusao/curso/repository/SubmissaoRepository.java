package br.com.carlos.projeto.conclusao.curso.repository;

import br.com.carlos.projeto.conclusao.curso.model.entity.CursoEntity;
import br.com.carlos.projeto.conclusao.curso.model.entity.SubmissaoEntity;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface que implementa JpaRepository e é responsável pela comunicação com o
 * banco de dados, proporcionando assim, a criação de um CRUD de SubmissaoEntity
 *
 * @author Carlos H
 */
@Transactional
public interface SubmissaoRepository extends JpaRepository<SubmissaoEntity, Long> {

    public List<SubmissaoEntity> findAllByAluno_Turma_Curso(CursoEntity curso);

    public List<SubmissaoEntity> findAllByAluno_TurmaAnoInicial(int anoInicial);

}
