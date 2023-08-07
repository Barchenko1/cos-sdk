package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.option.IOptionDao;
import org.hibernate.SessionFactory;

public class BasicOptionDao<E> extends AbstractDao<E> implements IOptionDao<E> {
    public BasicOptionDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
