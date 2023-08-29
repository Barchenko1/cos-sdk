package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.business.IShopDao;
import org.hibernate.SessionFactory;

public class BasicShopDao extends AbstractDao implements IShopDao {
    public BasicShopDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
