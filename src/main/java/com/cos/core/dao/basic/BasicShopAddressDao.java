package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.shop.IShopAddressDao;
import org.hibernate.SessionFactory;

public class BasicShopAddressDao<E> extends AbstractDao<E> implements IShopAddressDao<E> {
    public BasicShopAddressDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
