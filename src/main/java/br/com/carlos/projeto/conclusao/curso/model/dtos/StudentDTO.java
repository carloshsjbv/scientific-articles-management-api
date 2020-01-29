package br.com.carlos.projeto.conclusao.curso.model.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * StudentDTO.
 *
 * @author Carlos H
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO
{

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
	@Size(min = 6, max = 6, message = "It must have 6 digits")
	private String ra;

	@NotNull(message = "{not.null}")
	private Long studentClass;

}
