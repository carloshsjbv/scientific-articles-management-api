package br.com.carlos.projeto.conclusao.curso.components;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.carlos.projeto.conclusao.curso.ProjetoConclusaoCursoApplication;
import br.com.carlos.projeto.conclusao.curso.components.request.RequestHandler;
import br.com.carlos.projeto.conclusao.curso.model.common.StudentModel;
import br.com.carlos.projeto.conclusao.curso.model.common.SubmissionModel;
import br.com.carlos.projeto.conclusao.curso.model.common.SubmissionsQueue;
import br.com.carlos.projeto.conclusao.curso.model.integration.JsonGETStatusResponse;
import br.com.carlos.projeto.conclusao.curso.model.integration.JsonPOSTResponse;
import br.com.carlos.projeto.conclusao.curso.repository.SubmissionRepository;
import br.com.carlos.projeto.conclusao.curso.repository.SubmissionsQueueRepository;

/**
 * Component responsible for sending an HTTP request to OTS api in a scheduled
 * time.
 *
 * @author Carlos H
 */
@Component
@EnableScheduling
public class RestEventScheduler
{

	private static final Logger LOG = LoggerFactory.getLogger(ProjetoConclusaoCursoApplication.class);

	@Autowired
	private SubmissionsQueueRepository submissionQueueRepository;

	@Autowired
	private SubmissionRepository submissionRepository;

	@Autowired
	private XMLConversor conversor;

	@Autowired
	private FileManager fileManager;

	private final String timeZone = "America/Sao_Paulo";

	@Value("${application.ots.account.email}")
	private String email;
	
	@Value("${appliction.ots.api.url}")
	private String otsUrl;

	@Value("${application.ots.api.auth.token}")
	private String otsAuthToken = "9f392da47a631ac2fe5ec2440e2ba4c36bca1d38";

	@Value("${application.files.metadata.filepath}")
	private String metadataFilePath;


	/**
	 * Scheduler method
	 *
	 * @throws java.lang.Exception
	 * @throws br.com.carlos.projeto.conclusao.curso.exceptions.RestRequestHandler
	 * 
	 */
	@Scheduled(cron = "0 0 1 * * *", zone = timeZone)
	public void sendArtile() throws Exception
	{

		String postURL = otsUrl + "submit";

		RestTemplate restTemplateToPost = new RestTemplate();

		HashMap<String, String> params = buildRequestParams();
		
		try
		{
			Iterable<SubmissionsQueue> queue = submissionQueueRepository.findAllBySent(false);

			for (SubmissionsQueue queueItem : queue)
			{

				SubmissionModel submissionToSend = queueItem.getSubmissionModel();

				if (submissionToSend != null)
				{

					System.out.println("Begining /POST request!");

					HttpEntity<MultiValueMap<String, Object>> requestEntity = new RequestHandler().build(submissionToSend, params);

					ResponseEntity<?> postForObject = restTemplateToPost.postForEntity(postURL, requestEntity, String.class);

					ObjectMapper mapper = new ObjectMapper();

					JsonPOSTResponse response = mapper.readValue((String) postForObject.getBody(),
							JsonPOSTResponse.class);

					if (postForObject.getBody().toString().contains("success"))
					{
						submissionToSend.setOtsId(Long.parseLong(response.getId()));
						submissionToSend.setAuthorized(true);

						queueItem.setSent(true);

						System.out.println(postForObject);
						System.out.println(response);

						submissionQueueRepository.save(queueItem);
						submissionRepository.save(submissionToSend);
					}

				}
			}
		} catch (IOException | RestClientException e)
		{
			throw new Exception(e);
		}

	}

