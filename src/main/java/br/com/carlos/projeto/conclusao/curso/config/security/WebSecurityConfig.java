package br.com.carlos.projeto.conclusao.curso.config.security;

import static br.com.carlos.projeto.conclusao.curso.config.security.SecurityConstants.SIGN_UP_URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{

	@Autowired
	private ImplementsUserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{

		http.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, SIGN_UP_URL).permitAll()
				.antMatchers("/*/protected/**").hasRole("USER").antMatchers("/*/admin/**").hasRole("ADMIN").and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), userDetailsService));
	}

	/**
	 * Configure authentication type
	 *
	 * @param auth
	 * @throws java.lang.Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
