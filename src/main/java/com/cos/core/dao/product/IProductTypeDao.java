package com.cos.core.dao.product;

import com.cos.core.dao.IEntityDao;

import java.util.Optional;

public interface IProductTypeDao<E> extends IEntityDao<E> {

    Optional<E> getProductTypeByName(String name);
}
