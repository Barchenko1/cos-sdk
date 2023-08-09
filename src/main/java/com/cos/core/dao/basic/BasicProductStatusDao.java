package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.product.IProductStatusDao;
import org.hibernate.SessionFactory;

public class BasicProductStatusDao<E> extends AbstractDao<E> implements IProductStatusDao<E> {
    public BasicProductStatusDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
