package com.cos.core.service;

import com.cos.core.dao.impl.DependentDao;
import com.cos.core.dao.impl.EmployeeDao;
import com.cos.core.dao.impl.IDependentDao;
import com.cos.core.dao.impl.IEmployeeDao;
import com.cos.core.modal.TestDependent;
import com.cos.core.modal.TestEmployee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmployeeDependentTransactionService implements IEmployeeDependentTransactionService {

    Class<?>[] classes;
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeDependentTransactionService.class);

    private SessionFactory sessionFactory;
    private IEmployeeDao<TestEmployee> employeeDao;
    private IDependentDao<TestDependent> dependentDao;

    public EmployeeDependentTransactionService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.employeeDao = new EmployeeDao<>(sessionFactory);
        this.dependentDao = new DependentDao<>(sessionFactory);
    }

    @Override
    public void setClasses(Class<?>[] classes) {
        this.classes = classes;
    }

    @Override
    public void saveTransactionalEntities(TestEmployee employee, List<TestDependent> dependents) {
        dependents.forEach(employee::addDependent);

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(employee);
            transaction.commit();
        } catch (Exception e) {
            LOG.warn("transaction error {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
