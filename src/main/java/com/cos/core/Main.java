package com.cos.core;


import com.cos.core.dao.IUserDao;
import com.cos.core.dao.UserDao;
import com.cos.core.modal.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.info("test123");
//        Book book = new Book();
//        book.setName("test123");
//        IUserDao<Book> userDao = new UserDao<>();
//        userDao.setClazz(Book.class);
////        SessionFactory sessionFactory = new ConnectionPullConfiguration().createSessionFactoryWithOutHibernateXML();
//        userDao.saveUser(book);
//        System.out.println(userDao.getAllUsers());
//        System.out.println(userDao.getUserByUserName("test123"));
//        createSessionFactoryWithoutXML();
    }

//    private static void saveObject(Book entityTest) {
//        Transaction transaction = null;
//        SessionFactory sessionFactory = new ConnectionPullConfiguration().createSessionFactoryWithOutHibernateXML();
//
//        try (Session session = sessionFactory.getCurrentSession()) {
//            // start a transaction
//            transaction = session.beginTransaction();
//            // save the student object
//            session.persist(entityTest);
//            // commit transaction
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//        }
//    }
//
//    public static List<Book> getObjects() {
//        try (Session session = new ConnectionPullConfiguration().createSessionFactoryWithOutHibernateXML().openSession()) {
//            return session.createQuery("from Book", Book.class).list();
//        }
//    }

    private static void createSessionFactoryWithoutXML() {

        Map<String, Object> settings = new HashMap<>();
        settings.put("hibernate.connection.driver_class", "org.h2.Driver");
        settings.put("hibernate.connection.url", "jdbc:h2:mem:test");
        settings.put("hibernate.connection.username", "sa");
        settings.put("hibernate.connection.password", "");
        settings.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        settings.put("hibernate.current_session_context_class", "thread");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.format_sql", "true");
        settings.put("hibernate.hbm2ddl.auto", "create-drop");

        SessionFactory sessionFactory = null;
        Session session = null;

        try {
            ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(settings).build();

            Metadata metadata = new MetadataSources(standardRegistry)
                    .addAnnotatedClass(Book.class)
                    .getMetadataBuilder()
                    .build();

            sessionFactory = metadata.getSessionFactoryBuilder().build();

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }

        session = sessionFactory.openSession();
        session.beginTransaction();

        Book book = new Book();
        book.setName("aaa");

        session.persist(book);


        session.getTransaction().commit();
        sessionFactory.close();
    }
}
