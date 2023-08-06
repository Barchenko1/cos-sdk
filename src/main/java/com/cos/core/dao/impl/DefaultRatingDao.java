package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.rating.IRatingDao;
import org.hibernate.SessionFactory;

public class DefaultRatingDao<E> extends AbstractDao<E> implements IRatingDao<E> {
    public DefaultRatingDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
