package com.cos.core.dao;

public interface IEntityDao<E> {

    void saveEntity(E user);

    void updateEntity(E user);

    void deleteEntity(E user);

}
