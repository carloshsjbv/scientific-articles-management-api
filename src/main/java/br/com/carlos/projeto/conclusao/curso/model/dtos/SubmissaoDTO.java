package br.com.carlos.projeto.conclusao.curso.model.dtos;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * Data Transfer Object para submissões.
 *
 * @author Carlos H
 */
public class SubmissaoDTO implements Serializable {

    private static final long serialVersionUID = 6862315526156721323L;

    @NotNull(message = "{submission.titulo.not.null}")
    @NotEmpty(message = "{submission.titulo.not.empty}")
    private String titulo;

    @NotNull(message = "{submission.resumo.not.null}")
    @NotEmpty(message = "{submission.resumo.not.empty}")
    private String resumo;

    @NotNull(message = "{submission.keywords.not.null}")
    @NotEmpty(message = "{submission.keywords.not.empty}")
    private String keywords;

    @NotNull(message = "{submission.file.not.null}")
    private MultipartFile arquivo;

    @Email(message = "{email.not.valid")
    private String email;

    public SubmissaoDTO() {
    }

    public SubmissaoDTO(
            String titulo,
            String resumo,
            String keywords,
            MultipartFile arquivo,
            String email
    ) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.keywords = keywords;
        this.arquivo = arquivo;
        this.email = email;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public MultipartFile getArquivo() {
        return arquivo;
    }

    public void setArquivo(MultipartFile arquivo) {
        this.arquivo = arquivo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
