package com.cos.core.dto;

import org.hibernate.SessionFactory;

public class BasicDtoEntityDao<E> extends AbstractDtoEntityDao<E> {
    public BasicDtoEntityDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
