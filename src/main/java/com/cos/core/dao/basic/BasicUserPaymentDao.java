package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.user.IUserPaymentDao;
import org.hibernate.SessionFactory;

public class BasicUserPaymentDao extends AbstractDao implements IUserPaymentDao {
    public BasicUserPaymentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
