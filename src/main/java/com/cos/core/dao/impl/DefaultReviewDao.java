package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.review.IReviewDao;
import org.hibernate.SessionFactory;

public class DefaultReviewDao<E> extends AbstractDao<E> implements IReviewDao<E> {
    public DefaultReviewDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
