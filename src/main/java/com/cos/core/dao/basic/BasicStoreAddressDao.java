package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.store.IStoreAddressDao;
import org.hibernate.SessionFactory;

public class BasicStoreAddressDao extends AbstractDao implements IStoreAddressDao {
    public BasicStoreAddressDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
