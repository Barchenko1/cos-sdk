package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDaoConnector;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class EmployeeDao<E> extends AbstractDaoConnector<E> implements IEmployeeDao<E> {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeDao.class);

    public EmployeeDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void setClazz(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void saveEntity(E user) {
        super.saveEntity(user);
    }
}
