package com.cos.core.dao;

import java.util.List;
import java.util.Optional;

public class UserDao<E> implements IUserDao<E> {



    @Override
    public List<E> getAllUsers() {
        return null;
    }

    @Override
    public Optional<E> getUserByUserName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<E> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void saveUser(E product) {

    }

    @Override
    public void updateUser(E product) {

    }

    @Override
    public void deleteUser(E product) {

    }
}
