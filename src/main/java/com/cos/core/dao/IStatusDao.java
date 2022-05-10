package com.cos.core.dao;

import java.util.Optional;

public interface IStatusDao<E> {

    Optional<E> getStatusByName(String name);

}
