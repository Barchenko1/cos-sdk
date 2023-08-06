package com.cos.core.dao.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.shop.IShopAddressDao;
import org.hibernate.SessionFactory;

public class DefaultShopAddressDao<E> extends AbstractDao<E> implements IShopAddressDao<E> {
    public DefaultShopAddressDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
