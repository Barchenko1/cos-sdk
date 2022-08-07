package com.cos.core.dao.order;

import com.cos.core.dao.IEntityDao;

import java.util.Optional;

public interface IOrderStatusDao<E> extends IEntityDao<E> {

    Optional<E> getStatusByName(String name);

}
