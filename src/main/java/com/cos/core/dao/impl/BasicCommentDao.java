package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.post.ICommentDao;
import org.hibernate.SessionFactory;

public class BasicCommentDao<E> extends AbstractDao<E> implements ICommentDao<E> {
    public BasicCommentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
