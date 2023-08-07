package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.option.IOptionGroupDao;
import org.hibernate.SessionFactory;

public class BasicOptionGroupDao<E> extends AbstractDao<E> implements IOptionGroupDao<E> {
    public BasicOptionGroupDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
