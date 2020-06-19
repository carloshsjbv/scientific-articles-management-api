package br.com.carlos.projeto.conclusao.curso.model.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a class
 *
 * @author Carlos H
 */
@Entity
@Table(name = "CLASS")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class StudentClassModel implements Serializable
{

	private static final long serialVersionUID = 627185243632457879L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@NotNull
	@Column(name = "INITIAL_YEAR")
	private int initialYear;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "COURSE_ID")
	private CourseModel course;

}
