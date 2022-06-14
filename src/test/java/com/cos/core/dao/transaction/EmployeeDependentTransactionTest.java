package com.cos.core.dao.transaction;

import com.cos.core.config.ConnectionPullHikariConfiguration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.dao.AbstractDaoConfigurationTest;
import com.cos.core.dao.impl.IDependentDao;
import com.cos.core.dao.impl.IEmployeeDao;
import com.cos.core.modal.TestDependent;
import com.cos.core.modal.TestEmployee;
import com.cos.core.service.EmployeeDependentTransactionService;
import com.cos.core.service.IEmployeeDependentTransactionService;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.zaxxer.hikari.hibernate.HikariConnectionProvider;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(DBUnitExtension.class)
@DataSet(cleanBefore = true, cleanAfter = true)
public class EmployeeDependentTransactionTest extends AbstractDaoConfigurationTest {

    private static IEmployeeDao<TestEmployee> employeeDao;
    private static IDependentDao<TestDependent> dependentDao;
    private static IEmployeeDependentTransactionService employeeDependentTransactionService;

    @Rule
    private static final ConnectionHolder connectionHolder =
            () -> sessionFactory.getSessionFactoryOptions()
                    .getServiceRegistry()
                    .getService(HikariConnectionProvider.class)
                    .getConnection();

    @BeforeAll
    public static void getSessionFactory() {
        IConnectionPullConfiguration connectionPullConfiguration = new ConnectionPullHikariConfiguration();
        sessionFactory = connectionPullConfiguration.createSessionFactoryWithHibernateXML();

//        employeeDao = new EmployeeDao<>(sessionFactory);
//        employeeDao.setClazz(TestEmployee.class);
//
//        dependentDao = new DependentDao<>(sessionFactory);
//        dependentDao.setClazz(TestDependent.class);

        Class<?>[] classes = { TestEmployee.class, TestDependent.class };
        employeeDependentTransactionService = new EmployeeDependentTransactionService(sessionFactory);
        employeeDependentTransactionService.setClasses(classes);
    }

    @Test
    @ExpectedDataSet(value = "/data/expected/createExpectedTransactionSet.yml")
    void saveDaoTest() {
        TestDependent dependent1 = setUpDependent("Liza", "GirlFriend");
        TestDependent dependent2 = setUpDependent("Bread", "Child");

        List<TestDependent> dependents = new ArrayList<>();
        dependents.add(dependent1);
        dependents.add(dependent2);

        TestEmployee employee = new TestEmployee();
        employee.setName("Robert");

        employeeDependentTransactionService.saveTransactionalEntities(employee, dependents);
    }

    private TestDependent setUpDependent(String name, String status) {
        TestDependent dependent = new TestDependent();
        dependent.setName(name);
        dependent.setStatus(status);
        return dependent;
    }

}
