package br.com.carlos.projeto.conclusao.curso.model.dtos;

import br.com.carlos.projeto.conclusao.curso.model.entity.CursoEntity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * AlunoDTO vindo do HTML.
 *
 * @author Carlos H
 */
public class CursoDTO {

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

    public CursoDTO() {
    }

    public CursoDTO(String nome, String acronimo, String area) {
        this.nome = nome;
        this.acronimo = acronimo;
        this.area = area;
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

    public CursoEntity transformaParaObjeto() {
        return new CursoEntity(0, nome, acronimo, area);
    }

}
