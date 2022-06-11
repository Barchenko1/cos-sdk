package com.cos.core.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class UserDao<E> extends AbstractDaoConnector<E> implements IUserDao<E> {

    private Class<E> clazz;

    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void setClazz(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<E> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createNamedQuery("getTestEntityAll", clazz)
                    .list();
        }
    }

    @Override
    public Optional<E> getUserByUserName(String name) {
        Transaction transaction = null;
        Optional<E> opt = Optional.empty();

        try (Session session = sessionFactory.openSession()) {
//            transaction = session.beginTransaction();
            opt = Optional.ofNullable(session
                    .createNamedQuery("getTestEntity", clazz)
                    .setParameter(1, name)
                    .getSingleResult());
//            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return opt;
    }

    @Override
    public Optional<E> getUserByEmail(String email) {
        return Optional.empty();
    }

//    @Override
//    public void saveUser(E user) {
//        Transaction transaction = null;
//        try (Session session = sessionFactory.getCurrentSession()) {
//            transaction = session.beginTransaction();
//            session.persist(user);
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//        }
//    }
//
//    @Override
//    public void updateUser(E user) {
//        Transaction transaction = null;
//        try (Session session = sessionFactory.getCurrentSession()) {
//            transaction = session.beginTransaction();
//            session.merge(user);
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//        }
//    }
//
//    @Override
//    public void deleteUser(E user) {
//        Transaction transaction = null;
//        try (Session session = sessionFactory.getCurrentSession()) {
//            transaction = session.beginTransaction();
//            session.remove(user);
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//        }
//    }
}
