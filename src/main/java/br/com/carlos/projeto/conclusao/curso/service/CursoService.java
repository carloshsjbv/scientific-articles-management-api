package br.com.carlos.projeto.conclusao.curso.service;

import br.com.carlos.projeto.conclusao.curso.model.dtos.CursoDTO;
import br.com.carlos.projeto.conclusao.curso.model.entity.CursoEntity;
import br.com.carlos.projeto.conclusao.curso.repository.CursoRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Service responsável por acessar via injeção de dependência todos os recursos
 * da interface CursoRepository.
 *
 * @author Carlos H
 */
@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public List<CursoEntity> findAll() {

        List<CursoEntity> cursos = (List<CursoEntity>) cursoRepository.findAll();
        return cursos;
    }

    public CursoEntity findById(Long id) throws EntityNotFoundException {
        return cursoRepository.findById(id).get();
    }

    public CursoEntity findByAcronimo(String acronimo) throws EntityNotFoundException {
        return cursoRepository.findByAcronimoIgnoreCase(acronimo);
    }

    public CursoEntity salvaCurso(CursoDTO curso) {
        return cursoRepository.save(curso.transformaParaObjeto());
    }

    public CursoEntity alteraCurso(CursoDTO curso) {
        CursoEntity oldCurso = findByAcronimo(curso.getAcronimo());

        oldCurso.setNome(curso.getNome());
        oldCurso.setArea(curso.getArea());
        oldCurso.setAcronimo(curso.getAcronimo());

        return cursoRepository.save(oldCurso);
    }

    public void removeCurso(Long id) {
        cursoRepository.deleteById(id);
    }

}
