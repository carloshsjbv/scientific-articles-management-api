package br.com.carlos.projeto.conclusao.curso.service;

import br.com.carlos.projeto.conclusao.curso.model.entity.UsuarioEntity;
import br.com.carlos.projeto.conclusao.curso.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service de usuários.
 *
 * @author Carlos H
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioEntity findByEmail(String email) {
        return usuarioRepository.findByUsername(email);
    }

}
