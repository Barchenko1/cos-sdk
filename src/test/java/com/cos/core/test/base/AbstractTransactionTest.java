package com.cos.core.test.base;

import com.cos.core.dao.impl.ITestEntityDao;
import com.cos.core.modal.TestEntity;
import com.cos.core.util.TestUtil;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;

import javax.sql.DataSource;

public abstract class AbstractTransactionTest {

    protected static SessionFactory sessionFactory;
    protected static ITestEntityDao<TestEntity> testEntityDao;
    protected static DataSource dataSource;

    @AfterAll
    public static void cleanUp() {
        TestUtil.cleanUp(sessionFactory, dataSource);
    }

    public static void prepareTestEntityDb() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.DELETE_ALL, "/data/dataset/initEmptyDataSet.xml");
    }

}