package com.cos.core.transaction;

import com.cos.core.annotation.AnnotationChecker;
import com.cos.core.annotation.IAnnotationChecker;
import com.cos.core.annotation.TransactionalApplicable;
import com.cos.core.dao.AbstractDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AbstractTransactionManager implements ITransactionManager {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractDao.class);
    private final IAnnotationChecker annotationChecker = new AnnotationChecker();

    protected final SessionFactory sessionFactory;

    public AbstractTransactionManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void useTransaction(List<?> values) {
        if (!annotationChecker.areAllElementsHaveAnnotation(values, TransactionalApplicable.class)) {
            throw new RuntimeException();
        }
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            values.forEach(value -> {
                session.persist(value);
            });
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
