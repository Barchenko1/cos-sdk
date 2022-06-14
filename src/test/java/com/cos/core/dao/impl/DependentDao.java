package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDaoConnector;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DependentDao<E> extends AbstractDaoConnector<E> implements IDependentDao<E> {
    private static final Logger LOG = LoggerFactory.getLogger(DependentDao.class);

    public DependentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void setClazz(Class<E> clazz) {
        this.clazz = clazz;
    }

}
