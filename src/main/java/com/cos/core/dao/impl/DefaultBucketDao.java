package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.bucket.IBucketDao;
import org.hibernate.SessionFactory;

public class DefaultBucketDao<E> extends AbstractDao<E> implements IBucketDao<E> {
    public DefaultBucketDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
