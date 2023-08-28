package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.product.IDiscountDao;
import org.hibernate.SessionFactory;

public class BasicDiscountDao extends AbstractDao implements IDiscountDao {
    public BasicDiscountDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
