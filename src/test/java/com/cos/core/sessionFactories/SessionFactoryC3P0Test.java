package com.cos.core.sessionFactories;

import com.cos.core.connect.ConnectionPullManager;
import com.cos.core.connect.IConnectionPullManager;
import com.cos.core.dao.IUserDao;
import com.cos.core.dao.impl.TestEntityDao;
import com.cos.core.modal.TestEntity;

import com.cos.core.properties.modal.ConnectionDetails;
import com.zaxxer.hikari.hibernate.HikariConnectionProvider;
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
    annotationList.add(TestEntity.class);
    Class<?>[] annotationClasses = annotationList.toArray(new Class<?>[0]);
    connectionPullManager.setAnnotatedClasses(annotationClasses);
    TestEntity testEntity = new TestEntity();
    SessionFactory sessionFactory =
            connectionPullManager.getConfigureSessionFactoryByProperties("c3p0.db.properties");

    IUserDao<TestEntity> userDao = new TestEntityDao<>(sessionFactory);
    userDao.setClazz(TestEntity.class);
    testEntity.setName("testprops");

    userDao.saveEntity(testEntity);

    Assertions.assertNotNull(testEntity.getId());
  }

  @Test
  void createSessionFactoryWithXML() {
    IConnectionPullManager connectionPullManager = new ConnectionPullManager();

    TestEntity testEntity = new TestEntity();
    SessionFactory sessionFactory =
            connectionPullManager.getConfigureSessionFactoryByXML("c3p0.hibernate.cfg.xml");
    IUserDao<TestEntity> userDao = new TestEntityDao<>(sessionFactory);
    userDao.setClazz(TestEntity.class);
    testEntity.setName("testxml");

    userDao.saveEntity(testEntity);

    Assertions.assertEquals(1, testEntity.getId());
  }

  @Test
  public void createDefaultSessionFactory() {
    IConnectionPullManager connectionPullManager = new ConnectionPullManager();

    List<Class<?>> annotationList = new ArrayList<>();
    annotationList.add(TestEntity.class);
    Class<?>[] annotationClasses = annotationList.toArray(new Class<?>[0]);
    connectionPullManager.setAnnotatedClasses(annotationClasses);
    ConnectionDetails connectionDetails = ConnectionDetails.newBuilder()
            .setDriver("org.h2.Driver")
            .setUrl("jdbc:h2:mem:test")
            .setUserName("sa")
            .setPassword("")
            .setDialect("org.hibernate.dialect.H2Dialect")
            .setShowSQL("true")
            .setCurrentSessionContextClass("thread")
            .setHBM2ddlAuto("create-drop")
            .setConnectionPullProviderClass(HikariConnectionProvider.class)
            .setInitialSize(0)
            .setMinIdle(5)
            .setMaxIdle(5)
            .setMaxTotal(0)
            .build();
    connectionPullManager.setConnectionDetails(connectionDetails);
    TestEntity testEntity = new TestEntity();
    SessionFactory sessionFactory = connectionPullManager.getConfigureSessionFactoryByDefault();

    IUserDao<TestEntity> userDao = new TestEntityDao<>(sessionFactory);
    userDao.setClazz(TestEntity.class);
    testEntity.setName("testprops");

    userDao.saveEntity(testEntity);

    Assertions.assertNotNull(testEntity.getId());
  }
}
