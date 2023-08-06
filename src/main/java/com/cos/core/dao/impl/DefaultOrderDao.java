package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.order.IOrderDao;
import org.hibernate.SessionFactory;

public class DefaultOrderDao<E> extends AbstractDao<E> implements IOrderDao<E> {
    public DefaultOrderDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
