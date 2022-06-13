package com.cos.core.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;

public class AbstractDaoConfigurationTest {

    protected static SessionFactory sessionFactory;

    @AfterAll
    public static void cleanSessionFactory() {
        sessionFactory = null;
    }

}
