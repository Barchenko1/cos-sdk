package com.cos.core.dao.product;

import com.cos.core.dao.IEntityDao;

import java.util.List;
import java.util.Optional;

public interface ICategoryDao<E> extends IEntityDao<E> {

    List<E> getAllCategories();

    Optional<E> getCategoryByName(String name);
}
