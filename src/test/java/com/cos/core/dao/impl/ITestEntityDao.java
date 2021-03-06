package com.cos.core.dao.impl;

import com.cos.core.dao.IEntityDao;

import java.util.List;
import java.util.Optional;

public interface ITestEntityDao<E> extends IEntityDao<E> {

    List<E> getAllUsers();

    Optional<E> getUserByUserName(String name);

    Optional<E> getUserByEmail(String email);

}
