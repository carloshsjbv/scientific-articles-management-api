package br.com.carlos.projeto.conclusao.curso.repository;

import br.com.carlos.projeto.conclusao.curso.model.entity.AlunoEntity;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface que implementa JpaRepository e é responsável pela comunicação com o
 * banco de dados, proporcionando assim, a criação de um CRUD de AlunoEntity
 *
 * @author Carlos H
 */
@Transactional
public interface AlunoRepository extends JpaRepository<AlunoEntity, Long> {

    public AlunoEntity findByUsername(String username);

}
