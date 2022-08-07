package com.cos.core.dao;

public interface IEntityDao<E> {

    void setClazz(Class<E> clazz);
    void saveEntity(E entity);
    void updateEntity(E entity);
    void deleteEntity(E entity);

}
