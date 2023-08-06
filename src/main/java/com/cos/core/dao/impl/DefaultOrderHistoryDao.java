package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.order.IOrderHistoryDao;
import org.hibernate.SessionFactory;

public class DefaultOrderHistoryDao<E> extends AbstractDao<E> implements IOrderHistoryDao<E> {
    public DefaultOrderHistoryDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
