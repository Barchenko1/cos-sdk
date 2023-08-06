package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.product.IDiscountDao;
import org.hibernate.SessionFactory;

public class DefaultDiscountDao<E> extends AbstractDao<E> implements IDiscountDao<E> {
    public DefaultDiscountDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
