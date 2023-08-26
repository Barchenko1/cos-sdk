package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.org.IOrgUserDao;
import org.hibernate.SessionFactory;

public class BasicOrgUserDao<E> extends AbstractDao<E> implements IOrgUserDao<E> {
    public BasicOrgUserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
