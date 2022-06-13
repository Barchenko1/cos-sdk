package com.cos.core.dao.test;

import com.cos.core.dao.AbstractDaoConnector;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class TestEntityDao<E> extends AbstractDaoConnector<E> implements ITestEntityDao<E> {
    private static final Logger LOG = LoggerFactory.getLogger(TestEntityDao.class);

    public TestEntityDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void setClazz(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<E> getAllUsers() {
        List<E> users;
        try (Session session = sessionFactory.openSession()) {
            users = session.createNamedQuery("getTestEntityAll", clazz)
                    .list();
        }
        return users;
    }

    @Override
    public Optional<E> getUserByUserName(String name) {
        Optional<E> opt;
        try (Session session = sessionFactory.openSession()) {
            opt = Optional.ofNullable(session
                    .createNamedQuery("getTestEntity", clazz)
                    .setParameter(1, name)
                    .getSingleResult());
        } catch (Exception e) {
            LOG.warn("get entity error {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return opt;
    }

    @Override
    public Optional<E> getUserByEmail(String email) {
        return Optional.empty();
    }

}
