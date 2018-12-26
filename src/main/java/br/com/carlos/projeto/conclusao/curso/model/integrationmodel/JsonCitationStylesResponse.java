package br.com.carlos.projeto.conclusao.curso.model.integrationmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author Carlos H
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonCitationStylesResponse {

    private String status;
    private List<CitationStyles> citationStyles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CitationStyles> getCitationStyles() {
        return citationStyles;
    }

    public void setCitationStyles(List<CitationStyles> citationStyles) {
        this.citationStyles = citationStyles;
    }

    @Override
    public String toString() {
        return "JsonCitationStylesResponse{" + "status=" + status + ", citationStyles=" + citationStyles + '}';
    }

}
