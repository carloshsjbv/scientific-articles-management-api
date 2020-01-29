package br.com.carlos.projeto.conclusao.curso.model.integrationmodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class JsonCitationStylesResponse
{

	private String status;
	private List<CitationStyles> citationStyles;

	@Override
	public String toString()
	{
		return "JsonCitationStylesResponse{" + "status=" + status + ", citationStyles=" + citationStyles + '}';
	}

}
