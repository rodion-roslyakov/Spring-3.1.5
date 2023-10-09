package ru.kata.spring.boot_security.demo.services;







import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    User show(int id);

    void save(User user);


    List<User> index();


    void delete(int id);

    List<User> findAll();

    void update(int id, User user);

}
