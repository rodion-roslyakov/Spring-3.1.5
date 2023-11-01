package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles", uniqueConstraints= @UniqueConstraint(columnNames={"id", "role"}) )

public class Role implements GrantedAuthority {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role")

    private String role;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return getRole();
    }

    @Override
    public String toString() {
        return role.replace("ROLE_", "");
    }

    @Override
    public int hashCode() {
        return (31 * (((role == null) ? 0 : role.hashCode())) + (int) id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return this.id == role.id &&
                Objects.equals(this.role, role.role);
    }
}
