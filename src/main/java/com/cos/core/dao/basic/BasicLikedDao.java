package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.liked.ILikedDao;
import org.hibernate.SessionFactory;

public class BasicLikedDao extends AbstractDao implements ILikedDao {
    public BasicLikedDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
