package com.cos.core.sessionFactories;

import com.cos.core.config.ConnectionPullViburConfiguration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.dao.IUserDao;
import com.cos.core.dao.impl.TestEntityDao;
import com.cos.core.modal.TestEntity;
import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.PropertiesProvider;
import com.cos.core.properties.modal.ConnectionDetails;
import org.hibernate.SessionFactory;
import org.hibernate.vibur.internal.ViburDBCPConnectionProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SessionFactoryViburTest {
    @Test
    void createSessionFactoryWithProperties() {
        IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullViburConfiguration();

        List<Class<?>> annotationList = new ArrayList<>();
        annotationList.add(TestEntity.class);
        Class<?>[] annotationClasses = annotationList.toArray(new Class<?>[0]);
        IPropertiesProvider propertiesProvider = new PropertiesProvider();
        propertiesProvider.loadPropertiesByName("vibur.db.properties");
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
        IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullViburConfiguration();

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
    public void createDefaultSessionFactory() {
        IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullViburConfiguration();

        List<Class<?>> annotationList = new ArrayList<>();
        annotationList.add(TestEntity.class);
        Class<?>[] annotationClasses = annotationList.toArray(new Class<?>[0]);
        connectionPullConfiguration.setAnnotatedClasses(annotationClasses);
        ConnectionDetails connectionDetails = ConnectionDetails.newBuilder()
                .setDriver("org.h2.Driver")
                .setUrl("jdbc:h2:mem:test")
                .setUserName("sa")
                .setPassword("")
                .setDialect("org.hibernate.dialect.H2Dialect")
                .setShowSQL("true")
                .setCurrentSessionContextClass("thread")
                .setHBM2ddlAuto("create-drop")
                .setConnectionPullProviderClass(ViburDBCPConnectionProvider.class)
                .setInitialSize(0)
                .setMinIdle(5)
                .setMaxIdle(5)
                .setMaxTotal(0)
                .build();
        TestEntity testEntity = new TestEntity();
        SessionFactory sessionFactory = connectionPullConfiguration
                .createDefaultSessionFactory(connectionDetails, annotationClasses);

        IUserDao<TestEntity> userDao = new TestEntityDao<>(sessionFactory);
        userDao.setClazz(TestEntity.class);
        testEntity.setName("testprops");

        userDao.saveEntity(testEntity);

        Assertions.assertNotNull(testEntity.getId());
    }
}