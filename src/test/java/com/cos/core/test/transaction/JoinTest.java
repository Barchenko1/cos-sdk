package com.cos.core.test.transaction;

import com.cos.core.config.ConnectionPullHikariConfiguration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.constant.DataSourcePoolType;
import com.cos.core.dto.BasicDtoEntityDao;
import com.cos.core.dto.IDtoEntityDao;
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

import static com.cos.core.util.DataSourcePool.getDataSource;

public class JoinTest extends AbstractJoinTest {

    private static ConnectionHolder connectionHolder;

    private static IEmployeeDependentService employeeDependentService;

    private static IDtoEntityDao<EmployeeDependentTestDto> dtoEntityDao;

    private static final String sqlQuery = "SELECT te.id AS employeeId, te.name AS employeeName, td.id AS dependentId, td.name AS dependentName, td.status AS dependentStatusFROM TestEmployee teLEFT JOIN TestDependent td ON te.id = td.testEmployee_id;";

    @BeforeAll
    public static void getSessionFactory() {
        IConnectionPullConfiguration connectionPullConfiguration =
                new ConnectionPullHikariConfiguration();
        sessionFactory = connectionPullConfiguration.createSessionFactoryWithHibernateXML();
        employeeDependentService = new EmployeeDependentService(sessionFactory);
        dtoEntityDao = new BasicDtoEntityDao<>(sessionFactory);
        dtoEntityDao.setClazz(EmployeeDependentTestDto.class);
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
        List<EmployeeDependentTestDto> employeeDependentTestDtoList = dtoEntityDao.getDtoList(sqlQuery);
        Assertions.assertFalse(employeeDependentTestDtoList.isEmpty());
    }

}
