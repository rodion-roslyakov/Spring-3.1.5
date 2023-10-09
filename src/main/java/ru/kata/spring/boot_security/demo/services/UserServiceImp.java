package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserServiceImp(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public List<User> index() {
        return entityManager.createQuery("FROM User").getResultList();
    }

    @Override
    @Transactional
    public void delete(int id) {
        entityManager.createQuery("DELETE FROM User WHERE id=?1").setParameter(1, id).executeUpdate();
    }

    public User show(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void update(int id, User user) {
        User updateUser = show(id);
        entityManager.merge(updateUser).setRoles(user.getRoles());
        entityManager.merge(updateUser).setUsername(user.getUsername());
        entityManager.merge(updateUser).setPassword(passwordEncoder.encode(user.getPassword()));
    }


}
