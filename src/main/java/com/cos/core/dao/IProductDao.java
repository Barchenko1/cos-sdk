package com.cos.core.dao;

import java.util.List;
import java.util.Optional;

public interface IProductDao<E> {

    List<E> getAllProducts();

    Optional<E> getProductByName(String name);

    void saveProduct(E product);

    void updateProduct(E product);

    void deleteProduct(E product);

}
