package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.order.IOrderDao;
import org.hibernate.SessionFactory;

public class BasicOrderDao<E> extends AbstractDao<E> implements IOrderDao<E> {
    public BasicOrderDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
