package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.servise.UserService;

import java.security.Principal;

@Controller
public class DefaultController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping
    public String index(Model model, Principal principal) {
        model.addAttribute("user", userService.loadUserModelByUsername(principal.getName()));
        return "index";
    }
}