package com.cos.core.dto;

import java.util.List;

public interface IDtoEntityDao {

    void setClazz(Class<?> clazz);
    <E> E getDto(String sqlQuery);
    <E> List<E> getDtoList(String sqlQuery);
}
