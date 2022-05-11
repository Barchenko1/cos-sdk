package com.cos.core.config;

import org.hibernate.SessionFactory;

public interface IConnectionPullConfiguration {

    SessionFactory createDefaultSessionFactory();
    SessionFactory createSessionFactoryWithProperties();
    SessionFactory createSessionFactoryWithHibernateXML();
}
