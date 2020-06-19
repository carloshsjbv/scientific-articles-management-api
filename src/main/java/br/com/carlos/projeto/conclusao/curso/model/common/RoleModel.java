package br.com.carlos.projeto.conclusao.curso.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * Represents the user's roles in the system.
 *
 * @author Carlos H
 */
@Entity
@Table(name = "ROLE")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RoleModel implements GrantedAuthority
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ROLEID")
	@JsonIgnore
	private String authority;

	@ManyToMany(mappedBy = "authority")
	@Column(name = "USUARIOID")
	@JsonIgnore
	private List<UserModel> users;

	public RoleModel(String authority)
	{
		this.authority = authority;
	}

}
