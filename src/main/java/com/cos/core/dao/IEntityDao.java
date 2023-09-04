package com.cos.core.dao;

import java.util.List;
import java.util.Optional;

public interface IEntityDao {

    void setClazz(Class<?> clazz);
    <E> void saveEntity(E entity);
    <E> void updateEntity(E entity);
    <E> void deleteEntity(E entity);
    <E> List<E> getEntityListBySQLQuery(String sqlQuery);
    <E> E getEntityBySQLQuery(String sqlQuery);
    <E> Optional<E> getOptionEntityBySQLQuery(String sqlQuery);
    <E> List<E> getEntityListBySQLQueryWithParams(String sqlQuery, List<Object> params);
    <E> Optional<E> getEntityBySQLQueryWithParams(String sqlQuery, List<Object> params);

}
