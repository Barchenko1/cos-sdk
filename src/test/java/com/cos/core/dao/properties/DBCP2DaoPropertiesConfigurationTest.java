package com.cos.core.dao.properties;

import com.cos.core.config.ConnectionPullDBCP2Configuration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.constant.DataSourcePoolType;
import com.cos.core.dao.AbstractDaoConfigurationTest;
import com.cos.core.dao.impl.TestEntityDao;
import com.cos.core.modal.TestEntity;
import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.PropertiesProvider;
import com.cos.core.util.CosCoreConstants;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static com.cos.core.constant.DataSourcePool.getDataSource;

@ExtendWith(DBUnitExtension.class)
public class DBCP2DaoPropertiesConfigurationTest extends AbstractDaoConfigurationTest {

    private static ConnectionHolder connectionHolder;

    public DBCP2DaoPropertiesConfigurationTest() {
    }

    @BeforeAll
    public static void getSessionFactory() {
        IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullDBCP2Configuration();
        Class<?>[] classes = { TestEntity.class };
        IPropertiesProvider propertiesProvider = new PropertiesProvider();
        propertiesProvider.loadPropertiesByName(CosCoreConstants.DBCP2_PROPERTIES_FILE_NAME);
        connectionPullConfiguration.setAnnotatedClasses(classes);
        connectionPullConfiguration.setPropertiesProvider(propertiesProvider);
        sessionFactory = connectionPullConfiguration.createSessionFactoryWithProperties();
        testEntityDao = new TestEntityDao<>(sessionFactory);
        testEntityDao.setClazz(TestEntity.class);
        dataSource = getDataSource(DataSourcePoolType.DBCP2_DATASOURCE);
        connectionHolder = dataSource::getConnection;
    }

    @BeforeEach
    public void BeforeEach() {
        prepareTestEntityDb();
    }

    @Test
    @DataSet(cleanBefore = true)
    @ExpectedDataSet(value = "/data/expected/createOneExpectedSet.xml")
    void saveDaoTest() {
        TestEntity testEntity = new TestEntity();
        testEntity.setName("testSave");

        testEntityDao.saveEntity(testEntity);
    }
    @Test
    @ExpectedDataSet("/data/expected/updateExpectedSet.xml")
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

