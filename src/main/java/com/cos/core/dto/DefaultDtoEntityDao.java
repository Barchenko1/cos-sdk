package com.cos.core.dto;

import org.hibernate.SessionFactory;

public class DefaultDtoEntityDao<E> extends AbstractDtoEntityDao<E> {

    public DefaultDtoEntityDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
