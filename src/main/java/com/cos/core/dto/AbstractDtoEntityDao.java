package com.cos.core.dto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AbstractDtoEntityDao implements IDtoEntityDao {

    protected final SessionFactory sessionFactory;
    protected final IDtoAdaptor dtoAdaptor;

    public AbstractDtoEntityDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.dtoAdaptor = new DtoAdaptor();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E getDto(String sqlQuery, Class<?> clazz) {
        try (Session session = sessionFactory.openSession()) {
            EntityManager entityManager = session.getEntityManagerFactory().createEntityManager();
            TypedQuery<Tuple> nativeQuery = (TypedQuery<Tuple>) (entityManager).createNativeQuery(sqlQuery, Tuple.class);

            Tuple tuple = nativeQuery.getSingleResult();
            Field[] classFields = clazz.getDeclaredFields();
            try {
                E resultObject = (E) clazz.getDeclaredConstructor().newInstance();
                for (Field field : classFields) {
                    String setterMethodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    Method setterMethod = clazz.getMethod(setterMethodName, field.getType());
                    setterMethod.invoke(resultObject, tuple.get(field.getName()));
                }
                return resultObject;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> getDtoList(String sqlQuery, Class<?> clazz) {
        try (Session session = sessionFactory.openSession()) {
            EntityManager entityManager = session.getEntityManagerFactory().createEntityManager();
            TypedQuery<Tuple> nativeQuery = (TypedQuery<Tuple>) (entityManager).createNativeQuery(sqlQuery, Tuple.class);
            List<Tuple> tupleList = nativeQuery.getResultList();
            List<E> result = new ArrayList<>();
            Field[] classFields = clazz.getDeclaredFields();
            for (Tuple tuple : tupleList) {
                Object resultObject;
                try {
                    resultObject = clazz.getDeclaredConstructor().newInstance();
                    for (Field field : classFields) {
                        String setterMethodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                        Method setterMethod = clazz.getMethod(setterMethodName, field.getType());
                        setterMethod.invoke(resultObject, tuple.get(field.getName()));
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                result.add((E) resultObject);
            }
            return result;
        }
    }
}
