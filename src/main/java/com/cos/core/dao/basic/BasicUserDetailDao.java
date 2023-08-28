package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.user.IUserDetailDao;
import org.hibernate.SessionFactory;

public class BasicUserDetailDao extends AbstractDao implements IUserDetailDao {
    public BasicUserDetailDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
