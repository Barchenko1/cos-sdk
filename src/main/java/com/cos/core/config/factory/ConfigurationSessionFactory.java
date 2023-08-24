package com.cos.core.config.factory;

import com.cos.core.config.ConfigDbType;
import com.cos.core.config.ConnectionPoolType;
import com.cos.core.config.cp.*;
import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.PropertiesProvider;
import com.cos.core.properties.details.ConnectionDetails;
import com.cos.core.util.CosCoreConstants;
import com.cos.core.util.format.FileFormat;
import com.cos.core.util.format.FileFormatter;
import com.cos.core.util.format.IFileFormatter;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationSessionFactory implements IConfigurationSessionFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPullC3P0Configuration.class);

    private final IFileFormatter fileFormatter = new FileFormatter();
    private final ConnectionPoolType connectionPoolType;
    private ConfigDbType configDbType;
    private SessionFactory sessionFactory;
    private Class<?>[] annotationClasses;
    private ConnectionDetails connectionDetails;
    private String configFileName;

    public ConfigurationSessionFactory(ConnectionPoolType connectionPoolType) {
        this.connectionPoolType = connectionPoolType;
        this.configDbType = ConfigDbType.XML;
    }

    public ConfigurationSessionFactory(String configFileName) {
        this.connectionPoolType = ConnectionPoolType.CUSTOM;
        this.configFileName = configFileName;
    }

    public ConfigurationSessionFactory(ConnectionPoolType connectionPoolType,
                                       Class<?>[] annotationClasses) {
        this.connectionPoolType = connectionPoolType;
        this.configDbType = ConfigDbType.PROPERTIES;
        this.annotationClasses = annotationClasses;
    }

    public ConfigurationSessionFactory(ConnectionPoolType connectionPoolType,
                                       ConnectionDetails connectionDetails,
                                       Class<?>[] annotationClasses) {
        this.connectionPoolType = connectionPoolType;
        this.configDbType = ConfigDbType.CLASS;
        this.annotationClasses = annotationClasses;
        this.connectionDetails = connectionDetails;
    }

    public SessionFactory getSessionFactory() {
//        if (sessionFactory == null) {
//            this.sessionFactory = configureSessionFactory();
//        }
        return configureSessionFactory();
    }

    private SessionFactory configureSessionFactory() {
        IConnectionPullConfiguration connectionPullConfiguration = connectionPullConfigurationFactory();
        return configDbSessionFactory(connectionPullConfiguration);
    }

    private SessionFactory configDbSessionFactory(IConnectionPullConfiguration connectionPullConfiguration) {
        SessionFactory sessionFactory = null;
        if (configFileName != null) {
            FileFormat fileFormat = fileFormatter.getFormat(configFileName);
            if (FileFormat.XML.equals(fileFormat)) {
                sessionFactory =
                        connectionPullConfiguration.createSessionFactoryWithHibernateXML();
            }
            if (FileFormat.PROPERTIES.equals(fileFormat)) {
                connectionPullConfiguration.setAnnotatedClasses(annotationClasses);
                IPropertiesProvider propertiesProvider = propertiesProviderFactory();
                connectionPullConfiguration.setPropertiesProvider(propertiesProvider);
                sessionFactory =
                        connectionPullConfiguration.createSessionFactoryWithProperties();
            }
        } else {
            if (ConfigDbType.XML.equals(configDbType)) {
                sessionFactory =
                        connectionPullConfiguration.createSessionFactoryWithHibernateXML();
            }
            if (ConfigDbType.PROPERTIES.equals(configDbType)) {
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
        if (ConnectionPoolType.CUSTOM.equals(connectionPoolType)) {
            connectionPullConfiguration = new ConnectionPullConfiguration(configFileName);
        }
        if (connectionPullConfiguration == null) {
            LOG.warn("no connected pool selected");
            throw new RuntimeException();
        }

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
