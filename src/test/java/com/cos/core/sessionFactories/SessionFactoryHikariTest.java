package com.cos.core.sessionFactories;

import com.cos.core.config.ConnectionPullDBCP2Configuration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.dao.IUserDao;
import com.cos.core.dao.impl.TestEntityDao;
import com.cos.core.modal.TestEntity;
import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.PropertiesProvider;
import com.cos.core.properties.modal.DBCP2ConnectionDetails;
import com.cos.core.properties.modal.ExternalCPConnectionDetails;
import com.zaxxer.hikari.hibernate.HikariConnectionProvider;
import org.hibernate.SessionFactory;
import org.hibernate.c3p0.internal.C3P0ConnectionProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SessionFactoryHikariTest {
    @Test
    void createSessionFactoryWithProperties() {
        IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullDBCP2Configuration();

        List<Class<?>> annotationList = new ArrayList<>();
        annotationList.add(TestEntity.class);
        Class<?>[] annotationClasses = annotationList.toArray(new Class<?>[0]);
        IPropertiesProvider propertiesProvider = new PropertiesProvider();
        propertiesProvider.loadPropertiesByName("hikari.db.properties");
        connectionPullConfiguration.setAnnotatedClasses(annotationClasses);
        connectionPullConfiguration.setPropertiesProvider(propertiesProvider);

        TestEntity testEntity = new TestEntity();
        SessionFactory sessionFactory = connectionPullConfiguration
                .createSessionFactoryWithProperties();

        IUserDao<TestEntity> userDao = new TestEntityDao<>(sessionFactory);
        userDao.setClazz(TestEntity.class);
        testEntity.setName("testprops");

        userDao.saveEntity(testEntity);

        Assertions.assertNotNull(testEntity.getId());
    }

    @Test
    void createSessionFactoryWithXML() {
        IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullDBCP2Configuration();

        TestEntity testEntity = new TestEntity();
        SessionFactory sessionFactory = connectionPullConfiguration
                .createSessionFactoryWithHibernateXML();
        IUserDao<TestEntity> userDao = new TestEntityDao<>(sessionFactory);
        userDao.setClazz(TestEntity.class);
        testEntity.setName("testxml");

        userDao.saveEntity(testEntity);

        Assertions.assertEquals(1, testEntity.getId());
        List<TestEntity> testEntities = userDao.getAllUsers();
        Assertions.assertEquals(1, testEntities.size());
    }

    @Test
    public void createClassDetailsSessionFactory() {
        IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullDBCP2Configuration();

        List<Class<?>> annotationList = new ArrayList<>();
        annotationList.add(TestEntity.class);
        Class<?>[] annotationClasses = annotationList.toArray(new Class<?>[0]);
        ExternalCPConnectionDetails connectionDetails = ExternalCPConnectionDetails.newBuilder()
                .setDriver("org.h2.Driver")
                .setUrl("jdbc:h2:mem:test")
                .setUserName("sa")
                .setPassword("")
                .setDialect("org.hibernate.dialect.H2Dialect")
                .setShowSQL("true")
                .setCurrentSessionContextClass("thread")
                .setHBM2ddlAuto("create-drop")
                .setConnectionPullProviderClass(HikariConnectionProvider.class)
                .build();

        TestEntity testEntity = new TestEntity();
        SessionFactory sessionFactory = connectionPullConfiguration
                .createClassDetailsSessionFactory(connectionDetails, annotationClasses);

        IUserDao<TestEntity> userDao = new TestEntityDao<>(sessionFactory);
        userDao.setClazz(TestEntity.class);
        testEntity.setName("testprops");

        userDao.saveEntity(testEntity);

        Assertions.assertNotNull(testEntity.getId());
    }
}
