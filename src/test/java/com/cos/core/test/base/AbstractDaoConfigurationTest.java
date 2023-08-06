package com.cos.core.test.base;

import com.cos.core.dao.impl.ITestEntityDao;
import com.cos.core.modal.TestEntity;
import com.cos.core.util.ResourceReader;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.junit.jupiter.api.AfterAll;

import javax.sql.DataSource;
import java.sql.Connection;

public abstract class AbstractDaoConfigurationTest {

    protected static SessionFactory sessionFactory;
    protected static ITestEntityDao<TestEntity> testEntityDao;
    protected static DataSource dataSource;
    protected static final ResourceReader resourceReader = new ResourceReader();

    @AfterAll
    public static void cleanUp() {
        if (sessionFactory != null) {
            StandardServiceRegistry serviceRegistry =
                    sessionFactory.getSessionFactoryOptions().getServiceRegistry();
            if (serviceRegistry != null) {
                StandardServiceRegistryBuilder.destroy(serviceRegistry);
            }
        }
        prepareTestEntityDb();
    }

    public static void prepareTestEntityDb() {
        try (Connection connection = dataSource.getConnection()) {
            IDataSet dataSet = resourceReader.getDataSet("/data/dataset/initDataSet.xml");
            DatabaseOperation.CLEAN_INSERT.execute(new DatabaseConnection(connection), dataSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
