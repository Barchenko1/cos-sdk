package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.order.IOrderItemDao;
import org.hibernate.SessionFactory;

public class BasicOrderItemDao extends AbstractDao implements IOrderItemDao {
    public BasicOrderItemDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
