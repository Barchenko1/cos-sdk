package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.order.IOrderAddressDao;
import org.hibernate.SessionFactory;

public class BasicOrderAddressDao extends AbstractDao implements IOrderAddressDao {
    public BasicOrderAddressDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
