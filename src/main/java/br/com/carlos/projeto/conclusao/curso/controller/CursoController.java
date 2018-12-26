package br.com.carlos.projeto.conclusao.curso.controller;

import br.com.carlos.projeto.conclusao.curso.model.dtos.CursoDTO;
import br.com.carlos.projeto.conclusao.curso.model.entity.CursoEntity;
import br.com.carlos.projeto.conclusao.curso.model.entity.TurmaEntity;
import br.com.carlos.projeto.conclusao.curso.service.CursoService;
import br.com.carlos.projeto.conclusao.curso.service.TurmaService;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador Cursos
 *
 * @author Carlos H
 */
@RestController
@RequestMapping(path = "/ri-api/v1/cursos")
public class CursoController {

    // Injeção de dependência dos Services
    @Autowired
    private CursoService cursoService;

    @Autowired
    private TurmaService turmaService;

    @GetMapping(value = "/list")
    public List<CursoEntity> listarCursos() {
        List<CursoEntity> cursos = (List<CursoEntity>) cursoService.findAll();
        return cursos;
    }

    @GetMapping(value = "/{id}")
    public CursoEntity listarCursosPorId(@PathVariable("id") Long id) throws EntityNotFoundException {
        try {
            return cursoService.findById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage() + id.toString());
        }
    }

    /**
     * Este método é utilizado para trazer os anos das turmas dado o acronimo de
     * um curso.
     *
     * @param acronimo
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/turmas/{acronimo}")
    public List<TurmaEntity> listarTurmasPorCurso(@PathVariable("acronimo") String acronimo) throws Exception {

        try {
            CursoEntity curso = cursoService.findByAcronimo(acronimo.toUpperCase());
            List<TurmaEntity> turma;
            try {
                turma = turmaService.findByCursoId(curso.getId());
            } catch (Exception e) {
                throw new Exception(e);
            }
            return turma;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @PostMapping
    public ResponseEntity<?> salvaCurso(@RequestBody @Valid CursoDTO curso) {
        return new ResponseEntity<>(cursoService.salvaCurso(curso), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> alteraCurso(@RequestBody @Valid CursoDTO curso) {
        return new ResponseEntity<>(cursoService.alteraCurso(curso), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void removeCurso(@PathVariable Long id) {
        cursoService.removeCurso(id);
    }

}
