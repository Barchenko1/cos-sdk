package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.user.IUserPaymentDao;
import org.hibernate.SessionFactory;

public class DefaultUserPaymentDao<E> extends AbstractDao<E> implements IUserPaymentDao<E> {
    public DefaultUserPaymentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
