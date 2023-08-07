package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.viewed.IViewedDao;
import org.hibernate.SessionFactory;

public class BasicViewedDao<E> extends AbstractDao<E> implements IViewedDao<E> {
    public BasicViewedDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
