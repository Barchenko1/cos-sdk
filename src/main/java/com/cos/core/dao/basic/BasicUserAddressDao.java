package com.cos.core.dao.basic;


import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.user.IUserAddressDao;
import org.hibernate.SessionFactory;

public class BasicUserAddressDao<E> extends AbstractDao<E> implements IUserAddressDao<E> {
    public BasicUserAddressDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
