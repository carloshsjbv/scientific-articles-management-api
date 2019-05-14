package br.com.carlos.projeto.conclusao.curso.model.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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

/**
 *
 * Classe responsável pelos dados de acesso à aplicação. A implementação da
 * interfface UserDetails, é um padrão definido pelo SpringSecurity.
 *
 * @author Carlos H
 */
@Entity
@Table(name = "USUARIO")
@Inheritance(strategy = InheritanceType.JOINED)
public class UsuarioEntity implements UserDetails, Serializable {

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
    @JoinTable(name = "USUARIO_ROLES",
            joinColumns = @JoinColumn(name = "USUARIOID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLEID", referencedColumnName = "ROLEID")
    )
    private Collection<Role> authority;

    public UsuarioEntity() {
    }

    public UsuarioEntity(String username, String password, Collection<Role> authorities) {
        this.username = username;
        this.password = password;
        this.authority = authorities;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authority = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authority;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
