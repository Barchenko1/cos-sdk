package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.review.IReviewDao;
import org.hibernate.SessionFactory;

public class BasicReviewDao extends AbstractDao implements IReviewDao {
    public BasicReviewDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
