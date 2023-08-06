package com.cos.core.test.transaction;

import com.cos.core.config.ConfigDbType;
import com.cos.core.config.ConnectionPoolType;
import com.cos.core.config.factory.ConfigurationSessionFactory;
import com.cos.core.constant.DataSourcePoolType;
import com.cos.core.service.EmployeeDependentService;
import com.cos.core.service.IEmployeeDependentService;
import com.cos.core.test.base.AbstractJoinTest;
import com.cos.core.test.modal.EmployeeDependentTestDto;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.cos.core.constant.DataSourcePool.getDataSource;

public class JoinTest extends AbstractJoinTest {

    private static ConnectionHolder connectionHolder;

    private static IEmployeeDependentService employeeDependentService;

    @BeforeAll
    public static void getSessionFactory() {
        Class[] classes = new Class[]{EmployeeDependentTestDto.class};
        ConfigurationSessionFactory configurationSessionFactory = new ConfigurationSessionFactory(
                ConnectionPoolType.HIKARI, ConfigDbType.PROPERTY, classes
        );
        sessionFactory = configurationSessionFactory.getSessionFactory();
        employeeDependentService = new EmployeeDependentService(sessionFactory);

        dataSource = getDataSource(DataSourcePoolType.HIKARI_DATASOURCE);
        connectionHolder = dataSource::getConnection;
    }

    @BeforeEach
    public void BeforeEach() {
        prepareTestEntityDb();
    }

    @Test
    @ExpectedDataSet(value = "/data/dataset/initDataJoinSet.xml")
    void employeeDependentServiceTest() {
        List<EmployeeDependentTestDto> employeeDependentTestDtoList =
                employeeDependentService.getEmployeeDependenTestDtoList();
        Assertions.assertFalse(employeeDependentTestDtoList.isEmpty());
    }

}
