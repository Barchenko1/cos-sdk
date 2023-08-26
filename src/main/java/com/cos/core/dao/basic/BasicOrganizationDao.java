package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.org.IOrganizationDao;
import org.hibernate.SessionFactory;

public class BasicOrganizationDao<E> extends AbstractDao<E> implements IOrganizationDao<E> {
    public BasicOrganizationDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
