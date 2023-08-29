package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.business.IStoreDao;
import org.hibernate.SessionFactory;

public class BasicStoreDao extends AbstractDao implements IStoreDao {
    public BasicStoreDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
