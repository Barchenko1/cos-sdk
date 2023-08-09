package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployeeDao<E> extends AbstractDao<E> implements IEmployeeDao<E> {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeDao.class);

    public EmployeeDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
