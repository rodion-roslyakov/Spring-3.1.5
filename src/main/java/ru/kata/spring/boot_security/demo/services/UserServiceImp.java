package ru.kata.spring.boot_security.demo.services;

import org.apache.catalina.realm.GenericPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.registry.infomodel.Organization;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User showUser(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void update(long id, User user) {
        User updateUser = showUser(id);
        updateUser.setRoles(user.getRoles());
        updateUser.setUsername(user.getUsername());
        updateUser.setFirstName(user.getFirstName());
        updateUser.setSecondName(user.getSecondName());
        updateUser.setAge(user.getAge());
        if (!updateUser.getPassword().equals(user.getPassword())) {
            updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.save(updateUser);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
