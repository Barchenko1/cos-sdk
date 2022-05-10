package com.cos.core.dao;

import java.util.List;
import java.util.Optional;

public interface ITypeDao<E> {

    Optional<E> getTypeByName(String name);

    List<E> getAllTypes();


}
