package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.order.IOrderHistoryDao;
import org.hibernate.SessionFactory;

public class BasicOrderHistoryDao<E> extends AbstractDao<E> implements IOrderHistoryDao<E> {
    public BasicOrderHistoryDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
