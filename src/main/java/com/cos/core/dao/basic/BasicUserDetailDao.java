package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.user.IUserDetailDao;
import org.hibernate.SessionFactory;

public class BasicUserDetailDao<E> extends AbstractDao<E> implements IUserDetailDao<E> {
    public BasicUserDetailDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
