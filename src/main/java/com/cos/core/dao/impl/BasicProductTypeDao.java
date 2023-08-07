package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.product.IProductTypeDao;
import org.hibernate.SessionFactory;

public class BasicProductTypeDao<E> extends AbstractDao<E> implements IProductTypeDao<E> {
    public BasicProductTypeDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
