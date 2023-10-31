package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exception_handling.NoSuchUserException;
import ru.kata.spring.boot_security.demo.exception_handling.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.PersonValidator;


import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class AdminRestController {

    private final RoleService roleService;
    private final UserService userService;
    private final PersonValidator personValidator;

    @Autowired
    public AdminRestController(RoleService roleService, UserService userService, PersonValidator personValidator) {
        this.roleService = roleService;
        this.userService = userService;
        this.personValidator = personValidator;
    }

    @GetMapping("/showAccount")
    public ResponseEntity<User> getAccount(Principal principal){
        return new ResponseEntity<>(userService.findByUsername(principal.getName()).orElse(null),HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {

        User user = userService.showUser(id);
        if (user == null) {
            throw new NoSuchUserException("User c ID = " + id + " нет в БД");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<Collection<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Collection<Role>> getRole(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.showUser(id).getRoles(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addNewUser(@RequestBody @Valid User newUser, BindingResult bindingResult) {
        personValidator.validate(newUser, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new UserNotCreatedException(bindingResult.getFieldErrors().toString());
        }
        userService.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> update(@PathVariable("id") long id, @RequestBody @Valid User updateUser, BindingResult bindingResult) {
        personValidator.validate(updateUser, bindingResult);
        if (bindingResult.hasErrors() && !updateUser.getUsername().equals(userService.showUser(id).getUsername())) {
            throw new UserNotCreatedException(bindingResult.getFieldErrors().toString());
        }

        User user = userService.showUser(id);
        if (user == null) {
            throw new NoSuchUserException("User c ID = " + id + " нет в БД");
        }
        userService.update(id, updateUser);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        User user = userService.showUser(id);
        if (user == null) {
            throw new NoSuchUserException("User c ID = " + id + " нет в БД");
        }
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
