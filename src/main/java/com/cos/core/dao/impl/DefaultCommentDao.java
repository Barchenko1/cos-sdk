package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.post.ICommentDao;
import org.hibernate.SessionFactory;

public class DefaultCommentDao<E> extends AbstractDao<E> implements ICommentDao<E> {
    public DefaultCommentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
