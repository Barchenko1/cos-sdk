package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.liked.ILikedDao;
import org.hibernate.SessionFactory;

public class DefaultLikedDao<E> extends AbstractDao<E> implements ILikedDao<E> {
    public DefaultLikedDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
