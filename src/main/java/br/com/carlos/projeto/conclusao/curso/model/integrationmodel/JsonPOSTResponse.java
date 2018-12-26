package br.com.carlos.projeto.conclusao.curso.model.integrationmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author Carlos H
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonPOSTResponse {

    private String status;
    private String id;
    private String[] flashMessages;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getFlashMessages() {
        return flashMessages;
    }

    public void setFlashMessages(String[] flashMessages) {
        this.flashMessages = flashMessages;
    }

    @Override
    public String toString() {
        return "JsonPOSTResponse{" + "status=" + status + ", id=" + id + '}';
    }

}
