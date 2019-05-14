package br.com.carlos.projeto.conclusao.curso.model.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Classe model referente à tabela TURMA, do banco de dados.
 *
 * @author Carlos H
 */
@Entity
@Table(name = "TURMA")
public class TurmaEntity implements Serializable {

    private static final long serialVersionUID = 627185243632457879L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @NotNull
    @Column(name = "ANOINICIAL")
    private int anoInicial;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CURSOID")
    private CursoEntity curso;

    public TurmaEntity() {
    }

    public TurmaEntity(long id) {
        this.id = id;
    }

    public TurmaEntity(long id, int anoInicial, CursoEntity curso) {
        this.id = id;
        this.anoInicial = anoInicial;
        this.curso = curso;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAnoInicial() {
        return anoInicial;
    }

    public void setAnoInicial(int anoInicial) {
        this.anoInicial = anoInicial;
    }

    public CursoEntity getCurso() {
        return curso;
    }

    public void setCurso(CursoEntity curso) {
        this.curso = curso;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final TurmaEntity other = (TurmaEntity) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.anoInicial != other.anoInicial) {
            return false;
        }
        if (!Objects.equals(this.curso, other.curso)) {
            return false;
        }
        return true;
    }

}
