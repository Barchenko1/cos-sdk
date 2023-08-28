package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.product.IProductStatusDao;
import org.hibernate.SessionFactory;

public class BasicProductStatusDao extends AbstractDao implements IProductStatusDao {
    public BasicProductStatusDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
