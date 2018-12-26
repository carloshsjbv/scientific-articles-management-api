package br.com.carlos.projeto.conclusao.curso.controller;

import br.com.carlos.projeto.conclusao.curso.model.dtos.AlunoDTO;
import br.com.carlos.projeto.conclusao.curso.model.entity.AlunoEntity;
import br.com.carlos.projeto.conclusao.curso.model.entity.Role;
import br.com.carlos.projeto.conclusao.curso.model.entity.TurmaEntity;
import br.com.carlos.projeto.conclusao.curso.service.AlunoService;
import br.com.carlos.projeto.conclusao.curso.service.TurmaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador Alunos
 *
 * @author Carlos H
 */
@RestController
@RequestMapping("/ri-api/v1/alunos")
@Api(value = "Aluno Controller")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private TurmaService turmaService;

    @ApiOperation(value = "Obtem a contagem de alunos presentes no sitema.")
    @GetMapping(value = "/count")
    public long contarSubmissoes() {
        return alunoService.countAll();
    }

    @ApiOperation(value = "Obtem a listagem de alunos presentes no sitema.")
    @GetMapping
    public List<AlunoEntity> listarAlunos() {
        return alunoService.findAll();
    }

    @ApiOperation(value = "Obtem a contagem de alunos presentes no sitema por ID.")
    @GetMapping(value = "/{id}")
    public AlunoEntity listarAlunosPorId(@PathVariable("id") Long id) {
        return alunoService.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid AlunoDTO aluno) {

        TurmaEntity turma = turmaService.findById(aluno.getTurma()).get();

        if (turma != null) {
            ArrayList<Role> roles = new ArrayList<>();
            Role role = new Role("ROLE_USER");
            roles.add(role);

            AlunoEntity novoAluno = new AlunoEntity();
            novoAluno.setNome(aluno.getNome());
            novoAluno.setRa(aluno.getRa());
            novoAluno.setTurma(turma);
            novoAluno.setUsername(aluno.getEmail());
            novoAluno.setPassword(new BCryptPasswordEncoder().encode(aluno.getSenha()));
            novoAluno.setAuthorities(roles);

//            AlunoEntity novoAluno = new AlunoEntity(
//                    aluno.getEmail(),
//                    new BCryptPasswordEncoder().encode(aluno.getSenha()),
//                    new ArrayList<>().add(new Role("ROLE_USER")),
//                    aluno.getNome(),
//                    aluno.getRa(),
//                    turma
//            );
            alunoService.save(novoAluno);
        }

        return new ResponseEntity<>(aluno, HttpStatus.CREATED);
    }

}
