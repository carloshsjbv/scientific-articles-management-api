package br.com.carlos.projeto.conclusao.curso.model.integrationmodel;

import java.util.List;

/**
 *
 * @author Carlos H
 */
public class JsonGETStatusResponse {

    private String status;
    private String jobStatus;
    private String jobStatusDescription;
    private List<String> flashMessages;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobStatusDescription() {
        return jobStatusDescription;
    }

    public void setJobStatusDescription(String jobStatusDescription) {
        this.jobStatusDescription = jobStatusDescription;
    }

    public List<String> getFlashMessages() {
        return flashMessages;
    }

    public void setFlashMessages(List<String> flashMessages) {
        this.flashMessages = flashMessages;
    }

    @Override
    public String toString() {
        return "JsonGETStatusResponse{" + "status=" + status + ", jobStatus=" + jobStatus + ", jobStatusDescription=" + jobStatusDescription + ", flashMessages=" + flashMessages + '}';
    }

}
