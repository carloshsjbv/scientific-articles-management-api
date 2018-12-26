package br.com.carlos.projeto.conclusao.curso.service;

import br.com.carlos.projeto.conclusao.curso.model.entity.TurmaEntity;
import br.com.carlos.projeto.conclusao.curso.repository.TurmaRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service responsável por acessar via injeção de dependência todos os recursos
 * da interface TurmaRepository.
 *
 * @author Carlos H
 */
@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    public List<TurmaEntity> findAll() {

        List<TurmaEntity> turmas = (List<TurmaEntity>) turmaRepository.findAll();
        return turmas;
    }

    public Optional<TurmaEntity> findById(Long id) throws EntityNotFoundException {
        return turmaRepository.findById(id);
    }

    public List<TurmaEntity> findByCursoId(Long cursoId) throws EntityNotFoundException {
        return turmaRepository.findByCursoId(cursoId);
    }

    public List<TurmaEntity> findByCursoId(long id) {
        return turmaRepository.findByCursoId(id);
    }

    public TurmaEntity save(TurmaEntity turma) {
        return turmaRepository.save(turma);
    }

    public void delete(Long id) {
        turmaRepository.deleteById(id);
    }
}
