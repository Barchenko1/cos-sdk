package com.cos.core;

import com.cos.core.modal.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.c3p0.internal.C3P0ConnectionProvider;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SessionFactoryCPDB2Test {

  @Test
  void createSessionFactoryWithoutXML() {

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

    settings.put(Environment.C3P0_MIN_SIZE, 5); //Minimum size of pool
    settings.put(Environment.C3P0_MAX_SIZE, 20); //Maximum size of pool
    settings.put(Environment.C3P0_ACQUIRE_INCREMENT, 1); //Number of connections acquired at a time when pool is exhausted
    settings.put(Environment.C3P0_TIMEOUT, 1800); //Connection idle time
    settings.put(Environment.C3P0_MAX_STATEMENTS, 150); //PreparedStatement cache size
    settings.put(Environment.CONNECTION_PROVIDER, C3P0ConnectionProvider.class);
    settings.put(Environment.C3P0_CONFIG_PREFIX+".initialPoolSize", 5);

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
    book.setName("test");

    session.persist(book);

    Assertions.assertNotNull(book.getId());

    session.getTransaction().commit();
    sessionFactory.close();
  }

  @Test
  void createSessionFactoryWithXML() {
    SessionFactory sessionFactory = null;
    Session session = null;

    try {
      StandardServiceRegistry standardRegistry
          = new StandardServiceRegistryBuilder().configure()
          .build();

      Metadata metadata = new MetadataSources(standardRegistry)
          .getMetadataBuilder()
          .build();

      sessionFactory = metadata.getSessionFactoryBuilder().build();

    } catch (Throwable ex) {
      throw new ExceptionInInitializerError(ex);
    }

    session = sessionFactory.openSession();
    session.beginTransaction();

    Book book = new Book();
    book.setName("test");

    session.persist(book);

    Assertions.assertNotNull(book.getId());

    session.getTransaction().commit();
    sessionFactory.close();
  }
}
