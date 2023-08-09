package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.user.IAppUserDao;
import org.hibernate.SessionFactory;

public class BasicAppUserDao<E> extends AbstractDao<E> implements IAppUserDao<E> {
    public BasicAppUserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
