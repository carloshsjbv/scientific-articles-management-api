package br.com.carlos.projeto.conclusao.curso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.carlos.projeto.conclusao.curso.config.security.JWTAuthenticationFilter;
import br.com.carlos.projeto.conclusao.curso.model.dtos.LoginDTO;

/**
 * Login Controller
 *
 * @author Carlos H
 */
@RestController
public class LoginController
{

	private AuthenticationManager auth;

	public void setAuth(AuthenticationManager auth)
	{
		this.auth = auth;
	}

	@PostMapping(path = "/login")
	public UserDetails login(@RequestBody LoginDTO login, HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException
	{

		Authentication credentials = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());

		UserDetails usr = (UserDetails) auth.authenticate(credentials).getPrincipal();

		JWTAuthenticationFilter filter = new JWTAuthenticationFilter(auth);
		filter.attemptAuthentication(request, response);

		return usr;

	}
}
