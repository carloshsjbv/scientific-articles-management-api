package br.com.carlos.projeto.conclusao.curso.model.integrationmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Carlos H
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CitationStyles
{

	private String code;

	public String toString()
	{
		return "CitationStyles{" + "code=" + code + '}';
	}

}
