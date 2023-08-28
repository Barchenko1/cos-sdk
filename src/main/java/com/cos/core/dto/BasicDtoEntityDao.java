package com.cos.core.dto;

import org.hibernate.SessionFactory;

public class BasicDtoEntityDao extends AbstractDtoEntityDao {
    public BasicDtoEntityDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
