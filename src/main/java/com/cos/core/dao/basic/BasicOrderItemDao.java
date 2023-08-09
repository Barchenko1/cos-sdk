package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.order.IOrderItemDao;
import org.hibernate.SessionFactory;

public class BasicOrderItemDao<E> extends AbstractDao<E> implements IOrderItemDao<E> {
    public BasicOrderItemDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
