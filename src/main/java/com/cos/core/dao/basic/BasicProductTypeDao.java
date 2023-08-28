package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.product.IProductTypeDao;
import org.hibernate.SessionFactory;

public class BasicProductTypeDao extends AbstractDao implements IProductTypeDao {
    public BasicProductTypeDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
