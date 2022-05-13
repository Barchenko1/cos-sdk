package com.cos.core;

import com.cos.core.connect.ConnectionPullManager;
import com.cos.core.connect.IConnectionPullManager;
import com.cos.core.dao.IUserDao;
import com.cos.core.dao.UserDao;
import com.cos.core.modal.Book;

import org.hibernate.SessionFactory;
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
    SessionFactory sessionFactory = connectionPullManager.getConfigureSessionFactory();

    IUserDao<Book> userDao = new UserDao<>(sessionFactory);
    userDao.setClazz(Book.class);
    book.setName("testprops");

    userDao.saveUser(book);

    Assertions.assertNotNull(book.getId());
  }

  @Test
  void createSessionFactoryWithXML() {
    IConnectionPullManager connectionPullManager = new ConnectionPullManager();

    List<Class<?>> annotationList = new ArrayList<>();
    annotationList.add(Book.class);
    Class<?>[] annotationClasses = annotationList.toArray(new Class<?>[0]);
    connectionPullManager.setAnnotatedClasses(annotationClasses);

    Book book = new Book();
    SessionFactory sessionFactory = connectionPullManager.getConfigureSessionFactory();
    IUserDao<Book> userDao = new UserDao<>(sessionFactory);
    userDao.setClazz(Book.class);
    book.setName("testprops");
    book.setName("testxml");

    Assertions.assertNotNull(book.getId());
  }
}
