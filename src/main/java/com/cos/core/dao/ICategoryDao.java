package com.cos.core.dao;

import java.util.List;
import java.util.Optional;

public interface ICategoryDao<E> {

    List<E> getAllCategories();

    Optional<E> getCategoryByName(String name);

    void saveCategory(E category);

    void updateCategory(E category);

    void deleteCategory(E category);
}
