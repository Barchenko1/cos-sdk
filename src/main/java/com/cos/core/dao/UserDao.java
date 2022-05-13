package com.cos.core.dao;

import com.cos.core.connect.AbstractDaoConnector;
import com.cos.core.modal.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class UserDao<E> extends AbstractDaoConnector implements IUserDao<E> {

    private Class<E> clazz;
    private final SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void setClazz(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<E> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createNamedQuery("book.selectAll", clazz).list();
        }
    }

    @Override
    public Optional<E> getUserByUserName(String name) {
        Transaction transaction = null;
        Optional<E> opt = Optional.empty();

        try (Session session = sessionFactory.openSession()) {
//            transaction = session.beginTransaction();
//            session.createNamedQuery("book.select", clazz).setParameter("name", "test123").getSingleResult();
            opt = Optional.ofNullable(session.createNativeQuery("select b from Book b where b.name =: name ", clazz).setParameter("name", name).getSingleResult());
//            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        System.out.println(opt.get());
        return opt;
    }

    @Override
    public Optional<E> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void saveUser(E user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void updateUser(E user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void deleteUser(E user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
