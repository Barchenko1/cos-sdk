package com.cos.core.test.xml.param;

import com.cos.core.config.factory.ConfigurationSessionFactory;
import com.cos.core.constant.DataSourcePoolType;
import com.cos.core.dao.basic.TestEntityDao;
import com.cos.core.modal.TestEntity;
import com.cos.core.test.base.AbstractDaoConfigurationTest;
import com.cos.core.util.CosCoreConstants;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.cos.core.util.DataSourcePool.getDataSource;

@DataSet(cleanAfter = true)
public class ViburDaoXMLParamConfigurationTest extends AbstractDaoConfigurationTest {

    private static ConnectionHolder connectionHolder;

    public ViburDaoXMLParamConfigurationTest() {
    }

    @BeforeAll
    public static void getSessionFactory() {
        ConfigurationSessionFactory configurationSessionFactory = new ConfigurationSessionFactory(
                CosCoreConstants.VIBUR_HIBERNATE_XML_FILE_NAME
        );
        sessionFactory = configurationSessionFactory.getSessionFactory();
        testEntityDao = new TestEntityDao(sessionFactory);
        testEntityDao.setClazz(TestEntity.class);
        dataSource = getDataSource(DataSourcePoolType.VIBUR_DATASOURCE);
        connectionHolder = dataSource::getConnection;
    }

    @BeforeEach
    public void BeforeEach() {
        prepareTestEntityDb();
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
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

