package com.cos.core.dao.product.impl;

import com.cos.core.dao.AbstractDao;
import com.cos.core.dao.product.IProductDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static com.core.im.query.ProductQuery.GET_PRODUCT_BY_NAME;
import static com.core.im.query.ProductQuery.GET_PRODUCT_LIST;

public class ProductDao<E> extends AbstractDao<E> implements IProductDao<E> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductDao.class);

    public ProductDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<E> getAllProducts() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createNativeQuery(GET_PRODUCT_LIST, clazz)
                    .list();
        } catch (Exception e) {
            LOG.warn("get entity error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<E> getProductByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session
                    .createNativeQuery(GET_PRODUCT_BY_NAME, clazz)
                    .setParameter(1, name)
                    .getSingleResult());
        } catch (Exception e) {
            LOG.warn("get entity error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
