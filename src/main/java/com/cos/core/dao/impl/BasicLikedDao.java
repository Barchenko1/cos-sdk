package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.liked.ILikedDao;
import org.hibernate.SessionFactory;

public class BasicLikedDao<E> extends AbstractDao<E> implements ILikedDao<E> {
    public BasicLikedDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
