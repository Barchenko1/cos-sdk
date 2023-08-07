package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.user.IUserDetailsDao;
import org.hibernate.SessionFactory;

public class BasicUserDetailsDao<E> extends AbstractDao<E> implements IUserDetailsDao<E> {
    public BasicUserDetailsDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
