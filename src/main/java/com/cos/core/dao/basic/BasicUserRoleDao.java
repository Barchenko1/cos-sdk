package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.user.IUserRoleDao;
import org.hibernate.SessionFactory;

public class BasicUserRoleDao extends AbstractDao implements IUserRoleDao {
    public BasicUserRoleDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
