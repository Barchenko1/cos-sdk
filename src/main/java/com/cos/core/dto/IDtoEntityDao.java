package com.cos.core.dto;

import java.util.List;

public interface IDtoEntityDao<E> {

    void setClazz(Class<E> clazz);
    E getDto(String sqlQuery);
    List<E> getDtoList(String sqlQuery);
}
