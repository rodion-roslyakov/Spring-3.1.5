package ru.kata.spring.boot_security.demo.initiation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public DatabaseInitializer(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        //Создали роль админ
        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");
        roleService.save(adminRole);

        //Создали роль юзер
        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        roleService.save(userRole);

        List<Role> userRoles = List.of(userRole);
        List<Role> adminRoles = Arrays.asList(adminRole, userRole);

        User admin = new User();
        admin.setUsername("admin@mail.ru");
        admin.setPassword("admin");
        admin.setFirstName("Rodion");
        admin.setSecondName("Roslyakov");
        admin.setAge(21);
        admin.setRoles(adminRoles);
        userService.save(admin);

        User user = new User();
        user.setUsername("user@mail.ru");
        user.setPassword("user");
        user.setFirstName("Andrew");
        user.setSecondName("Malyshew");
        user.setAge(22);
        user.setRoles(userRoles);
        userService.save(user);
    }
}