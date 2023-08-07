package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.order.IOrderDetailsDao;
import org.hibernate.SessionFactory;

public class BasicOrderDetailsDao<E> extends AbstractDao<E> implements IOrderDetailsDao<E> {
    public BasicOrderDetailsDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
