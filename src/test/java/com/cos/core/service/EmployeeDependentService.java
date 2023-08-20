package com.cos.core.service;

import com.cos.core.dao.basic.DependentDao;
import com.cos.core.dao.basic.EmployeeDao;
import com.cos.core.dao.basic.IDependentDao;
import com.cos.core.dao.basic.IEmployeeDao;
import com.cos.core.modal.TestDependent;
import com.cos.core.modal.TestEmployee;
import com.cos.core.test.modal.EmployeeDependentTestDto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.type.StandardBasicTypes;

import java.util.List;

public class EmployeeDependentService implements IEmployeeDependentService {

    Class<?>[] classes;
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeDependentService.class);

    private SessionFactory sessionFactory;
    private IEmployeeDao<TestEmployee> employeeDao;
    private IDependentDao<TestDependent> dependentDao;
    private static final String sqlQuery = "SELECT te.id AS employee_id, te.name AS employee_name, td.id AS dependent_id, td.name AS dependent_name, td.status AS dependent_statusFROM TestEmployee teLEFT JOIN TestDependent td ON te.id = td.testEmployee_id;";

    public EmployeeDependentService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.employeeDao = new EmployeeDao<>(sessionFactory);
        this.dependentDao = new DependentDao<>(sessionFactory);
    }

    @Override
    public void saveTransactionalEntities(TestEmployee employee, List<TestDependent> dependents) {
        dependents.forEach(employee::addDependent);
        employeeDao.saveEntity(employee);
    }

    @Override
    public void saveIncorrectTransactionalEntities(TestEmployee employee, List<TestDependent> dependents) {
        employeeDao.saveEntity(null);
    }

    @Override
    public List<EmployeeDependentTestDto> getEmployeeDependenTestDtoList() {
        List<EmployeeDependentTestDto> resultList;
        try (Session session = sessionFactory.openSession()) {
            resultList = session.createNativeQuery(sqlQuery)
                    .addScalar("employee_id", StandardBasicTypes.LONG)
                    .addScalar("employee_name", StandardBasicTypes.STRING)
                    .addScalar("dependent_id", StandardBasicTypes.LONG)
                    .addScalar("dependent_name", StandardBasicTypes.STRING)
                    .addScalar("dependent_status", StandardBasicTypes.STRING)
                    .setResultTransformer(Transformers.aliasToBean(EmployeeDependentTestDto.class))
                    .list();
        }

        return resultList;
    }
}
