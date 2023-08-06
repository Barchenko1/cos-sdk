package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.product.IProductDao;
import org.hibernate.SessionFactory;

public class DefaultProductDao<E> extends AbstractDao<E> implements IProductDao<E> {
    public DefaultProductDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
