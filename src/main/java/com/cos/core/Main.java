package com.cos.core;


import com.cos.core.config.HibernateConfiguration;
import com.cos.core.modal.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.info("test");
        Book entityTest = new Book();
        entityTest.setName("aaa");
//        getObjects();
//        HibernateConfiguration hb = new HibernateConfiguration(properties);
        saveObject(entityTest);
//        createSessionFactoryWithoutXML();
        System.out.println(entityTest);
        LOG.info("phone: {}",entityTest);
    }

    private static void saveObject(Book entityTest) {
        Transaction transaction = null;
        SessionFactory sessionFactory = new HibernateConfiguration().getSessionFactory();

        try (Session session = sessionFactory.getCurrentSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.persist(entityTest);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public static List<Book> getObjects() {
        try (Session session = new HibernateConfiguration().getSessionFactory().openSession()) {
            return session.createQuery("from User", Book.class).list();
        }
    }

    private static Properties testProperties() {
        Properties appProps = new Properties();
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

        try {
            appProps.load(new FileInputStream(rootPath + "db.properties"));
        } catch (IOException ex) {
            throw new RuntimeException();
        }
        return appProps;
    }

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
