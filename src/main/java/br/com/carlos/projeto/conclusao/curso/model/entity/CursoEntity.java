package br.com.carlos.projeto.conclusao.curso.model.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Classe model referente à tabela CURSO, do banco de dados.
 *
 * @author Carlos H
 */
@Entity
@Table(name = "CURSO")
public class CursoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CURSO_SEQ")
    @SequenceGenerator(name = "CURSO_SEQ", sequenceName = "CURSO_SEQ", allocationSize = 1)
    private long id;

    @NotNull(message = "{not.null}")
    @NotEmpty(message = "{not.empty}")
    private String nome;

    @NotNull(message = "{not.null}")
    @NotEmpty(message = "{not.empty}")
    @Pattern(regexp = "[A-Z]{2}")
    private String acronimo;

    @NotNull(message = "{not.null}")
    @NotEmpty(message = "{not.empty}")
    private String area;

    public CursoEntity() {
    }

    public CursoEntity(long id) {
        this.id = id;
    }

    public CursoEntity(long id, String nome, String acronimo, String area) {
        this.id = id;
        this.nome = nome;
        this.acronimo = acronimo;
        this.area = area;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final CursoEntity other = (CursoEntity) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.acronimo, other.acronimo)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        return true;
    }

}
