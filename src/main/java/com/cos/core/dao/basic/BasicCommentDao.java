package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.post.ICommentDao;
import org.hibernate.SessionFactory;

public class BasicCommentDao extends AbstractDao implements ICommentDao {
    public BasicCommentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
