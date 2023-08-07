package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.rating.IRatingDao;
import org.hibernate.SessionFactory;

public class BasicRatingDao<E> extends AbstractDao<E> implements IRatingDao<E> {
    public BasicRatingDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
