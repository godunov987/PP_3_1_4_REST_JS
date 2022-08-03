package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DefaultController {

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping
    public String logOut() {
        return "redirect:/logout";
    }
}
