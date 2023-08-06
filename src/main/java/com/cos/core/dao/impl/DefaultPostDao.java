package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.post.IPostDao;
import org.hibernate.SessionFactory;

public class DefaultPostDao<E> extends AbstractDao<E> implements IPostDao<E> {
    public DefaultPostDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
