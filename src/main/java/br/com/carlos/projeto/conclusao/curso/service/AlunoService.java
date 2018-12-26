package br.com.carlos.projeto.conclusao.curso.service;

import br.com.carlos.projeto.conclusao.curso.model.entity.AlunoEntity;
import br.com.carlos.projeto.conclusao.curso.repository.AlunoRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Service responsável por acessar via injeção de dependência todos os realunos
 * da interface AlunoRepository.
 *
 * @author Carlos H
 */
@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public long countAll() {
        return alunoRepository.count();
    }

    public List<AlunoEntity> findAll() {
        return (List<AlunoEntity>) alunoRepository.findAll();
    }

    public AlunoEntity findById(Long id) throws EntityNotFoundException {
        return alunoRepository.findById(id).get();
    }

    public AlunoEntity findByEmail(String email) {
        return alunoRepository.findByUsername(email);
    }

    public AlunoEntity save(AlunoEntity aluno) {
        return alunoRepository.save(aluno);
    }

    public void remove(Long id) {
        alunoRepository.deleteById(id);
    }

}
