package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.order.IOrderStatusDao;
import org.hibernate.SessionFactory;

public class BasicOrderStatusDao extends AbstractDao implements IOrderStatusDao {
    public BasicOrderStatusDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
