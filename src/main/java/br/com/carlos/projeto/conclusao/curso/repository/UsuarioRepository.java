package br.com.carlos.projeto.conclusao.curso.repository;

import br.com.carlos.projeto.conclusao.curso.model.entity.UsuarioEntity;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface que implementa JpaRepository e é responsável pela comunicação com o
 * banco de dados, proporcionando assim, a criação de um CRUD de UsuarioEntity.
 *
 * @author Carlos H
 */
@Transactional
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, String> {

    public UsuarioEntity findByUsername(String username);

}
