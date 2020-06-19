package br.com.carlos.projeto.conclusao.curso.config.security;

import io.jsonwebtoken.Jwts;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter
{

	private final ImplementsUserDetailsService userDetailsService;

	@Autowired
	private SecurityProperties securityProps;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
			ImplementsUserDetailsService userDetailsService)
	{
		super(authenticationManager);
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		String header = request.getHeader(securityProps.getHeaderString());

		if (header == null || !header.startsWith(securityProps.getTokenPrefix()))
		{
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);

	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request)
	{
		String token = request.getHeader(securityProps.getHeaderString());
		if (token == null)
		{
			return null;
		}

		String username = Jwts.parser().setSigningKey(securityProps.getSecret()).parseClaimsJws(token.replace(securityProps.getTokenPrefix(), "")).getBody()
				.getSubject();

		UserDetails ud = userDetailsService.loadUserByUsername(username);
		return ud != null ? new UsernamePasswordAuthenticationToken(username, null, ud.getAuthorities()) : null;
	}
}
