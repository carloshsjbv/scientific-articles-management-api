package br.com.carlos.projeto.conclusao.curso.controller;

import br.com.carlos.projeto.conclusao.curso.model.dtos.SubmissaoDTO;
import br.com.carlos.projeto.conclusao.curso.model.entity.SubmissaoEntity;
import br.com.carlos.projeto.conclusao.curso.service.SubmissaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de submissões, responsável pelo armazenamento de arquivo
 * submetido por alunos.
 *
 * @author Carlos H
 */
@RestController
@RequestMapping(path = "/ri-api/v1/submissoes")
public class SubmissaoController {

    @Autowired
    private SubmissaoService submissionsService;

    @GetMapping(value = "/count")
    public long contarSubmissoes() {
        return submissionsService.countAll();
    }

    @GetMapping
    public List<SubmissaoEntity> listarSubmissoes() {
        List<SubmissaoEntity> submissoes = (List<SubmissaoEntity>) submissionsService.findAll();
        return submissoes;
    }

    @GetMapping(value = "/{id}")
    public SubmissaoEntity listarSubmissoesPorId(@PathVariable("id") Long id) throws EntityNotFoundException, IOException {
        try {
            return submissionsService.findById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage() + id.toString());
        }
    }

    @GetMapping(value = "list/{acronimo}")
    public List<SubmissaoEntity> listarSubmissoesPorAlunoTurmaCurso(@PathVariable("acronimo") String acronimo) {
        List<SubmissaoEntity> submissoes = (List<SubmissaoEntity>) submissionsService.findAllByCourse(acronimo);
        return submissoes.isEmpty() ? null : submissoes;
    }

    @GetMapping(value = "list/{acronimo}/{anoInicial}")
    public List<SubmissaoEntity> listarSubmissoesPorAlunoTurmaAnoInicial(@PathVariable("acronimo") String acronimo, @PathVariable("anoInicial") int anoInicial) {
        List<SubmissaoEntity> submissoes = (List<SubmissaoEntity>) submissionsService.findAllByTurmaAnoCurso(anoInicial);
        return submissoes.isEmpty() ? null : submissoes;
    }

    @PostMapping(headers = "content-type=multipart/form-data", consumes = {
        MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> submitForm(@ModelAttribute @Valid SubmissaoDTO submissao) throws Exception {
        try {
            submissionsService.save(submissao);
            return (ResponseEntity<?>) ResponseEntity.ok(submissao);
        } catch (IOException | SQLException e) {
            throw new Exception(e);
        }
    }
}
