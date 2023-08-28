package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.rating.IRatingDao;
import org.hibernate.SessionFactory;

public class BasicRatingDao extends AbstractDao implements IRatingDao {
    public BasicRatingDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
