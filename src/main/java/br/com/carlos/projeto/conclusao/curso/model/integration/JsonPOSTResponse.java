package br.com.carlos.projeto.conclusao.curso.model.integration;

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
public class JsonPOSTResponse
{

	private String status;
	private String id;
	private String[] flashMessages;

	@Override
	public String toString()
	{
		return "JsonPOSTResponse{" + "status=" + status + ", id=" + id + '}';
	}

}
