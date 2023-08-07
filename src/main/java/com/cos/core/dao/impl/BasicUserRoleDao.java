package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.user.IUserRoleDao;
import org.hibernate.SessionFactory;

public class BasicUserRoleDao<E> extends AbstractDao<E> implements IUserRoleDao<E> {
    public BasicUserRoleDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
