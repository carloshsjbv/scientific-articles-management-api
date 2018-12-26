package br.com.carlos.projeto.conclusao.curso.model.dtos;

import javax.validation.constraints.NotNull;

/**
 * DTO criado para manipular o objeto vindo da requisição POST de turmas, onde
 * um id de um curso é enviado.
 *
 * Depois de diversas tentativas, essa foi uma alternativa para conseguir
 * receber não o objeto curso, mas sim um idCurso, para que depois o objeto seja
 * recuperado internamente.
 *
 * @author Carlos H
 */
public class TurmaEntityDTO {

    @NotNull(message = "{not.null}")
    private int anoInicial;

    @NotNull(message = "{not.null}")
    private Long curso;

    public int getAnoInicial() {
        return anoInicial;
    }

    public void setAnoInicial(int anoInicial) {
        this.anoInicial = anoInicial;
    }

    public long getCurso() {
        return curso;
    }

    public void setCurso(long curso) {
        this.curso = curso;
    }

}
