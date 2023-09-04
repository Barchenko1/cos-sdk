package com.cos.core.dao;

import com.cos.core.util.converter.ISqlParamsConverter;
import com.cos.core.util.converter.SqlParamsConverter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractDao {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractDao.class);
    protected Class<?> clazz;
    protected final SessionFactory sessionFactory;
    protected final ISqlParamsConverter sqlParamsConverter;

    public AbstractDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.sqlParamsConverter = new SqlParamsConverter();
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public <E> void saveEntity(E entity) {
        classTypeChecker(entity);
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

    public <E> void updateEntity(E entity) {
        classTypeChecker(entity);
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

    public <E> void deleteEntity(E entity) {
        classTypeChecker(entity);
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

    @SuppressWarnings("unchecked")
    public <E> List<E> getEntityListBySQLQuery(String sqlQuery) {
        try (Session session = sessionFactory.openSession()) {
            return (List<E>) session
                    .createNativeQuery(sqlQuery, clazz)
                    .list();
        } catch (Exception e) {
            LOG.warn("get entity error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <E> E getEntityBySQLQuery(String sqlQuery) {
        try (Session session = sessionFactory.openSession()) {
            return (E) session
                    .createNativeQuery(sqlQuery, clazz)
                    .getSingleResult();
        } catch (Exception e) {
            LOG.warn("get entity error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <E> Optional<E> getOptionEntityBySQLQuery(String sqlQuery) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable((E) session
                    .createNativeQuery(sqlQuery, clazz)
                    .getSingleResult());
        } catch (Exception e) {
            LOG.warn("get entity error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <E> List<E> getEntityListBySQLQueryWithParams(String sqlQuery, List<Object> params) {
        Map<Integer, Object> paramMap = sqlParamsConverter.getObjectParamsMap(params);
        try (Session session = sessionFactory.openSession()) {
            NativeQuery<E> nativeQuery = (NativeQuery<E>) session.createNativeQuery(sqlQuery, clazz);
            for (Map.Entry<Integer, Object> entry : paramMap.entrySet()) {
                nativeQuery.setParameter(entry.getKey(), entry.getValue());
            }
            return nativeQuery.list();
        } catch (Exception e) {
            LOG.warn("get entity error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <E> Optional<E> getEntityBySQLQueryWithParams(String sqlQuery, List<Object> params) {
        Map<Integer, Object> paramMap = sqlParamsConverter.getObjectParamsMap(params);
        try (Session session = sessionFactory.openSession()) {
            NativeQuery<E> nativeQuery = (NativeQuery<E>) session.createNativeQuery(sqlQuery, clazz);
            for (Map.Entry<Integer, Object> entry : paramMap.entrySet()) {
                nativeQuery.setParameter(entry.getKey(), entry.getValue());
            }
            return Optional.ofNullable(nativeQuery.getSingleResult());
        } catch (Exception e) {
            LOG.warn("get entity error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private <E> void classTypeChecker(E entity) {
        if (this.clazz != entity.getClass()) {
            throw new RuntimeException("Invalid entity type");
        }
    }
}
