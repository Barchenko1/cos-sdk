package com.cos.core.dao;

import com.cos.core.connect.AbstractDaoConnector;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class UserDao<E> extends AbstractDaoConnector implements IUserDao<E> {

    private SessionFactory sessionFactory;
    public UserDao() {
        sessionFactory = getSessionFactory();
    }

    @Override
    public List<E> getAllUsers() {
        return null;
    }

    @Override
    public Optional<E> getUserByUserName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<E> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void saveUser(E product) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.persist(product);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void updateUser(E product) {

    }

    @Override
    public void deleteUser(E product) {

    }
}
