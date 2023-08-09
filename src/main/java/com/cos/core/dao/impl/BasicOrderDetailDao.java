package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.order.IOrderDetailDao;
import org.hibernate.SessionFactory;

public class BasicOrderDetailDao<E> extends AbstractDao<E> implements IOrderDetailDao<E> {
    public BasicOrderDetailDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
