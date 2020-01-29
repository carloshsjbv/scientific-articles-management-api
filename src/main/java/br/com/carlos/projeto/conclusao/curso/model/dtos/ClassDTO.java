package br.com.carlos.projeto.conclusao.curso.model.dtos;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ClassDTO
 *
 * @author Carlos H
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO
{

	@NotNull(message = "{not.null}")
	private int initialYear;

	@NotNull(message = "{not.null}")
	private Long course;

	}
