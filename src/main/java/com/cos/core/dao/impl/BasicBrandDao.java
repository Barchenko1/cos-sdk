package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.product.IBrandDao;
import org.hibernate.SessionFactory;

public class BasicBrandDao<E> extends AbstractDao<E> implements IBrandDao<E> {
    public BasicBrandDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
