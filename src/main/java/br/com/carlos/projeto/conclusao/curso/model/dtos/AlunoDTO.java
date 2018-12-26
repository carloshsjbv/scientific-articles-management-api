package br.com.carlos.projeto.conclusao.curso.model.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * AlunoDTO vindo do HTML.
 *
 * @author Carlos H
 */
public class AlunoDTO {

    @NotNull(message = "{not.null}")
    @NotEmpty(message = "{not.empty}")
    @Email
    private String email;

    @NotNull(message = "{not.null}")
    @NotEmpty(message = "{not.empty}")
    private String senha;

    @NotNull(message = "{not.null}")
    @NotEmpty(message = "{not.empty}")
    private String nome;

    @NotNull(message = "{not.null}")
    @NotEmpty(message = "{not.empty}")
    @Size(min = 6, max = 6, message = "RA deve ter 6 dígitos")
    private String ra;

    @NotNull(message = "{not.null}")
    private Long turma;

    public AlunoDTO() {
    }

    public AlunoDTO(String email, String senha, String nome, String ra, Long turma) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.ra = ra;
        this.turma = turma;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public Long getTurma() {
        return turma;
    }

    public void setTurma(Long turma) {
        this.turma = turma;
    }

}
