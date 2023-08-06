package com.cos.core.dao;

import java.util.List;
import java.util.Optional;

public interface IEntityDao<E> {

    void setClazz(Class<E> clazz);
    void saveEntity(E entity);
    void updateEntity(E entity);
    void deleteEntity(E entity);
    List<E> getEntityListBySQLQuery(String sqlQuery);
    List<E> getEntityListBySQLQueryWithParams(String sqlQuery, List<Object> params);
    Optional<E> getEntityBySQLQueryWithParams(String sqlQuery, List<Object> params);

}
