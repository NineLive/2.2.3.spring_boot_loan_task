package ru.spring.spring_boot.service;


import ru.spring.spring_boot.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> findAll();

    public Optional<User> findById(long id);

    public void save(User user);

    public void update(long id, User updatedUser);

    public void delete(long id);
}
