package com.cos.core.dao.impl;


import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.user.IUserAddressDao;
import org.hibernate.SessionFactory;

public class DefaultUserAddressDao<E> extends AbstractDao<E> implements IUserAddressDao<E> {
    public DefaultUserAddressDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
