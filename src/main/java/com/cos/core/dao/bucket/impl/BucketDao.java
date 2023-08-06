package com.cos.core.dao.bucket.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.bucket.IBucketDao;
import com.cos.core.dao.product.impl.ProductDao;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BucketDao<E> extends AbstractDao<E> implements IBucketDao<E> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductDao.class);

    public BucketDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
