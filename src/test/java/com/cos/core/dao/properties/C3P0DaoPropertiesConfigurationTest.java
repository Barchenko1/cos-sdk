package com.cos.core.dao.properties;

import com.cos.core.config.ConnectionPullC3P0Configuration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.dao.AbstractDaoConfigurationTest;
import com.cos.core.dao.IUserDao;
import com.cos.core.dao.impl.TestEntityDao;
import com.cos.core.modal.TestEntity;
import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.PropertiesProvider;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.hibernate.c3p0.internal.C3P0ConnectionProvider;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

@ExtendWith(DBUnitExtension.class)
@DataSet(cleanBefore = true, cleanAfter = true)
public class C3P0DaoPropertiesConfigurationTest extends AbstractDaoConfigurationTest {

    private static IUserDao<TestEntity> userDao;

    @Rule
    private static final ConnectionHolder connectionHolder =
            () -> sessionFactory.getSessionFactoryOptions()
                        .getServiceRegistry()
                        .getService(C3P0ConnectionProvider.class)
                        .getConnection();

    public C3P0DaoPropertiesConfigurationTest() {
    }

    @BeforeAll
    public static void getSessionFactory() {
        IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullC3P0Configuration();
        Class<?>[] classes = { TestEntity.class };
        IPropertiesProvider propertiesProvider = new PropertiesProvider();
        propertiesProvider.loadPropertiesByName("c3p0.db.properties");
        connectionPullConfiguration.setAnnotatedClasses(classes);
        connectionPullConfiguration.setPropertiesProvider(propertiesProvider);
        sessionFactory = connectionPullConfiguration.createSessionFactoryWithProperties();
        userDao = new TestEntityDao<>(sessionFactory);
        userDao.setClazz(TestEntity.class);
    }

    @Test
    @ExpectedDataSet(value = "/data/expected/createExpectedSet.yml")
    void saveDaoTest() {
        TestEntity testEntity = new TestEntity();
        testEntity.setName("testSave");

        userDao.saveEntity(testEntity);
    }

    @Test
    @DataSet(value = "/data/dataset/initDataSet.yml")
    @ExpectedDataSet(value = "/data/expected/updateExpectedSet.yml")
    void updateDaoTest() {
        TestEntity testEntity = new TestEntity();
        testEntity.setId(1L);
        testEntity.setName("testUpdate");

        userDao.updateEntity(testEntity);
        List<TestEntity> testEntityList = userDao.getAllUsers();
        System.out.println(testEntityList);
    }

    @Test
    @DataSet(value = "/data/dataset/initDataSet.yml")
    @ExpectedDataSet(value = "/data/expected/deleteExpectedSet.yml")
    void deleteDaoTest() {
        TestEntity testEntity = new TestEntity();
        testEntity.setId(2L);
        testEntity.setName("test2");

        userDao.deleteEntity(testEntity);
    }

    @Test
    @DataSet(value = "/data/dataset/initDataSet.yml")
    @ExpectedDataSet(value = "/data/dataset/initDataSet.yml")
    void getTestEntityList() {
        List<TestEntity> resultList = userDao.getAllUsers();
        Assertions.assertEquals(2, resultList.size());
    }

    @Test
    @DataSet(value = "/data/dataset/initDataSet.yml")
    void getTestEntity() {
        Optional<TestEntity> result = userDao
                .getUserByUserName("test2");

        Assertions.assertEquals("test2", result.get().getName());

        List<TestEntity> testEntityList = userDao.getAllUsers();
        System.out.println(testEntityList);
    }
}

