package ru.kata.spring.boot_security.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.kata.spring.boot_security.demo.entity.RoleType;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(value = "user")
public class UserModel {

    private Long id;

    private String name;

    private String lastname;

    private String email;

    private Integer age;

    private String password;

    private List<String> roles;


    public static UserModel getModel(User user) {
        UserModel model = new UserModel();
        model.setId(user.getId());
        model.setName(user.getName());
        model.setLastname(user.getLastname());
        model.setEmail(user.getEmail());
        model.setAge(user.getAge());
        model.password = null;
        model.setRoles(user.getRoles().stream()
                .map(role -> role.getRole().name())
                .collect(Collectors.toList()));
        return model;
    }

    public User getUser(User user) {
        user.setId(this.id);
        user.setName(this.name == null || this.name.length() == 0 ? user.getName() : this.name);
        user.setLastname(this.lastname == null || this.lastname.length() == 0 ? user.getLastname() : this.lastname);
        user.setEmail(this.email == null || this.email.length() == 0 ? user.getEmail() : this.email);
        user.setAge(this.age == null || this.age == 0 ? user.getAge() : this.age);
        user.setPassword(this.password == null || this.password.length() == 0 ? user.getPassword() : this.password);
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean serchRoleAdmin() {
        return roles.stream().anyMatch(x -> x.equals(RoleType.ADMIN.name()));
    }
}