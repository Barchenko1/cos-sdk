package com.cos.core.sessionFactories;

import com.cos.core.config.ConnectionPullDBCP2Configuration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.dao.impl.TestEntityDao;
import com.cos.core.dao.impl.ITestEntityDao;
import com.cos.core.modal.TestEntity;
import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.PropertiesProvider;
import com.cos.core.properties.modal.DBCP2ConnectionDetails;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SessionFactoryDBCP2Test {

  @Test
  void createSessionFactoryWithProperties() {
    IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullDBCP2Configuration();

    List<Class<?>> annotationList = new ArrayList<>();
    annotationList.add(TestEntity.class);
    Class<?>[] annotationClasses = annotationList.toArray(new Class<?>[0]);
    IPropertiesProvider propertiesProvider = new PropertiesProvider();
    propertiesProvider.loadPropertiesByName("dbcp2.db.properties");
    connectionPullConfiguration.setAnnotatedClasses(annotationClasses);
    connectionPullConfiguration.setPropertiesProvider(propertiesProvider);
    TestEntity testEntity = new TestEntity();
    SessionFactory sessionFactory = connectionPullConfiguration
            .createSessionFactoryWithProperties();

    ITestEntityDao<TestEntity> testEntityDao = new TestEntityDao<>(sessionFactory);
    testEntityDao.setClazz(TestEntity.class);
    testEntity.setName("testprops");

    testEntityDao.saveEntity(testEntity);

    Assertions.assertNotNull(testEntity.getId());
  }

  @Test
  void createSessionFactoryWithXML() {
    IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullDBCP2Configuration();

    TestEntity testEntity = new TestEntity();
    SessionFactory sessionFactory =
            connectionPullConfiguration.createSessionFactoryWithHibernateXML();
    ITestEntityDao<TestEntity> testEntityDao = new TestEntityDao<>(sessionFactory);
    testEntityDao.setClazz(TestEntity.class);
    testEntity.setName("testxml");

    testEntityDao.saveEntity(testEntity);

    Assertions.assertEquals(1, testEntity.getId());
  }

  @Test
  public void createClassDetailsSessionFactory() {
    IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullDBCP2Configuration();

    List<Class<?>> annotationList = new ArrayList<>();
    annotationList.add(TestEntity.class);
    Class<?>[] annotationClasses = annotationList.toArray(new Class<?>[0]);
    DBCP2ConnectionDetails connectionDetails = DBCP2ConnectionDetails.newBuilder()
            .setDriver("org.h2.Driver")
            .setUrl("jdbc:h2:mem:test")
            .setUserName("sa")
            .setPassword("")
            .setDialect("org.hibernate.dialect.H2Dialect")
            .setShowSQL("true")
            .setCurrentSessionContextClass("thread")
            .setHBM2ddlAuto("create-drop")
            .setConnectionPullProviderClass(DatasourceConnectionProviderImpl.class)
            .setInitialSize(0)
            .setMinIdle(5)
            .setMaxIdle(5)
            .setMaxTotal(0)
            .build();

    TestEntity testEntity = new TestEntity();
    SessionFactory sessionFactory = connectionPullConfiguration
            .createClassDetailsSessionFactory(connectionDetails, annotationClasses);

    ITestEntityDao<TestEntity> testEntityDao = new TestEntityDao<>(sessionFactory);
    testEntityDao.setClazz(TestEntity.class);
    testEntity.setName("testprops");

    testEntityDao.saveEntity(testEntity);

    Assertions.assertNotNull(testEntity.getId());
  }
}
