package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.org.ITenantDao;
import org.hibernate.SessionFactory;

public class BasicTenantDao<E> extends AbstractDao<E> implements ITenantDao<E> {
    public BasicTenantDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
