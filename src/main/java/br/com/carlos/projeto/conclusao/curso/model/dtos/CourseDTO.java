package br.com.carlos.projeto.conclusao.curso.model.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.carlos.projeto.conclusao.curso.model.CourseModel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CourseDTO
 *
 * @author Carlos H
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO
{

	@NotNull(message = "{not.null}")
	@NotEmpty(message = "{not.empty}")
	private String name;

	@NotNull(message = "{not.null}")
	@NotEmpty(message = "{not.empty}")
	@Pattern(regexp = "[A-Z]{2}")
	private String acronym;

	@NotNull(message = "{not.null}")
	@NotEmpty(message = "{not.empty}")
	private String area;

	public CourseModel transformToObject()
	{
		return new CourseModel(0, name, acronym, area);
	}

}
