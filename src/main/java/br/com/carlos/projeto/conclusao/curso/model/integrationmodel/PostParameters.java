package br.com.carlos.projeto.conclusao.curso.model.integrationmodel;

/**
 *
 * @author Carlos H
 */
public class PostParameters {

    private String email = "carloshsjbv@gmail.com";
    private String access_token = "5340f79650f050b60d11f73f5315e39eeb04009b";

    private String fileName;
    private String citationStyleHash;
    private String fileContent;
    private String fileMetadata;

    public PostParameters(
            String fileName,
            String citationStyleHash,
            String fileContent,
            String fileMetadata
    ) {
        this.fileName = fileName;
        this.citationStyleHash = citationStyleHash;
        this.fileContent = fileContent;
        this.fileMetadata = fileMetadata;
    }

    public String getEMAIL() {
        return email;
    }

    public String getACCESS_TOKEN() {
        return access_token;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCitationStyleHash() {
        return citationStyleHash;
    }

    public void setCitationStyleHash(String citationStyleHash) {
        this.citationStyleHash = citationStyleHash;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileMetadata() {
        return fileMetadata;
    }

    public void setFileMetadata(String fileMetadata) {
        this.fileMetadata = fileMetadata;
    }

}
