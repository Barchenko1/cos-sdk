package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.product.IDiscountDao;
import org.hibernate.SessionFactory;

public class BasicDiscountDao<E> extends AbstractDao<E> implements IDiscountDao<E> {
    public BasicDiscountDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
