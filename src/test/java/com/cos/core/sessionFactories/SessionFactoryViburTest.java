package com.cos.core.sessionFactories;

import com.cos.core.config.ConnectionPullViburConfiguration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.dao.impl.ITestEntityDao;
import com.cos.core.dao.impl.TestEntityDao;
import com.cos.core.modal.TestEntity;
import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.PropertiesProvider;
import com.cos.core.properties.modal.ExternalCPConnectionDetails;
import org.hibernate.SessionFactory;
import org.hibernate.vibur.internal.ViburDBCPConnectionProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.cos.core.constant.Constant.*;

@Deprecated
@Disabled
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

        ITestEntityDao<TestEntity> testEntityDao = new TestEntityDao<>(sessionFactory);
        testEntityDao.setClazz(TestEntity.class);
        testEntity.setName("testprops");

        testEntityDao.saveEntity(testEntity);

        Assertions.assertEquals(1, testEntity.getId());
    }

    @Test
    void createSessionFactoryWithXML() {
        IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullViburConfiguration();

        TestEntity testEntity = new TestEntity();
        SessionFactory sessionFactory = connectionPullConfiguration
                .createSessionFactoryWithHibernateXML();
        ITestEntityDao<TestEntity> testEntityDao = new TestEntityDao<>(sessionFactory);
        testEntityDao.setClazz(TestEntity.class);
        testEntity.setName("testxml");

        testEntityDao.saveEntity(testEntity);

        Assertions.assertEquals(1, testEntity.getId());
        List<TestEntity> testEntities = testEntityDao.getAllTestEntities();
        Assertions.assertEquals(1, testEntities.size());
    }

    @Test
    public void createClassDetailsSessionFactory() {
        IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullViburConfiguration();

        List<Class<?>> annotationList = new ArrayList<>();
        annotationList.add(TestEntity.class);
        Class<?>[] annotationClasses = annotationList.toArray(new Class<?>[0]);
        ExternalCPConnectionDetails connectionDetails = ExternalCPConnectionDetails.newBuilder()
                .setDriver(POSTGRES_DRIVER)
                .setUrl(POSTGRES_DB_URL)
                .setUserName(POSTGRES_USERNAME)
                .setPassword(POSTGRES_PASSWORD)
                .setDialect(POSTGRES_DIALECT)
                .setShowSQL(true)
                .setCurrentSessionContextClass("thread")
                .setHBM2ddlAuto("update")
                .setConnectionPullProviderClass(ViburDBCPConnectionProvider.class)
                .build();

        TestEntity testEntity = new TestEntity();
        SessionFactory sessionFactory = connectionPullConfiguration
                .createClassDetailsSessionFactory(connectionDetails, annotationClasses);

        ITestEntityDao<TestEntity> testEntityDao = new TestEntityDao<>(sessionFactory);
        testEntityDao.setClazz(TestEntity.class);
        testEntity.setName("testprops");

        testEntityDao.saveEntity(testEntity);

        Assertions.assertEquals(1, testEntity.getId());
    }
}
