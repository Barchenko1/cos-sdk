package com.cos.core.config.factory;

import com.cos.core.config.ConfigDbType;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.properties.modal.AbstractConnectionDetails;
import org.hibernate.SessionFactory;

public class ConfigurationSessionFactory implements IConfigurationSessionFactory {
    private final IConnectionPullConfiguration connectionPullConfiguration;
    private final ConfigDbType configDbType;
    private final Class<?>[] annotationClasses;
    private SessionFactory sessionFactory;
    private AbstractConnectionDetails connectionDetails;

    public ConfigurationSessionFactory(IConnectionPullConfiguration connectionPullConfiguration,
                                       ConfigDbType configDbType,
                                       Class<?>[] annotationClasses) {
        this.connectionPullConfiguration = connectionPullConfiguration;
        this.configDbType = configDbType;
        this.annotationClasses = annotationClasses;
    }

    public ConfigurationSessionFactory(IConnectionPullConfiguration connectionPullConfiguration,
                                       ConfigDbType configDbType,
                                       AbstractConnectionDetails connectionDetails,
                                       Class<?>[] annotationClasses) {
        this.connectionPullConfiguration = connectionPullConfiguration;
        this.configDbType = configDbType;
        this.annotationClasses = annotationClasses;
        this.connectionDetails = connectionDetails;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            configureSessionFactory();
        }
        return sessionFactory;
    }

    private void configureSessionFactory() {
        connectionPullConfiguration.setAnnotatedClasses(annotationClasses);
        if (ConfigDbType.XML.equals(configDbType)) {
            this.sessionFactory =
                    connectionPullConfiguration.createSessionFactoryWithHibernateXML();
        }
        if (ConfigDbType.PROPERTY.equals(configDbType)) {
            this.sessionFactory =
                    connectionPullConfiguration.createSessionFactoryWithProperties();
        }
        if (ConfigDbType.CLASS.equals(configDbType)) {
            this.sessionFactory =
                    connectionPullConfiguration.createSessionFactoryWithClassDetails(connectionDetails, annotationClasses);
        }
    }
}