	/**
	 * Scheduler built to reconver processed files from OTS api
	 *
	 * There will be triggered three requests with the following indexes (according
	 * to OTS documentation).
	 *
	 * 8 - PDF file.
	 *
	 * 11 - EPUB File.
	 *
	 * 20 - XML file.
	 */
	@Scheduled(cron = "0 0 1 * * *", zone = timeZone)
	public void getRequest()
	{

		String getURL = otsUrl + "status?email=" + email + "&access_token=" + otsAuthToken;

		RestTemplate restTemplateToGet = new RestTemplate();

		try
		{
			LOG.debug("Starting file recovery from database...");

			Iterable<SubmissionsQueue> sentFilesQueue = submissionQueueRepository.findAllBySent(true);

			for (SubmissionsQueue queueItem : sentFilesQueue)
			{

				StudentModel aluno = queueItem.getSubmissionModel().getStudent();

				getURL += "&id=" + queueItem.getSubmissionModel().getOtsId();

				ResponseEntity<?> getObject = restTemplateToGet.getForEntity(getURL, JsonGETStatusResponse.class);

				JsonGETStatusResponse statusReponse = (JsonGETStatusResponse) getObject.getBody();

				if (statusReponse.getJobStatus().equals("2"))
				{

					getURL = getURL.replace("status", "retrieve");

					String pdfFilePath = processFile(executeRequest(restTemplateToGet, getURL, "8"), "pdf", aluno);

					String epubFilePath = processFile(executeRequest(restTemplateToGet, getURL, "11"), "epub", aluno);

					String xmlFilePath = processFile(executeRequest(restTemplateToGet, getURL, "20"), "xml", aluno);

					String htmlFilePath = conversor.converteXML(xmlFilePath, fileManager, aluno);

					SubmissionModel submission = queueItem.getSubmissionModel();

					submission.setPdfPath(pdfFilePath);
					submission.setEpubPath(epubFilePath);
					submission.setXmlPath(xmlFilePath);
					submission.setHtmlPath(htmlFilePath);

					submissionRepository.save(submission);

					submissionQueueRepository.delete(queueItem);
				}

			}

		} catch (Exception e)
		{
			LOG.error(e.getMessage());
		}
	}

	/**
	 * Execute GET request to OTS API.
	 * 
	 * @param restTemplate - Manages http request
	 * @param url          - Request URL.
	 * @param              idArquivoObtido- File id to be requested to OTS api.
	 * 
	 * @return = Byte array from file comming from OTS api.
	 * @throws Exception 
	 */
	protected byte[] executeRequest(RestTemplate restTemplate, String url, String fileId) throws Exception
	{

		ByteArrayHttpMessageConverter messageConverter = new ByteArrayHttpMessageConverter();

		List<MediaType> allowedMediaTypes = buildFileTypes();
		
		messageConverter.setSupportedMediaTypes(allowedMediaTypes);

		List<HttpMessageConverter<?>> httpMessageConverter = new ArrayList<>();
		
		httpMessageConverter.add(messageConverter);

		restTemplate.setMessageConverters(httpMessageConverter);

		HttpHeaders httpHeaders = new HttpHeaders();

		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

		ResponseEntity<byte[]> response = restTemplate.exchange(conversionUrlBuilder(url, fileId), HttpMethod.GET, entity, byte[].class);

		if (response.hasBody())
		{
			return response.getBody();
		}

		throw new Exception("It was not possible to recover files from OTS API");
		
	}

	private List<MediaType> buildFileTypes() 
	{
		
		List<MediaType> fileTypes = new ArrayList<>();
		
		fileTypes.add(new MediaType("application", "pdf"));
		fileTypes.add(new MediaType("application", "xml"));
		fileTypes.add(new MediaType("application", "epub+zip"));
		
		return fileTypes;
	}

	/**
	 * Conversion URL builder
	 *
	 * @param url
	 * @param conversionStage
	 * @return
	 */
	private String conversionUrlBuilder(String url, String conversionStage)
	{
		return url.concat("&conversionStage=").concat(conversionStage);
	}

	/**
	 * Aims to get files from file system before sending to OTS api.
	 *
	 * @param byteFile       - File ByteArray
	 * @param fileExtensinon - File extension
	 * @param student          = Student entity which will help to build file path
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private String processFile(byte[] byteFile, String fileExtensinon, StudentModel student)
			throws FileNotFoundException, IOException
	{

		InputStream byteArrayInputStream = new ByteArrayInputStream(byteFile);

		String[] paths = fileManager.pathBuilder(student);

		fileManager.writeFile(new FileOutputStream(new File(paths[0] + "submissao." + fileExtensinon)), byteArrayInputStream);

		return paths[0] + "submissao." + fileExtensinon;
	}

	private HashMap<String, String> buildRequestParams() {

		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("email", email);
		params.put("otsAuthToken", otsAuthToken);
		params.put("metadataFilePath", metadataFilePath);
		
		return params;
	}

}
