package br.com.carlos.projeto.conclusao.curso.controller;

import br.com.carlos.projeto.conclusao.curso.model.dtos.TurmaEntityDTO;
import br.com.carlos.projeto.conclusao.curso.model.entity.CursoEntity;
import br.com.carlos.projeto.conclusao.curso.model.entity.TurmaEntity;
import br.com.carlos.projeto.conclusao.curso.service.CursoService;
import br.com.carlos.projeto.conclusao.curso.service.TurmaService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador Turmas
 *
 * @author Carlos H
 */
@RestController
@RequestMapping("/ri-api/v1/turmas")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public List<TurmaEntity> listarTurmas() {

        List<TurmaEntity> turmas = (List<TurmaEntity>) turmaService.findAll();
        return turmas;
    }

    @GetMapping(value = "/{id}")
    public TurmaEntity listarTurmasPorId(@PathVariable("id") Long id) {
        return turmaService.findById(id).get();
    }

    @GetMapping(value = "curso/{cursoId}")
    public List<TurmaEntity> listarTurmasPorCursoId(@PathVariable("cursoId") Long cursoId) {
        return turmaService.findByCursoId(cursoId);
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid TurmaEntityDTO turmaDTO) {
        CursoEntity curso = cursoService.findById(turmaDTO.getCurso());

        TurmaEntity turma = new TurmaEntity();

        turma.setAnoInicial(turmaDTO.getAnoInicial());
        turma.setCurso(curso);

        return new ResponseEntity<>(turmaService.save(turma), HttpStatus.CREATED);

    }

    @DeleteMapping
    public void delete(@RequestBody @Valid TurmaEntity turmaDTO) {
        turmaService.delete(turmaDTO.getId());
    }
}
