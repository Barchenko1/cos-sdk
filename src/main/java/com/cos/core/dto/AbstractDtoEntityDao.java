package com.cos.core.dto;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BasicTypeReference;

import java.util.List;
import java.util.Map;

public class AbstractDtoEntityDao<E> implements IDtoEntityDao<E> {

    protected final SessionFactory sessionFactory;
    protected Class<E> clazz;
    protected final IDtoAdaptor dtoAdaptor;

    public AbstractDtoEntityDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.dtoAdaptor = new DtoAdaptor();
    }

    @Override
    public void setClazz(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public E getDto(String sqlQuery) {
        Map<String, BasicTypeReference<?>> metadata = dtoAdaptor.getMetadata(clazz);
        try (Session session = sessionFactory.openSession()) {
            EntityManager entityManager = session.getEntityManagerFactory().createEntityManager();
            NativeQuery<E> nativeQuery = entityManager.createNativeQuery(sqlQuery).unwrap(NativeQuery.class);
            for (Map.Entry<String, BasicTypeReference<?>> entry : metadata.entrySet()) {
                nativeQuery.addScalar(entry.getKey(), entry.getValue());
            }
            nativeQuery.setResultListTransformer(Transformers.aliasToBean(clazz));
            return nativeQuery.getSingleResult();
        }
    }

    @Override
    public List<E> getDtoList(String sqlQuery) {
        Map<String, BasicTypeReference<?>> metadata = dtoAdaptor.getMetadata(clazz);
        try (Session session = sessionFactory.openSession()) {
            EntityManager entityManager = session.getEntityManagerFactory().createEntityManager();
            NativeQuery<E> nativeQuery = entityManager.createNativeQuery(sqlQuery).unwrap(NativeQuery.class);
            for (Map.Entry<String, BasicTypeReference<?>> entry : metadata.entrySet()) {
                nativeQuery.addScalar(entry.getKey(), entry.getValue());
            }
            nativeQuery.setResultListTransformer(Transformers.aliasToBean(clazz));
            return nativeQuery.getResultList();
        }
    }
}
