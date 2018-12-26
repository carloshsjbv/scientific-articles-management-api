package br.com.carlos.projeto.conclusao.curso.model.entity;

//package br.com.carlos.projeto.conclusao.curso.model;
//
//import java.io.Serializable;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
///**
// * Classe model referente à tabela PROFESSOR, do banco de dados.
// *
// * @author Carlos H
// */
//@Entity
//@Table(name = "PROFESSOR")
//public class ProfessorEntity implements Serializable {
//
//    @Id
//    @NotNull
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFESSOR_SEQ")
//    @SequenceGenerator(name = "PROFESSOR_SEQ", sequenceName = "PROFESSOR_SEQ", allocationSize = 1)
//    private long id;
//
//    @NotNull
//    @Size(min = 6, max = 6, message = "RA deve ter 6 dígitos")
//    private int ra;
//
//    @NotNull
//    @Email
//    private String email;
//
//    @NotNull
//    private String senha;
//
//    @NotNull
//    private String area;
//
//    @NotNull
//    @ManyToOne
//    private TurmaEntity turma;
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public int getRa() {
//        return ra;
//    }
//
//    public void setRa(int ra) {
//        this.ra = ra;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getSenha() {
//        return senha;
//    }
//
//    public void setSenha(String senha) {
//        this.senha = senha;
//    }
//
//    public String getArea() {
//        return area;
//    }
//
//    public void setArea(String area) {
//        this.area = area;
//    }
//
//    public TurmaEntity getTurma() {
//        return turma;
//    }
//
//    public void setTurma(TurmaEntity turma) {
//        this.turma = turma;
//    }
//
//}
