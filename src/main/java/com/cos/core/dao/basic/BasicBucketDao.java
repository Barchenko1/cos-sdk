package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.bucket.IBucketDao;
import org.hibernate.SessionFactory;

public class BasicBucketDao<E> extends AbstractDao<E> implements IBucketDao<E> {
    public BasicBucketDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
