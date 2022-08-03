package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.servise.UserService;


import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
@EnableWebSecurity
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model, Principal principal) {
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        model.addAttribute("allRoles", userService.getAllRole());
        model.addAttribute("userNew", new User());
        model.addAttribute("userList", userService.getAll());
        return "/index";
    }

    @RequestMapping(value = "/update_{id}", method = RequestMethod.POST)
    public String update(@ModelAttribute("userAct") @Valid User user, BindingResult bindingResult,
                         @RequestParam(value = "rolNewUser", required = false) List<String> role) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin#edit_" + user.getId();
        }
        userService.updateUser(user, role);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/delete_{id}", method = RequestMethod.POST)
    public String delete(@PathVariable("id") long id) {
        userService.remove(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("userNew") @Valid User userNew,
                             BindingResult bindingResult, @RequestParam(value = "rolNewUser", required = false) List<String> role) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin#new";
        }
        userService.add(userNew, role);
        return "redirect:/admin";
    }


}