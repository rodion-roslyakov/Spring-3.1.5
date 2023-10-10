package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    User showUser(long id);

    void save(User user);


    List<User> listUsers();


    void delete(long id);

    void update(long id, User user);

}
