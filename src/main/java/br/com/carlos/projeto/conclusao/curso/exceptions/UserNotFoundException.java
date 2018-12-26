package br.com.carlos.projeto.conclusao.curso.exceptions;

/**
 *
 * Exception a ser lançada quando usuários não forem encontrados em buscas.
 *
 * @author Carlos H
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String mensagem) {
        super(mensagem);
    }

    public UserNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
