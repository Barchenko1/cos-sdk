package com.cos.core.dao.basic;


import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.user.IUserAddressDao;
import org.hibernate.SessionFactory;

public class BasicUserAddressDao extends AbstractDao implements IUserAddressDao {
    public BasicUserAddressDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
