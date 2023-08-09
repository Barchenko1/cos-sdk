package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.product.IProductDao;
import org.hibernate.SessionFactory;

public class BasicProductDao<E> extends AbstractDao<E> implements IProductDao<E> {
    public BasicProductDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
