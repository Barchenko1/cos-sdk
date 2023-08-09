package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.store.IStoreAddressDao;
import org.hibernate.SessionFactory;

public class BasicStoreAddressDao<E> extends AbstractDao<E> implements IStoreAddressDao<E> {
    public BasicStoreAddressDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
