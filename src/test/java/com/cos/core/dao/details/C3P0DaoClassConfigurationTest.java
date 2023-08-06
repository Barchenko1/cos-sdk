package com.cos.core.dao.details;

import com.cos.core.config.ConnectionPullC3P0Configuration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.constant.DataSourcePoolType;
import com.cos.core.dao.AbstractDaoConfigurationTest;
import com.cos.core.dao.impl.TestEntityDao;
import com.cos.core.modal.TestEntity;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.cos.core.constant.DataSourcePool.*;

@ExtendWith(DBUnitExtension.class)
public class C3P0DaoClassConfigurationTest extends AbstractDaoConfigurationTest {

    private static ConnectionHolder connectionHolder;

    public C3P0DaoClassConfigurationTest() {
    }

    @BeforeAll
    public static void getSessionFactory() throws SQLException {
        IConnectionPullConfiguration connectionPullConfiguration =
                new ConnectionPullC3P0Configuration();
        Class<?>[] classes = { TestEntity.class };
        sessionFactory =
                connectionPullConfiguration.createClassDetailsSessionFactory(getConnectionDetails(DataSourcePoolType.C3PO_DATASOURCE), classes);
        testEntityDao = new TestEntityDao<>(sessionFactory);
        testEntityDao.setClazz(TestEntity.class);
        dataSource = getDataSource(DataSourcePoolType.C3PO_DATASOURCE);
        connectionHolder = dataSource::getConnection;
    }

    @BeforeEach
    public void BeforeEach() {
        dataSource = getDataSource(DataSourcePoolType.C3PO_DATASOURCE);
        prepareTestEntityDb();
    }

    @Test
    @ExpectedDataSet(value = "/data/expected/createExpectedSet.xml")
    void saveDaoTest() {
        TestEntity testEntity = new TestEntity();
        testEntity.setId(3L);
        testEntity.setName("testSave");

        testEntityDao.saveEntity(testEntity);
    }

    @Test
    @ExpectedDataSet("/data/expected/expectedDataSet.xml")
    void updateDaoTest() {
        TestEntity testEntity = new TestEntity();
        testEntity.setId(1L);
        testEntity.setName("Updated");
        testEntityDao.updateEntity(testEntity);
    }

    @Test
    @ExpectedDataSet(value = "/data/expected/deleteExpectedSet.xml")
    void deleteDaoTest() {
        TestEntity testEntity = new TestEntity();
        testEntity.setId(2L);
        testEntity.setName("Test2");

        testEntityDao.deleteEntity(testEntity);
    }

    @Test
    @ExpectedDataSet(value = "/data/dataset/initDataSet.xml")
    void getTestEntityList() {
        List<TestEntity> resultList = testEntityDao.getAllTestEntities();
        Assertions.assertEquals(2, resultList.size());
    }

    @Test
    void getTestEntity() {
        Optional<TestEntity> result = testEntityDao
                .getTestEntityByUser("Test1");

        Assertions.assertEquals("Test1", result.get().getName());
    }
}

