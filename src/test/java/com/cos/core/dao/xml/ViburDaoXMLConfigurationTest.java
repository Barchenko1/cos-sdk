package com.cos.core.dao.xml;

import com.cos.core.config.ConnectionPullViburConfiguration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.dao.AbstractDaoConfigurationTest;
import com.cos.core.dao.IUserDao;
import com.cos.core.dao.impl.TestEntityDao;
import com.cos.core.modal.TestEntity;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.hibernate.vibur.internal.ViburDBCPConnectionProvider;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.vibur.dbcp.integration.ViburDBCPConnectionProvider;

import java.util.List;
import java.util.Optional;

@ExtendWith(DBUnitExtension.class)
@DataSet(cleanBefore = true, cleanAfter = true)
public class ViburDaoXMLConfigurationTest extends AbstractDaoConfigurationTest {

    private static IUserDao<TestEntity> userDao;

    @Rule
    private static final ConnectionHolder connectionHolder =
            () -> sessionFactory.getSessionFactoryOptions()
                        .getServiceRegistry()
                        .getService(ViburDBCPConnectionProvider.class)
                        .getConnection();

    public ViburDaoXMLConfigurationTest() {
    }

    @BeforeAll
    @DataSet(cleanBefore = true, cleanAfter = true)
    public static void getSessionFactory() {
        IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullViburConfiguration();
        sessionFactory = connectionPullConfiguration.createSessionFactoryWithHibernateXML();
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
    }
}

