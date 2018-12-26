package br.com.carlos.projeto.conclusao.curso.config.security;

/**
 *
 * @author Carlos H
 */
public class SecurityConstants {

    static final String SECRET = "violao";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/login";
    static final long EXPIRATION_TIME = 86400000l;

}
