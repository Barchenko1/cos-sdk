package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.product.IBrandDao;
import org.hibernate.SessionFactory;

public class BasicBrandDao extends AbstractDao implements IBrandDao {
    public BasicBrandDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
