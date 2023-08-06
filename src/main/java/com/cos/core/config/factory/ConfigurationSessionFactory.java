package com.cos.core.config.factory;

import com.cos.core.config.ConfigDbType;
import com.cos.core.config.ConnectionPoolType;
import com.cos.core.config.ConnectionPullC3P0Configuration;
import com.cos.core.config.ConnectionPullDBCP2Configuration;
import com.cos.core.config.ConnectionPullHikariConfiguration;
import com.cos.core.config.ConnectionPullViburConfiguration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.properties.modal.AbstractConnectionDetails;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationSessionFactory implements IConfigurationSessionFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPullC3P0Configuration.class);

    private final ConnectionPoolType connectionPoolType;
    private final ConfigDbType configDbType;
    private final Class<?>[] annotationClasses;
    private SessionFactory sessionFactory;
    private AbstractConnectionDetails connectionDetails;

    public ConfigurationSessionFactory(ConnectionPoolType connectionPoolType,
                                       ConfigDbType configDbType,
                                       Class<?>[] annotationClasses) {
        this.connectionPoolType = connectionPoolType;
        this.configDbType = configDbType;
        this.annotationClasses = annotationClasses;
    }

    public ConfigurationSessionFactory(ConnectionPoolType connectionPoolType,
                                       ConfigDbType configDbType,
                                       AbstractConnectionDetails connectionDetails,
                                       Class<?>[] annotationClasses) {
        this.connectionPoolType = connectionPoolType;
        this.configDbType = configDbType;
        this.annotationClasses = annotationClasses;
        this.connectionDetails = connectionDetails;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            this.sessionFactory = configureSessionFactory();
        }
        return sessionFactory;
    }

    private SessionFactory configureSessionFactory() {
        IConnectionPullConfiguration connectionPullConfiguration = connectionPullConfigurationFactory();
        return configDbSessionFactory(connectionPullConfiguration);
    }

    private SessionFactory configDbSessionFactory(IConnectionPullConfiguration connectionPullConfiguration) {
        SessionFactory sessionFactory = null;
        if (ConfigDbType.XML.equals(configDbType)) {
            sessionFactory =
                    connectionPullConfiguration.createSessionFactoryWithHibernateXML();
        }
        if (ConfigDbType.PROPERTY.equals(configDbType)) {
            sessionFactory =
                    connectionPullConfiguration.createSessionFactoryWithProperties();
        }
        if (ConfigDbType.CLASS.equals(configDbType)) {
            sessionFactory =
                    connectionPullConfiguration.createSessionFactoryWithClassDetails(connectionDetails, annotationClasses);
        }
        if (sessionFactory == null) {
            LOG.warn("no configuration selected");
            throw new RuntimeException();
        }

        return sessionFactory;
    }

    private IConnectionPullConfiguration connectionPullConfigurationFactory() {
        IConnectionPullConfiguration connectionPullConfiguration = null;
        if (ConnectionPoolType.HIKARI.equals(connectionPoolType)) {
            connectionPullConfiguration = new ConnectionPullHikariConfiguration();
        }
        if (ConnectionPoolType.C3P0.equals(connectionPoolType)) {
            connectionPullConfiguration = new ConnectionPullC3P0Configuration();
        }
        if (ConnectionPoolType.DBCP2.equals(connectionPoolType)) {
            connectionPullConfiguration = new ConnectionPullDBCP2Configuration();
        }
        if (ConnectionPoolType.VIBUR.equals(connectionPoolType)) {
            connectionPullConfiguration = new ConnectionPullViburConfiguration();
        }
        if (connectionPullConfiguration == null) {
            LOG.warn("no connected pool selected");
            throw new RuntimeException();
        }

        connectionPullConfiguration.setAnnotatedClasses(annotationClasses);
        return connectionPullConfiguration;
    }
}
