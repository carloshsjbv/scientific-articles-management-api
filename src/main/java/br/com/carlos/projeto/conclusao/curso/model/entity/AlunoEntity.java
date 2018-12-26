package br.com.carlos.projeto.conclusao.curso.model.entity;

import java.util.Collection;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Classe model referente à tabela ALUNO, do banco de dados.
 *
 * @author Carlos H
 */
@Entity
@Table(name = "ALUNO")
public class AlunoEntity extends UsuarioEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 3, max = 80, message = "O campo nome deve conter de 3 a 80 caracteres.")
    @Column(name = "CURSO", length = 80)
    private String nome;

    @NotNull
    @NotEmpty(message = "RA não pode ser vazio")
    @Size(min = 6, max = 6, message = "RA deve ter 6 dígitos")
    @Column(name = "RA", unique = true, length = 6, nullable = false)
    private String ra;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "TURMAID")
    private TurmaEntity turma;

    public AlunoEntity() {
    }

    public AlunoEntity(String username, String password, Collection<Role> roles, String type) {
        super(username, password, roles);
    }

    public AlunoEntity(
            String username,
            String password,
            Collection<Role> roles,
            String nome,
            String ra,
            TurmaEntity turma
    ) {
        super(
                username,
                password,
                roles
        );
        this.nome = nome;
        this.ra = ra;
        this.turma = turma;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public TurmaEntity getTurma() {
        return turma;
    }

    public void setTurma(TurmaEntity turma) {
        this.turma = turma;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.nome);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AlunoEntity other = (AlunoEntity) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.ra, other.ra)) {
            return false;
        }
        if (!Objects.equals(this.turma, other.turma)) {
            return false;
        }
        return true;
    }

}
