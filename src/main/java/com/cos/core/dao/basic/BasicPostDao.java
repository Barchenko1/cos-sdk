package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.post.IPostDao;
import org.hibernate.SessionFactory;

public class BasicPostDao extends AbstractDao implements IPostDao {
    public BasicPostDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
