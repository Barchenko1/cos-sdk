package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.user.IAppUserDao;
import org.hibernate.SessionFactory;

public class BasicAppUserDao extends AbstractDao implements IAppUserDao {
    public BasicAppUserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
