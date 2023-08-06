package com.cos.core.dao.user.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.user.IAppUserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.core.im.query.AppUserQuery.*;

public class AppUserDao<E> extends AbstractDao<E> implements IAppUserDao<E> {

    private static final Logger LOG = LoggerFactory.getLogger(AppUserDao.class);

    public AppUserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<E> getAllUsers() {
        return getEntityListBySQLQuery(GET_APP_USER_LIST);
    }

    @Override
    public Optional<E> getUserByUserName(String username) {
        List<Object> params = Collections.singletonList(username);
        return super.getEntityBySQLQueryWithParams(GET_APP_USER_BY_USERNAME, params);
    }

    @Override
    public Optional<E> getUserByEmail(String email) {
        List<Object> params = Collections.singletonList(email);
        return super.getEntityBySQLQueryWithParams(GET_APP_USER_BY_EMAIL, params);
    }
}
