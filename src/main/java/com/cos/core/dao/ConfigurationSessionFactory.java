package com.cos.core.dao;

import com.cos.core.config.IConnectionPullConfiguration;
import org.hibernate.SessionFactory;

public class ConfigurationSessionFactory {
    IConnectionPullConfiguration connectionPullConfiguration;
    private final Class<?>[] classes;
    private SessionFactory sessionFactory;
    public ConfigurationSessionFactory(IConnectionPullConfiguration connectionPullConfiguration,
                                       Class<?>[] classes) {
        this.connectionPullConfiguration = connectionPullConfiguration;
        this.classes = classes;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            configureSessionFactory();
        }
        return sessionFactory;
    }

    public void configureSessionFactory() {
        connectionPullConfiguration.setAnnotatedClasses(classes);
        this.sessionFactory =
                connectionPullConfiguration.createSessionFactoryWithHibernateXML();
    }
}
