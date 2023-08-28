package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.option.IOptionDao;
import org.hibernate.SessionFactory;

public class BasicOptionDao extends AbstractDao implements IOptionDao {
    public BasicOptionDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
