package com.cos.core;

import com.cos.core.connect.ConnectionPullManager;
import com.cos.core.connect.IConnectionPullManager;
import com.cos.core.dao.IUserDao;
import com.cos.core.dao.UserDao;
import com.cos.core.modal.Book;

import com.cos.core.properties.modal.ConnectionDetails;
import org.hibernate.SessionFactory;
import org.hibernate.c3p0.internal.C3P0ConnectionProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SessionFactoryC3P0Test {

  @Test
  void createSessionFactoryWithProperties() {

    IConnectionPullManager connectionPullManager = new ConnectionPullManager();

    List<Class<?>> annotationList = new ArrayList<>();
    annotationList.add(Book.class);
    Class<?>[] annotationClasses = annotationList.toArray(new Class<?>[0]);
    connectionPullManager.setAnnotatedClasses(annotationClasses);
    Book book = new Book();
    SessionFactory sessionFactory =
            connectionPullManager.getConfigureSessionFactoryByProperties("c3p0.db.properties");

    IUserDao<Book> userDao = new UserDao<>(sessionFactory);
    userDao.setClazz(Book.class);
    book.setName("testprops");

    userDao.saveUser(book);

    Assertions.assertNotNull(book.getId());
  }

  @Test
  void createSessionFactoryWithXML() {
    IConnectionPullManager connectionPullManager = new ConnectionPullManager();

    Book book = new Book();
    SessionFactory sessionFactory =
            connectionPullManager.getConfigureSessionFactoryByXML("c3p0.hibernate.cfg.xml");
    IUserDao<Book> userDao = new UserDao<>(sessionFactory);
    userDao.setClazz(Book.class);
    book.setName("testxml");

    userDao.saveUser(book);

    Assertions.assertNotNull(book.getId());
  }

  @Test
  public void createDefaultSessionFactory() {
    IConnectionPullManager connectionPullManager = new ConnectionPullManager();

    List<Class<?>> annotationList = new ArrayList<>();
    annotationList.add(Book.class);
    Class<?>[] annotationClasses = annotationList.toArray(new Class<?>[0]);
    connectionPullManager.setAnnotatedClasses(annotationClasses);
    ConnectionDetails connectionDetails = ConnectionDetails.newBuilder()
            .setDriver("org.h2.Driver")
            .setUrl("jdbc:h2:mem:test")
            .setUserName("sa")
            .setPassword("")
            .setDialect("org.hibernate.dialect.H2Dialect")
            .setConnectionPullProviderClass(C3P0ConnectionProvider.class)
            .setInitialSize(0)
            .setMinIdle(5)
            .setMaxIdle(5)
            .setMaxTotal(0)
            .build();
    connectionPullManager.setConnectionDetails(connectionDetails);
    Book book = new Book();
    SessionFactory sessionFactory = connectionPullManager.getConfigureSessionFactoryByDefault();

    IUserDao<Book> userDao = new UserDao<>(sessionFactory);
    userDao.setClazz(Book.class);
    book.setName("testprops");

    userDao.saveUser(book);

    Assertions.assertNotNull(book.getId());
  }
}
