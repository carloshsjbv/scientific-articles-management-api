package br.com.carlos.projeto.conclusao.curso.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Carlos H
 */
@Component
public class SecurityProperties
{

	@Value("${application.token.secret}")
	private String secret = "c7a3r76";

	@Value("${application.token.prefix}")
	private String tokenPrefix;
	
	@Value("${application.token.header.string}")
	private String headerString;
	
	@Value("${application.token.signin.url}")
	private String singupUrl;

	@Value("${application.token.expiration.time}")
	private Long expirationTime;

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getTokenPrefix() {
		return tokenPrefix;
	}

	public void setTokenPrefix(String tokenPrefix) {
		this.tokenPrefix = tokenPrefix;
	}

	public String getHeaderString() {
		return headerString;
	}

	public void setHeaderString(String headerString) {
		this.headerString = headerString;
	}

	public String getSingupUrl() {
		return singupUrl;
	}

	public void setSingupUrl(String singupUrl) {
		this.singupUrl = singupUrl;
	}

	public Long getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Long expirationTime) {
		this.expirationTime = expirationTime;
	}
	

}
