package com.cos.core.dao.basic;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.option.IOptionGroupDao;
import org.hibernate.SessionFactory;

public class BasicOptionGroupDao extends AbstractDao implements IOptionGroupDao {
    public BasicOptionGroupDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
