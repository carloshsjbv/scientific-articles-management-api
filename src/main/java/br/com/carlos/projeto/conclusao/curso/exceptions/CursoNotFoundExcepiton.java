package br.com.carlos.projeto.conclusao.curso.exceptions;

/**
 * Tentativa de implementação de exception para Curso não encontrado. Deveria
 * ser utilizado nas requisições de busca por Id
 *
 * Ainda em estudo.
 *
 * @author Carlos H
 */
class CursoNotFoundExcepiton extends Exception {

    public CursoNotFoundExcepiton(String message) {
        super(message);
    }
}
