package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.shop.IShopDao;
import org.hibernate.SessionFactory;

public class DefaultShopDao<E> extends AbstractDao<E> implements IShopDao<E> {
    public DefaultShopDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
