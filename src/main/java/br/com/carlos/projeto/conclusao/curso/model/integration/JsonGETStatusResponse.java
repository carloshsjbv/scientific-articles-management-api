package br.com.carlos.projeto.conclusao.curso.model.integration;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class JsonGETStatusResponse
{

	private String status;
	private String jobStatus;
	private String jobStatusDescription;
	private List<String> flashMessages;

	@Override
	public String toString()
	{
		return "JsonGETStatusResponse{" + "status=" + status + ", jobStatus=" + jobStatus + ", jobStatusDescription="
				+ jobStatusDescription + ", flashMessages=" + flashMessages + '}';
	}

}
