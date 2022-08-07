package com.cos.core.dao.order;

import com.cos.core.dao.IEntityDao;

import java.util.List;
import java.util.Optional;

public interface IOrderDao<E> extends IEntityDao<E> {

    List<E> getAllOrders();

    Optional<E> getOrderByName(String name);
}
