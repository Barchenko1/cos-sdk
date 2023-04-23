package com.cos.core.dao.user.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.user.IAppUserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createNativeQuery(GET_APP_USER_LIST, clazz)
                    .list();
        } catch (Exception e) {
            LOG.warn("get entity error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<E> getUserByUserName(String username) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session
                    .createNativeQuery(GET_APP_USER_BY_USERNAME, clazz)
                    .setParameter(1, username)
                    .getSingleResult());
        } catch (Exception e) {
            LOG.warn("get entity error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<E> getUserByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session
                    .createNativeQuery(GET_APP_USER_BY_EMAIL, clazz)
                    .setParameter(1, email)
                    .getSingleResult());
        } catch (Exception e) {
            LOG.warn("get entity error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
