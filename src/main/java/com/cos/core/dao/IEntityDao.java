package com.cos.core.dao;

public interface IEntityDao<E> {

    void setClazz(Class<E> clazz);
    void saveEntity(E user);
    void updateEntity(E user);
    void deleteEntity(E user);

}
