package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.store.IStoreAddressDao;
import org.hibernate.SessionFactory;

public class DefaultStoreAddressDao<E> extends AbstractDao<E> implements IStoreAddressDao<E> {
    public DefaultStoreAddressDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
