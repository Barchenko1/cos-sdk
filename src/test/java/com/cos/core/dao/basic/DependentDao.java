package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DependentDao extends AbstractDao implements IDependentDao {
    private static final Logger LOG = LoggerFactory.getLogger(DependentDao.class);

    public DependentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
