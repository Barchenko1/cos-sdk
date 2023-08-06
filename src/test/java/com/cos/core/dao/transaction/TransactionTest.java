package com.cos.core.dao.transaction;

import com.cos.core.config.ConnectionPullHikariConfiguration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.constant.DataSourcePoolType;
import com.cos.core.modal.TestDependent;
import com.cos.core.modal.TestEmployee;
import com.cos.core.service.EmployeeDependentTransactionService;
import com.cos.core.service.IEmployeeDependentTransactionService;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static com.cos.core.constant.DataSourcePool.getDataSource;

@ExtendWith(DBUnitExtension.class)
public class TransactionTest extends AbstractTransactionTest {
    private static ConnectionHolder connectionHolder;

    private static IEmployeeDependentTransactionService employeeDependentTransactionService;

    @BeforeAll
    public static void getSessionFactory() {
        IConnectionPullConfiguration connectionPullConfiguration =
                new ConnectionPullHikariConfiguration();
        sessionFactory = connectionPullConfiguration.createSessionFactoryWithHibernateXML();

        Class<?>[] classes = { TestEmployee.class, TestDependent.class };
        employeeDependentTransactionService = new EmployeeDependentTransactionService(sessionFactory);
        employeeDependentTransactionService.setClasses(classes);

        dataSource = getDataSource(DataSourcePoolType.HIKARI_DATASOURCE);
        connectionHolder = dataSource::getConnection;
    }

    @BeforeEach
    public void BeforeEach() {
        prepareTestEntityDb();
    }

    @Test
    @DataSet(cleanBefore = true)
    @ExpectedDataSet(value = "/data/expected/createExpectedTransactionSet.xml")
    void saveCorrectTransactionTest() {
        TestDependent dependent1 = setUpDependent("Liza", "GirlFriend");
        TestDependent dependent2 = setUpDependent("Bread", "Child");

        List<TestDependent> dependents = new ArrayList<>();
        dependents.add(dependent1);
        dependents.add(dependent2);

        TestEmployee employee = new TestEmployee();
        employee.setName("Robert");

        employeeDependentTransactionService.saveTransactionalEntities(employee, dependents);
    }

    @Test
    @DataSet(cleanBefore = true)
    void saveIncorrectTransactionTest() {
        TestDependent dependent1 = setUpDependent("Liza", "GirlFriend");
        TestDependent dependent2 = setUpDependent("Bread", "Child");

        List<TestDependent> dependents = new ArrayList<>();
        dependents.add(dependent1);
        dependents.add(dependent2);

        TestEmployee employee = new TestEmployee();
        employee.setName("Robert");

        Assertions.assertThrows(RuntimeException.class, () -> {
            employeeDependentTransactionService.saveIncorrectTransactionalEntities(null, dependents);
        });
    }

    private TestDependent setUpDependent(String name, String status) {
        TestDependent dependent = new TestDependent();
        dependent.setName(name);
        dependent.setStatus(status);
        return dependent;
    }

}
