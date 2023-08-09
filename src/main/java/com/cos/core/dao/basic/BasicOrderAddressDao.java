package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.order.IOrderAddressDao;
import org.hibernate.SessionFactory;

public class BasicOrderAddressDao<E> extends AbstractDao<E> implements IOrderAddressDao<E> {
    public BasicOrderAddressDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
