package br.com.carlos.projeto.conclusao.curso.model.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Carlos H
 */
@Entity
@Table(name = "FILASUBMISSOES")
public class FilaSubmissoes implements Serializable {

    private static final long serialVersionUID = 3353083147886258895L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILA_SEQ")
    @SequenceGenerator(name = "FILA_SEQ", sequenceName = "FILA_SEQ", allocationSize = 1)
    @Column(name = "FILASUBMISSOESID")
    private long id;

    @NotNull
    @OneToOne
    private SubmissaoEntity submissaoEntity;

    @NotNull
    @Column(name = "ENVIADO")
    private boolean enviado;

    public FilaSubmissoes() {
    }

    public FilaSubmissoes(long id) {
        this.id = id;
    }

    public FilaSubmissoes(
            long id,
            SubmissaoEntity submissaoEntity,
            boolean enviado
    ) {
        this.id = id;
        this.submissaoEntity = submissaoEntity;
        this.enviado = enviado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SubmissaoEntity getSubmissaoEntity() {
        return submissaoEntity;
    }

    public void setSubmissaoEntity(SubmissaoEntity submissaoEntity) {
        this.submissaoEntity = submissaoEntity;
    }

    public boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final FilaSubmissoes other = (FilaSubmissoes) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.enviado != other.enviado) {
            return false;
        }
        if (!Objects.equals(this.submissaoEntity, other.submissaoEntity)) {
            return false;
        }
        return true;
    }

}
