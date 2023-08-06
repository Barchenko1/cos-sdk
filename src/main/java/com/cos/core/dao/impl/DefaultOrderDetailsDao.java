package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.order.IOrderDetailsDao;
import org.hibernate.SessionFactory;

public class DefaultOrderDetailsDao<E> extends AbstractDao<E> implements IOrderDetailsDao<E> {
    public DefaultOrderDetailsDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
