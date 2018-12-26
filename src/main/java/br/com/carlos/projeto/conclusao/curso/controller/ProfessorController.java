//package br.com.carlos.projeto.conclusao.curso.controller;
//
//import br.com.carlos.projeto.conclusao.curso.model.ProfessorEntity;
//import br.com.carlos.projeto.conclusao.curso.repository.ProfessorRepository;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * Controlador Professores
// *
// * @author Carlos H
// */
//@RestController
//public class ProfessorController {
//
//    @Autowired
//    private ProfessorRepository professorRepository;
//
//    @GetMapping(value = "/professores")
//    @CrossOrigin(origins = "http://localhost:4200")
//    public List<ProfessorEntity> listarProfessores() {
//
//        List<ProfessorEntity> professores = (List<ProfessorEntity>) professorRepository.findAll();
//        return professores;
//    }
//
//    @GetMapping(value = "/professores/{id}")
//    @CrossOrigin(origins = "http://localhost:4200")
//    public ProfessorEntity listarProfessores(@PathVariable("id") Long id) {
//        return professorRepository.findById(id).get();
//    }
//
//}
