package com.cos.core.test;

import com.cos.core.dao.AbstractDao;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DependentDao<E> extends AbstractDao<E> implements IDependentDao<E> {
    private static final Logger LOG = LoggerFactory.getLogger(DependentDao.class);

    public DependentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void setClazz(Class<E> clazz) {
        this.clazz = clazz;
    }

}
