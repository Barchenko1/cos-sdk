package com.cos.core.dao.impl;

import com.cos.core.dao.IEntityDao;

import java.util.List;
import java.util.Optional;

public interface ITestEntityDao<E> extends IEntityDao<E> {

    List<E> getAllTestEntities();

    Optional<E> getTestEntityById(long id);

    Optional<E> getTestEntityByUser(String name);

    Optional<E> getTestEntityByEmail(String email);

}
