package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.product.IProductDao;
import org.hibernate.SessionFactory;

public class BasicProductDao extends AbstractDao implements IProductDao {
    public BasicProductDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
