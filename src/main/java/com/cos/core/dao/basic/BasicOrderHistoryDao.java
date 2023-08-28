package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.order.IOrderHistoryDao;
import org.hibernate.SessionFactory;

public class BasicOrderHistoryDao extends AbstractDao implements IOrderHistoryDao {
    public BasicOrderHistoryDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
