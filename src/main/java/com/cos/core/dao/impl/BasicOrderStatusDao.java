package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.order.IOrderStatusDao;
import org.hibernate.SessionFactory;

public class BasicOrderStatusDao<E> extends AbstractDao<E> implements IOrderStatusDao<E> {
    public BasicOrderStatusDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
