package br.com.carlos.projeto.conclusao.curso.model.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Classe model referente à tabela SUBMISSAO, do banco de dados.
 *
 * @author Carlos H
 */
@Entity
@Table(name = "SUBMISSAO")
public class SubmissaoEntity implements Serializable {

    private static final long serialVersionUID = -2772112010193183542L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBMISSAOID")
    private long id;

    @NotNull
    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "RESUMO")
    private String resumo;

    @Column(name = "PALAVRASCHAVE")
    private String palavrasChave;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MOMENTOSUBMISSAO")
    private Calendar momentoSubmissao;

    @NotNull
    @Column(name = "PATHDOCUMENTOORIGINAL")
    private String pathDocumentoOriginal;

    @Column(name = "PATHEPUB")
    private String pathEpub;

    @Column(name = "PATHPDF")
    private String pathPDF;

    @Column(name = "PATHXML")
    private String pathXML;

    @Column(name = "PATHHTML")
    private String pathHTML;

    @NotNull
    @Column(name = "AUTORIZADO")
    private boolean autorizado;

    @Column(name = "OTSID")
    private long otsId;

    @NotNull
    @OneToOne
    @JoinColumn(name = "USUARIOID")
    private AlunoEntity aluno;

    public SubmissaoEntity() {
    }

    public SubmissaoEntity(long id) {
        this.id = id;
    }

    public SubmissaoEntity(
            long id,
            String titulo,
            String resumo,
            String palavrasChave,
            Calendar momentoSubmissao,
            String originalDocumentFilePath,
            String epubFilePath,
            String pdfFilePath,
            String xmlFilePath,
            String htmlFilePath,
            boolean autorizado,
            long otsId,
            AlunoEntity aluno) {
        this.id = id;
        this.titulo = titulo;
        this.resumo = resumo;
        this.palavrasChave = palavrasChave;
        this.momentoSubmissao = momentoSubmissao;
        this.pathDocumentoOriginal = originalDocumentFilePath;
        this.pathEpub = epubFilePath;
        this.pathPDF = pdfFilePath;
        this.pathXML = xmlFilePath;
        this.pathXML = htmlFilePath;
        this.autorizado = autorizado;
        this.otsId = otsId;
        this.aluno = aluno;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getPalavrasChave() {
        return palavrasChave;
    }

    public void setPalavrasChave(String palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    public Calendar getMomentoSubmissao() {
        return momentoSubmissao;
    }

    public void setMomentoSubmissao(Calendar momentoSubmissao) {
        this.momentoSubmissao = momentoSubmissao;
    }

    public String getPathDocumentoOriginal() {
        return pathDocumentoOriginal;
    }

    public void setPathDocumentoOriginal(String pathDocumentoOriginal) {
        this.pathDocumentoOriginal = pathDocumentoOriginal;
    }

    public String getPathEpub() {
        return pathEpub;
    }

    public void setPathEpub(String pathEpub) {
        this.pathEpub = pathEpub;
    }

    public String getPathPDF() {
        return pathPDF;
    }

    public void setPathPDF(String pathPDF) {
        this.pathPDF = pathPDF;
    }

    public String getPathXML() {
        return pathXML;
    }

    public void setPathXML(String pathXML) {
        this.pathXML = pathXML;
    }

    public String getPathHTML() {
        return pathHTML;
    }

    public void setPathHTML(String pathHTML) {
        this.pathHTML = pathHTML;
    }

    public boolean isAutorizado() {
        return autorizado;
    }

    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }

    public long getOtsId() {
        return otsId;
    }

    public void setOtsId(long otsId) {
        this.otsId = otsId;
    }

    public AlunoEntity getAluno() {
        return aluno;
    }

    public void setAluno(AlunoEntity aluno) {
        this.aluno = aluno;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SubmissaoEntity other = (SubmissaoEntity) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.autorizado != other.autorizado) {
            return false;
        }
        if (this.otsId != other.otsId) {
            return false;
        }
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        if (!Objects.equals(this.resumo, other.resumo)) {
            return false;
        }
        if (!Objects.equals(this.palavrasChave, other.palavrasChave)) {
            return false;
        }
        if (!Objects.equals(this.pathDocumentoOriginal, other.pathDocumentoOriginal)) {
            return false;
        }
        if (!Objects.equals(this.pathEpub, other.pathEpub)) {
            return false;
        }
        if (!Objects.equals(this.pathPDF, other.pathPDF)) {
            return false;
        }
        if (!Objects.equals(this.pathXML, other.pathXML)) {
            return false;
        }
        if (!Objects.equals(this.pathHTML, other.pathHTML)) {
            return false;
        }
        if (!Objects.equals(this.momentoSubmissao, other.momentoSubmissao)) {
            return false;
        }
        if (!Objects.equals(this.aluno, other.aluno)) {
            return false;
        }
        return true;
    }

}
