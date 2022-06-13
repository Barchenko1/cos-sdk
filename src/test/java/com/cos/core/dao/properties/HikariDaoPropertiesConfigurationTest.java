package com.cos.core.dao.properties;

import com.cos.core.config.ConnectionPullHikariConfiguration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.dao.AbstractDaoConfigurationTest;
import com.cos.core.dao.impl.ITestEntityDao;
import com.cos.core.dao.impl.TestEntityDao;
import com.cos.core.modal.TestEntity;
import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.PropertiesProvider;
import com.cos.core.util.CosCoreConstants;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.zaxxer.hikari.hibernate.HikariConnectionProvider;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

@ExtendWith(DBUnitExtension.class)
@DataSet(cleanBefore = true, cleanAfter = true)
public class HikariDaoPropertiesConfigurationTest extends AbstractDaoConfigurationTest {

    private static ITestEntityDao<TestEntity> testEntityDao;

    @Rule
    private static final ConnectionHolder connectionHolder =
            () -> sessionFactory.getSessionFactoryOptions()
                        .getServiceRegistry()
                        .getService(HikariConnectionProvider.class)
                        .getConnection();

    public HikariDaoPropertiesConfigurationTest() {
    }

    @BeforeAll
    public static void getSessionFactory() {
        IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullHikariConfiguration();
        Class<?>[] classes = { TestEntity.class };
        IPropertiesProvider propertiesProvider = new PropertiesProvider();
        propertiesProvider.loadPropertiesByName(CosCoreConstants.HIKARI_PROPERTIES_FILE_NAME);
        connectionPullConfiguration.setAnnotatedClasses(classes);
        connectionPullConfiguration.setPropertiesProvider(propertiesProvider);
        sessionFactory = connectionPullConfiguration.createSessionFactoryWithProperties();
        testEntityDao = new TestEntityDao<>(sessionFactory);
        testEntityDao.setClazz(TestEntity.class);
    }

    @Test
    @ExpectedDataSet(value = "/data/expected/createExpectedSet.yml")
    void saveDaoTest() {

        TestEntity testEntity = new TestEntity();
        testEntity.setName("testSave");

        testEntityDao.saveEntity(testEntity);
    }

    @Test
    @DataSet(value = "/data/dataset/initDataSet.yml")
    @ExpectedDataSet(value = "/data/expected/updateExpectedSet.yml")
    void updateDaoTest() {
        TestEntity testEntity = new TestEntity();
        testEntity.setId(1L);
        testEntity.setName("testUpdate");

        testEntityDao.updateEntity(testEntity);
    }

    @Test
    @DataSet(value = "/data/dataset/initDataSet.yml")
    @ExpectedDataSet(value = "/data/expected/deleteExpectedSet.yml")
    void deleteDaoTest() {

        TestEntity testEntity = new TestEntity();
        testEntity.setId(2L);
        testEntity.setName("test2");

        testEntityDao.deleteEntity(testEntity);
    }

    @Test
    @DataSet(value = "/data/dataset/initDataSet.yml")
    @ExpectedDataSet(value = "/data/dataset/initDataSet.yml")
    void getTestEntityList() {
        List<TestEntity> resultList = testEntityDao.getAllUsers();
        Assertions.assertEquals(2, resultList.size());
    }

    @Test
    @DataSet(value = "/data/dataset/initDataSet.yml")
    void getTestEntity() {
        Optional<TestEntity> result = testEntityDao
                .getUserByUserName("test2");

        Assertions.assertEquals("test2", result.get().getName());
    }
}

