package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.shop.IShopDao;
import org.hibernate.SessionFactory;

public class BasicShopDao<E> extends AbstractDao<E> implements IShopDao<E> {
    public BasicShopDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
