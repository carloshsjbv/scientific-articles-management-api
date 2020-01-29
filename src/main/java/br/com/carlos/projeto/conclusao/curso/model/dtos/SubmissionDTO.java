package br.com.carlos.projeto.conclusao.curso.model.dtos;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * SubmissionDTO.
 *
 * @author Carlos H
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO implements Serializable
{

	private static final long serialVersionUID = 6862315526156721323L;

	@NotNull(message = "{submission.titulo.not.null}")
	@NotEmpty(message = "{submission.titulo.not.empty}")
	private String articleTitle;

	@NotNull(message = "{submission.resumo.not.null}")
	@NotEmpty(message = "{submission.resumo.not.empty}")
	private String articleAbstract;

	@NotNull(message = "{submission.keywords.not.null}")
	@NotEmpty(message = "{submission.keywords.not.empty}")
	private String keywords;

	@NotNull(message = "{submission.file.not.null}")
	private MultipartFile file;

	@Email(message = "{email.not.valid")
	private String email;

}
