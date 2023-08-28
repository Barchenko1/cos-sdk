package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.viewed.IViewedDao;
import org.hibernate.SessionFactory;

public class BasicViewedDao extends AbstractDao implements IViewedDao {
    public BasicViewedDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
