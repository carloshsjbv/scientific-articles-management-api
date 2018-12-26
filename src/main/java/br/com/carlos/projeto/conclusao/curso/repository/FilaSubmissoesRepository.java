package br.com.carlos.projeto.conclusao.curso.repository;

import br.com.carlos.projeto.conclusao.curso.model.entity.FilaSubmissoes;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface que implementa JpaRepository e é responsável pela comunicação com o
 * banco de dados, proporcionando assim, a criação de um CRUD de FilaSubmissoes.
 *
 * @author Carlos H
 */
@Transactional
public interface FilaSubmissoesRepository extends JpaRepository<FilaSubmissoes, Long> {
    
    List<FilaSubmissoes> findAllByEnviado(boolean enviado);

}
