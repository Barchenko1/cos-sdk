package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.org.IOrganizationDao;
import org.hibernate.SessionFactory;

public class BasicOrganizationDao extends AbstractDao implements IOrganizationDao {
    public BasicOrganizationDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
