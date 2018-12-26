    package br.com.carlos.projeto.conclusao.curso.config.security;

import br.com.carlos.projeto.conclusao.curso.exceptions.UserNotFoundException;
import br.com.carlos.projeto.conclusao.curso.model.entity.UsuarioEntity;
import br.com.carlos.projeto.conclusao.curso.repository.UsuarioRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

/**
 *
 * Service que verifica se o usuário existe.
 *
 * A busca deve ser feita, e deve ser retornado um usuário com seus devidso
 * dados e autorizações.
 *
 * @author Carlos H
 */
@Repository
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByUsername(username);

        if (usuario == null) {
            throw new UserNotFoundException("Usuário nao encontrado");
        }
        return new User(usuario.getUsername(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());
    }

}
