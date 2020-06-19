package br.com.carlos.projeto.conclusao.curso.components.request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.com.carlos.projeto.conclusao.curso.model.common.SubmissionModel;

public class RequestHandler {
	
	/**
	 * Build http request before sending to OTS API.
	 * 
	 * @param submissionToSend
	 * @return
	 * @throws IOException
	 */
	public HttpEntity<MultiValueMap<String, Object>> build(SubmissionModel submissionToSend, HashMap<String, String> params) throws IOException
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

		File file = new File(submissionToSend.getOriginalDocumentPath());
		byte[] fileContent = Files.readAllBytes(file.toPath());

		File jsonMetadata = new File(params.get("metadataFilePath"));
		
		byte[] fileMetadata = Files.readAllBytes(jsonMetadata.toPath());

		body.add("email", params.get("email"));
		body.add("access_token", params.get("otsAuthToken"));
		body.add("fileName", file.getName());
		body.add("citationStyleHash", "3f0f7fede090f24cc71b7281073996be");
		body.add("fileContent", fileContent);

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		
		return requestEntity;
	}

}
