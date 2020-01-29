package br.com.carlos.projeto.conclusao.curso.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an abstract user.
 *
 * @author Carlos H
 */
@Entity
@Table(name = "USER")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserModel implements UserDetails, Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@NotNull(message = "Favor informar um username válido")
	@Column(name = "USERNAME", unique = true, length = 80)
	private String username;

	@NotNull(message = "Favor informar uma senha válida")
	@Column(name = "PASSWORD", length = 80, nullable = false)
	private String password;

	@ManyToMany
	@JoinTable(name = "USUARIO_ROLES", joinColumns = @JoinColumn(name = "USUARIOID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ROLEID", referencedColumnName = "ROLEID"))
	private Collection<Role> authority;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return this.authority;
	}

	@Override
	public String getPassword()
	{
		return this.password;
	}

	@Override
	public String getUsername()
	{
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}

}
