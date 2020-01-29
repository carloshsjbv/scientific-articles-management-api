package br.com.carlos.projeto.conclusao.curso.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a student users.
 *
 * @author Carlos H
 */
@Entity
@Table(name = "STUDENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentModel extends UserModel
{

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 3, max = 80)
	@Column(name = "NAME", length = 80)
	private String name;

	@NotNull
	@NotEmpty
	@Size(min = 6, max = 6)
	@Column(name = "SCHOLAR_IDENTIFICATION", unique = true, length = 6, nullable = false)
	private String scholarIdentification;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "CLASS_ID")
	private StudentClassModel studentClass;

}
