package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.order.IOrderStatusDao;
import org.hibernate.SessionFactory;

public class DefaultOrderStatusDao<E> extends AbstractDao<E> implements IOrderStatusDao<E> {
    public DefaultOrderStatusDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
