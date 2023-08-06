package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.order.IOrderAddressDao;
import org.hibernate.SessionFactory;

public class DefaultOrderAddressDao<E> extends AbstractDao<E> implements IOrderAddressDao<E> {
    public DefaultOrderAddressDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
