package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum RoleType {
    ADMIN("ROLE_ADMIN"),
    USER("USER_USER");

    RoleType(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }
}
