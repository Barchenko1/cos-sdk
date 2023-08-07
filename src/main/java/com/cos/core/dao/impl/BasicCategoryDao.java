package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.product.ICategoryDao;
import org.hibernate.SessionFactory;

public class BasicCategoryDao<E> extends AbstractDao<E> implements ICategoryDao<E> {
    public BasicCategoryDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
