package com.cos.core.dao;

import com.cos.core.connect.IConnectionPullManager;

import java.util.List;
import java.util.Optional;

public interface IUserDao<E> extends IEntityDao<E> {

    void setClazz(Class<E> clazz);
    List<E> getAllUsers();

    Optional<E> getUserByUserName(String name);

    Optional<E> getUserByEmail(String email);

}
