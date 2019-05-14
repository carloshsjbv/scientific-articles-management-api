package br.com.carlos.projeto.conclusao.curso.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * Classe responsável pelos papéis do usuário no sistema.
 *
 * @author Carlos H
 */
@Entity
@Table(name = "ROLE")
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ROLEID")
    @JsonIgnore
    private String authority;

    @ManyToMany (mappedBy = "authority") // Várias permissoes para vários usuários.
    @Column(name = "USUARIOID")
    @JsonIgnore
    private List<UsuarioEntity> usuarios;

    public Role() {
    }

    public Role(String auth) {
        super();
        this.authority = auth;
    }
    
    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public List<UsuarioEntity> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioEntity> usuarios) {
        this.usuarios = usuarios;
    }

}
