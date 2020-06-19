package br.com.carlos.projeto.conclusao.curso.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.carlos.projeto.conclusao.curso.model.common.UserModel;

/**
 * Interface que implementa JpaRepository e é responsável pela comunicação com o
 * banco de dados, proporcionando assim, a criação de um CRUD de UsuarioEntity.
 *
 * @author Carlos H
 */
@Transactional
public interface UserRepository extends JpaRepository<UserModel, String> {

    public UserModel findByUsername(String username);

}
