package com.cos.core.dao;

import com.cos.core.util.converter.ISqlParamsConverter;
import com.cos.core.util.converter.SqlParamsConverter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractDao<E> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractDao.class);
    protected Class<E> clazz;
    protected final SessionFactory sessionFactory;
    protected final ISqlParamsConverter sqlParamsConverter;

    public AbstractDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.sqlParamsConverter = new SqlParamsConverter();
    }

    public void setClazz(Class<E> clazz) {
        this.clazz = clazz;
    }

    public void saveEntity(E entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            LOG.warn("transaction error {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    public void updateEntity(E entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            LOG.warn("transaction error {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    public void deleteEntity(E entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            LOG.warn("transaction error {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    public List<E> getEntityListBySQLQuery(String sqlQuery) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createNativeQuery(sqlQuery, clazz)
                    .list();
        } catch (Exception e) {
            LOG.warn("get entity error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<E> getEntityListBySQLQueryWithParams(String sqlQuery, List<Object> params) {
        Map<Integer, Object> paramMap = sqlParamsConverter.getObjectParamsMap(params);
        try (Session session = sessionFactory.openSession()) {
            NativeQuery<E> nativeQuery = session.createNativeQuery(sqlQuery, clazz);
            for (Map.Entry<Integer, Object> entry : paramMap.entrySet()) {
                nativeQuery.setParameter(entry.getKey(), entry.getValue());
            }
            return nativeQuery.list();
        } catch (Exception e) {
            LOG.warn("get entity error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Optional<E> getEntityBySQLQueryWithParams(String sqlQuery, List<Object> params) {
        Map<Integer, Object> paramMap = sqlParamsConverter.getObjectParamsMap(params);
        try (Session session = sessionFactory.openSession()) {
            NativeQuery<E> nativeQuery = session.createNativeQuery(sqlQuery, clazz);
            for (Map.Entry<Integer, Object> entry : paramMap.entrySet()) {
                nativeQuery.setParameter(entry.getKey(), entry.getValue());
            }
            return Optional.ofNullable(nativeQuery.getSingleResult());
        } catch (Exception e) {
            LOG.warn("get entity error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
