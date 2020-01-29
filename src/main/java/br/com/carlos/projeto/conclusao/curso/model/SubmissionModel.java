package br.com.carlos.projeto.conclusao.curso.model;

import java.io.Serializable;
import java.util.Calendar;

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

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a file submission model.
 *
 * @author Carlos H
 */
@Entity
@Table(name = "SUBMISSION")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionModel implements Serializable
{

	private static final long serialVersionUID = -2772112010193183542L;

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SUBMISSION_ID")
	private long id;

	@NotNull
	@Column(name = "ARTICLE_TITLE")
	private String articleTitle;

	@Column(name = "ARTICLE_ABSTRACT")
	private String articleAbstract;

	@Column(name = "KEYWORDS")
	private String keywords;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SUBMISSION_INSTANT")
	private Calendar submissionInstant;

	@NotNull
	@Column(name = "ORIGINAL_DOCUMENT_PATH")
	private String originalDocumentPath;

	@Column(name = "EPUB_PATH")
	private String epubPath;

	@Column(name = "PDF_PATH")
	private String pdfPath;

	@Column(name = "XML_PATH")
	private String xmlPath;

	@Column(name = "HTML_PATH")
	private String htmlPath;

	@NotNull
	@Column(name = "AUTHORIZED")
	private boolean authorized;

	@Column(name = "OTSID")
	private long otsId;

	@NotNull
	@OneToOne
	@JoinColumn(name = "STUDENT_ID")
	private StudentModel student;

}
