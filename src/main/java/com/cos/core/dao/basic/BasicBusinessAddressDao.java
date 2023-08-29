package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.business.IBusinessAddressDao;
import org.hibernate.SessionFactory;

public class BasicBusinessAddressDao extends AbstractDao implements IBusinessAddressDao {
    public BasicBusinessAddressDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
