package com.cos.core.dao;

import java.util.List;
import java.util.Optional;

public interface IUserDao<E> {

    List<E> getAllUsers();

    Optional<E> getUserByUserName(String name);

    Optional<E> getUserByEmail(String email);

    void saveUser(E product);

    void updateUser(E product);

    void deleteUser(E product);
}
