package br.com.carlos.projeto.conclusao.curso.model.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a temporary submissions queue.
 *
 * @author Carlos H
 */
@Entity
@Table(name = "SUBMISSIONS_QUEUE")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionsQueue implements Serializable
{

	private static final long serialVersionUID = 3353083147886258895L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SUBMISSION_QUEUE_ID")
	private long id;

	@NotNull
	@OneToOne
	private SubmissionModel submissionModel;

	@NotNull
	@Column(name = "SENT")
	private boolean sent;

}
