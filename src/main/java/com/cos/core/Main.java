package com.cos.core;

import com.cos.core.config.ConnectionPullHikariConfiguration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.test.ITestEntityDao;
import com.cos.core.test.TestEntityDao;
import com.cos.core.test.service.EmployeeDependentTransactionService;
import com.cos.core.test.service.IEmployeeDependentTransactionService;
import com.cos.core.test.modal.TestDependent;
import com.cos.core.test.modal.TestEmployee;
import com.cos.core.test.modal.TestEntity;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static SessionFactory sessionFactory;
    private static ITestEntityDao<TestEntity> testEntityDao;

    public static void main(String[] args) {
        System.out.println("Hello word");
        init();

    }

    private static void test1() {
        init();
        TestEntity testEntity = creatTestEntity("testmain");

        TestEntity testEntity1Update = new TestEntity();
        testEntity1Update.setId(1L);
        testEntity1Update.setName("testupdate");

//        testEntityDao.saveEntity(testEntity);
//        testEntityDao.updateEntity(testEntity1Update);
//        testEntityDao.deleteEntity(testEntity1Update);
    }

    private static void test2() {
        initTransactionTest();
    }

    private static TestEntity creatTestEntity(String name) {
        TestEntity testEntity = new TestEntity();
        testEntity.setName(name);
        return testEntity;
    }

    private static void init() {
        IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullHikariConfiguration();
        Class<?>[] classes = { TestEntity.class };
        connectionPullConfiguration.setAnnotatedClasses(classes);
        sessionFactory = connectionPullConfiguration.createSessionFactoryWithHibernateXML();
        testEntityDao = new TestEntityDao<>(sessionFactory);
        testEntityDao.setClazz(TestEntity.class);
    }

    private static void initTransactionTest() {
        IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullHikariConfiguration();
        sessionFactory = connectionPullConfiguration.createSessionFactoryWithHibernateXML();

        TestDependent dependent1 = setUpDependent("Liza", "GirlFriend");
        TestDependent dependent2 = setUpDependent("Bread", "Child");

        List<TestDependent> dependents = new ArrayList<>();
        dependents.add(dependent1);
        dependents.add(dependent2);

        TestEmployee employee = new TestEmployee();
        employee.setName("Robert");

        IEmployeeDependentTransactionService employeeDependentTransactionService =
                new EmployeeDependentTransactionService(sessionFactory);
        employeeDependentTransactionService.saveTransactionalEntities(employee, dependents);
    }

    private static TestDependent setUpDependent(String name, String status) {
        TestDependent dependent = new TestDependent();
        dependent.setName(name);
        dependent.setStatus(status);
        return dependent;
    }

    private static TestEmployee setUpEmployee(String name, List<TestDependent> dependents) {
        TestEmployee employee = new TestEmployee();
        employee.setName(name);
        employee.setTestDependents(dependents);
        return employee;
    }
}
