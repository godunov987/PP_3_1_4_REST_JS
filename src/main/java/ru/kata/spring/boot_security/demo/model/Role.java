package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id = 0l;

    private RoleType role;

    @ManyToMany (mappedBy = "roles")
    private Set<User> users;

    public Role(Long id, RoleType role) {
        this.id = id;
        this.role = role;
    }
    public Role(RoleType role) {
        this.role = role;
    }

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.name();
    }
}
