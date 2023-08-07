package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.store.IStoreDao;
import org.hibernate.SessionFactory;

public class BasicStoreDao<E> extends AbstractDao<E> implements IStoreDao<E> {
    public BasicStoreDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
