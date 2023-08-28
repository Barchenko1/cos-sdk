package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.org.ITenantConfigDao;
import org.hibernate.SessionFactory;

public class BasicTenantConfigurationDao extends AbstractDao implements ITenantConfigDao {
    public BasicTenantConfigurationDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
