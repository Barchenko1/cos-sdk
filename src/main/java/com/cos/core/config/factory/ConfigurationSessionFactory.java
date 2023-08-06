package com.cos.core.config.factory;

import com.cos.core.config.ConfigDbType;
import com.cos.core.config.ConnectionPoolType;
import com.cos.core.config.ConnectionPullC3P0Configuration;
import com.cos.core.config.ConnectionPullDBCP2Configuration;
import com.cos.core.config.ConnectionPullHikariConfiguration;
import com.cos.core.config.ConnectionPullViburConfiguration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.PropertiesProvider;
import com.cos.core.properties.cd.ConnectionDetails;
import com.cos.core.util.CosCoreConstants;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationSessionFactory implements IConfigurationSessionFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPullC3P0Configuration.class);

    private final ConnectionPoolType connectionPoolType;
    private final ConfigDbType configDbType;
    private SessionFactory sessionFactory;
    private Class<?>[] annotationClasses;
    private ConnectionDetails connectionDetails;

    public ConfigurationSessionFactory(ConnectionPoolType connectionPoolType,
                                       ConfigDbType configDbType) {
        this.connectionPoolType = connectionPoolType;
        this.configDbType = configDbType;
    }

    public ConfigurationSessionFactory(ConnectionPoolType connectionPoolType,
                                       ConfigDbType configDbType,
                                       Class<?>[] annotationClasses) {
        this.connectionPoolType = connectionPoolType;
        this.configDbType = configDbType;
        this.annotationClasses = annotationClasses;
    }

    public ConfigurationSessionFactory(ConnectionPoolType connectionPoolType,
                                       ConfigDbType configDbType,
                                       ConnectionDetails connectionDetails,
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
            connectionPullConfiguration.setAnnotatedClasses(annotationClasses);
            IPropertiesProvider propertiesProvider = propertiesProviderFactory();
            connectionPullConfiguration.setPropertiesProvider(propertiesProvider);
            sessionFactory =
                    connectionPullConfiguration.createSessionFactoryWithProperties();
        }
        if (ConfigDbType.CLASS.equals(configDbType)) {
            connectionPullConfiguration.setAnnotatedClasses(annotationClasses);
            connectionPullConfiguration.setConnectionDetails(connectionDetails);
            sessionFactory =
                    connectionPullConfiguration.createSessionFactoryWithClassDetails();
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

    private IPropertiesProvider propertiesProviderFactory() {
        IPropertiesProvider propertiesProvider = new PropertiesProvider();
        if (ConnectionPoolType.HIKARI.equals(connectionPoolType)) {
            propertiesProvider.loadPropertiesByName(CosCoreConstants.HIKARI_PROPERTIES_FILE_NAME);
        }
        if (ConnectionPoolType.C3P0.equals(connectionPoolType)) {
            propertiesProvider.loadPropertiesByName(CosCoreConstants.C3P0_PROPERTIES_FILE_NAME);
        }
        if (ConnectionPoolType.DBCP2.equals(connectionPoolType)) {
            propertiesProvider.loadPropertiesByName(CosCoreConstants.DBCP2_PROPERTIES_FILE_NAME);
        }
        if (ConnectionPoolType.VIBUR.equals(connectionPoolType)) {
            propertiesProvider.loadPropertiesByName(CosCoreConstants.VIBUR_PROPERTIES_FILE_NAME);
        }

        return propertiesProvider;
    }
}
