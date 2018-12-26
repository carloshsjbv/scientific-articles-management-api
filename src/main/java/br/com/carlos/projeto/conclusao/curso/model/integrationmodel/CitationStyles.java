package br.com.carlos.projeto.conclusao.curso.model.integrationmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Carlos H
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CitationStyles {

    private String code;

    public CitationStyles(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CitationStyles{" + "code=" + code + '}';
    }

}
