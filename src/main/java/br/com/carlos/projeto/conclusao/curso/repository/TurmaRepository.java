package br.com.carlos.projeto.conclusao.curso.repository;

import br.com.carlos.projeto.conclusao.curso.model.entity.TurmaEntity;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface que implementa JpaRepository e é responsável pela comunicação com o
 * banco de dados, proporcionando assim, a criação de um CRUD de TurmaEntity
 *
 * @author Carlos H
 */
@Transactional
public interface TurmaRepository extends JpaRepository<TurmaEntity, Long> {

    public List<TurmaEntity> findByCursoId(Long cursoId);

}
