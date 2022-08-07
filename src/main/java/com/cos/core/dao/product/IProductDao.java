package com.cos.core.dao.product;

import com.cos.core.dao.IEntityDao;

import java.util.List;
import java.util.Optional;

public interface IProductDao<E> extends IEntityDao<E> {

    List<E> getAllProducts();

    Optional<E> getProductByName(String name);

}
