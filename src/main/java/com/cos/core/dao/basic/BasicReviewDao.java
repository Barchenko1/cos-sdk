package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.review.IReviewDao;
import org.hibernate.SessionFactory;

public class BasicReviewDao<E> extends AbstractDao<E> implements IReviewDao<E> {
    public BasicReviewDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
