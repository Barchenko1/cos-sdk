package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.bucket.IBucketDao;
import org.hibernate.SessionFactory;

public class BasicBucketDao extends AbstractDao implements IBucketDao {
    public BasicBucketDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
