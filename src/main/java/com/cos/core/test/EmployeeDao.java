package com.cos.core.test;

import com.cos.core.dao.AbstractDao;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployeeDao<E> extends AbstractDao<E> implements IEmployeeDao<E> {
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
