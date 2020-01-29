package br.com.carlos.projeto.conclusao.curso.model.integrationmodel;

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
public class PostParameters
{

	private String email = "carloshsjbv@gmail.com";
	private String access_token = "5340f79650f050b60d11f73f5315e39eeb04009b";

	private String fileName;
	private String citationStyleHash;
	private String fileContent;
	private String fileMetadata;

}
