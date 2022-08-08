package ru.kata.spring.boot_security.demo.servise;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.model.UserModel;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserModel> getAll();

    UserModel getUser(long id);

    void add(UserModel model);

    void remove(long id);

    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    UserModel loadUserModelByUsername(String s);

    void addDefaultRoles();

    UserModel updateUser(UserModel model);

    List<Role> getAllRole();
}