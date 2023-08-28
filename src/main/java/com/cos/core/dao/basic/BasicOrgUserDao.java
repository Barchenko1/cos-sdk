package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.org.IOrgUserDao;
import org.hibernate.SessionFactory;

public class BasicOrgUserDao extends AbstractDao implements IOrgUserDao {
    public BasicOrgUserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
