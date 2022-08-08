package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.RoleType;
import ru.kata.spring.boot_security.demo.model.UserModel;
import ru.kata.spring.boot_security.demo.servise.UserService;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@EnableWebSecurity
@RequestMapping(value = "/api")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/json")
    public ResponseEntity <?> getAllUsers(@RequestHeader ("type") String type) {
        try {
            if(type.equals("all")){
                return ResponseEntity.ok(userService.getAll());
            }
            return ResponseEntity.ok(userService.getUser(Long.parseLong(type)));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("ERROR: Не удалось получить пользователя/ей ::: " + e.getMessage());
        }
    }

    @GetMapping(value = "/roles")
    public ResponseEntity <List<RoleType>> getAllRoles(){
        return ResponseEntity.ok(userService.getAllRole().stream().map(Role::getRole).collect(Collectors.toList()));
    }

    @PutMapping(value = "/add")
    public ResponseEntity <UserModel> update(@RequestBody UserModel updateUser) {
        return ResponseEntity.ok(userService.updateUser(updateUser));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity <String> delete(@RequestHeader ("id") long id) {
        try {
            userService.remove(id);
            return ResponseEntity.ok("User id=" + id + " deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR: The user with this ID " + id + " could not be deleted! : " + e.getMessage());
        }
    }

    @PostMapping(value = "/add")
    public ResponseEntity <String> createUser(@RequestBody UserModel userNew) {

        try {
            userService.add(userNew);
            return ResponseEntity.ok("User " + userNew.getName() + " created");
        } catch (Exception uEx) {
            return ResponseEntity.badRequest().body("ERROR: Didn't create user " + userNew.getName());
        }
    }


}