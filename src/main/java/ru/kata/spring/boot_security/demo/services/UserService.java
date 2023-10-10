package ru.kata.spring.boot_security.demo.services;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.models.User;

import javax.xml.registry.infomodel.Organization;
import java.util.List;

public interface UserService {

    User showUser(long id);

    void save(User user);


    List<User> listUsers();


    void delete(long id);

    void update(long id, User user);

}
