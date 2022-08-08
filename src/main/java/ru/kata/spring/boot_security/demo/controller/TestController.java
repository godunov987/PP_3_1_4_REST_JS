package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.RoleType;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.servise.UserService;
import java.util.Collections;


@Controller
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    UserService service;
    UserRepository repo;

    @GetMapping
    public String addAdmin() {
        service.addDefaultRoles();
        repo.save(new User("Admin",
                "Admin_Last",
                111,
                "admin@admin",
                "admin",
                Collections.singleton(new Role(1L, RoleType.ADMIN))));
        for (int i = 1; i < 25; i++) {
            repo.save(new User("Пользователь " + i,
                    "Фамилия " + i,
                    20 + i,
                    i + "@user",
                    "p" + i, Collections.singleton(new Role(2L, RoleType.USER))));
        }
        return "redirect:/";
    }
}
