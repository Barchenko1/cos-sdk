package com.cos.core.dao.details;

import com.cos.core.config.ConnectionPullDBCP2Configuration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.dao.AbstractDaoConfigurationTest;
import com.cos.core.dao.impl.TestEntityDao;
import com.cos.core.dao.impl.ITestEntityDao;
import com.cos.core.modal.TestEntity;
import com.cos.core.properties.modal.AbstractConnectionDetails;
import com.cos.core.properties.modal.DBCP2ConnectionDetails;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

@ExtendWith(DBUnitExtension.class)
@DataSet(cleanBefore = true, cleanAfter = true)
public class DBCP2DaoClassConfigurationTest extends AbstractDaoConfigurationTest {

    private static ITestEntityDao<TestEntity> testEntityDao;

    @Rule
    private static final ConnectionHolder connectionHolder =
            () -> sessionFactory.getSessionFactoryOptions()
                    .getServiceRegistry()
                    .getService(DatasourceConnectionProviderImpl.class)
                    .getConnection();

    public DBCP2DaoClassConfigurationTest() {
    }

    @BeforeAll
    public static void getSessionFactory() {
        IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullDBCP2Configuration();
        Class<?>[] classes = { TestEntity.class };
        sessionFactory =
                connectionPullConfiguration.createClassDetailsSessionFactory(setUpConnectionDetails(), classes);
        testEntityDao = new TestEntityDao<>(sessionFactory);
        testEntityDao.setClazz(TestEntity.class);
    }

    private static AbstractConnectionDetails setUpConnectionDetails() {
        return DBCP2ConnectionDetails.newBuilder()
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

