package com.cos.core.dao;

import java.util.List;
import java.util.Optional;

public interface IOrderDao<E> {

    List<E> getAllOrders();

    Optional<E> getOrderByName(String name);

    void saveProduct(E product);

    void updateProduct(E product);

    void deleteProduct(E product);
}
