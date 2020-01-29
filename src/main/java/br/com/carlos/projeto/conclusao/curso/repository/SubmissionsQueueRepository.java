package br.com.carlos.projeto.conclusao.curso.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.carlos.projeto.conclusao.curso.model.SubmissionsQueue;

/**
 * Interface que implementa JpaRepository e é responsável pela comunicação com o
 * banco de dados, proporcionando assim, a criação de um CRUD de FilaSubmissoes.
 *
 * @author Carlos H
 */
@Transactional
public interface SubmissionsQueueRepository extends JpaRepository<SubmissionsQueue, Long> {
    
    List<SubmissionsQueue> findAllBySent(boolean enviado);

}
