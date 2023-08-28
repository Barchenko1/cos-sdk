package com.cos.core.dto;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BasicTypeReference;

import java.util.List;
import java.util.Map;

public class AbstractDtoEntityDao implements IDtoEntityDao {

    protected final SessionFactory sessionFactory;
    protected Class<?> clazz;
    protected final IDtoAdaptor dtoAdaptor;

    public AbstractDtoEntityDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.dtoAdaptor = new DtoAdaptor();
    }

    @Override
    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public <E> E getDto(String sqlQuery) {
        Map<String, BasicTypeReference<?>> metadata = dtoAdaptor.getMetadata(clazz);
        try (Session session = sessionFactory.openSession()) {
            EntityManager entityManager = session.getEntityManagerFactory().createEntityManager();
            NativeQuery<E> nativeQuery = entityManager.createNativeQuery(sqlQuery).unwrap(NativeQuery.class);
            for (Map.Entry<String, BasicTypeReference<?>> entry : metadata.entrySet()) {
                nativeQuery.addScalar(entry.getKey(), entry.getValue());
            }
            nativeQuery.setResultListTransformer(Transformers.aliasToBean((Class<E>) clazz));
            return nativeQuery.getSingleResult();
        }
    }

    @Override
    public <E> List<E> getDtoList(String sqlQuery) {
        Map<String, BasicTypeReference<?>> metadata = dtoAdaptor.getMetadata(clazz);
        try (Session session = sessionFactory.openSession()) {
            EntityManager entityManager = session.getEntityManagerFactory().createEntityManager();
            NativeQuery<E> nativeQuery = entityManager.createNativeQuery(sqlQuery).unwrap(NativeQuery.class);
            for (Map.Entry<String, BasicTypeReference<?>> entry : metadata.entrySet()) {
                nativeQuery.addScalar(entry.getKey(), entry.getValue());
            }
            nativeQuery.setResultListTransformer(Transformers.aliasToBean((Class<E>) clazz));
            return nativeQuery.getResultList();
        }
    }
}
