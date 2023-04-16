package com.cos.core.test.service;

import com.cos.core.test.modal.TestDependent;
import com.cos.core.test.modal.TestEmployee;
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

    public EmployeeDependentTransactionService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void setClasses(Class<?>[] classes) {
        this.classes = classes;
    }

    @Override
    public void saveTransactionalEntities(TestEmployee employee, List<TestDependent> dependents) {
        Transaction transaction = null;
        dependents.forEach(employee::addDependent);
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(employee);
//            employee.getDependents().forEach(session::persist);
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
