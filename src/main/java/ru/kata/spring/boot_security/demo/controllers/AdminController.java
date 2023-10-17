package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.PersonValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PersonValidator personValidator;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(PersonValidator personValidator, UserService userService, RoleService roleService) {
        this.personValidator = personValidator;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listUsers(Model model, Principal principal) {

        model.addAttribute("users", userService.listUsers());
        try {
            model.addAttribute("user", userService.findByUsername(principal.getName()).get());
        } catch (Exception e) {
            return "redirect:/login?error2";
        }
        model.addAttribute("allRoles", roleService.findAll());
        return "list-users";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        User user = userService.showUser(id);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", user.getRoles());
        return "user-profile";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return ("new");
    }

    @PostMapping
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAll());
            return "list-users";
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        User user = userService.showUser(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        model.addAttribute("allRoles", roleService.findAll());
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model, Principal principal) {
        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors() && !user.getUsername().equals(userService.showUser(id).getUsername())) {
            model.addAttribute("allRoles", roleService.findAll());
            return "edit";
        }

        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
