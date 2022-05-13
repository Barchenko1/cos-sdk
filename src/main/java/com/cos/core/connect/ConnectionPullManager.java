package com.cos.core.connect;

import com.cos.core.config.ConnectionPullC3P0Configuration;
import com.cos.core.config.ConnectionPullDBCP2Configuration;
import com.cos.core.config.ConnectionPullHikariConfiguration;
import com.cos.core.config.ConnectionPullProxoolConfiguration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.PropertiesProvider;
import com.cos.core.properties.modal.ConnectionDetails;
import com.cos.core.util.CosCoreConstants;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class ConnectionPullManager implements IConnectionPullManager {

    private final IPropertiesProvider propertiesProvider;
    private Class<?>[] annotatedClasses;
    private ConnectionDetails connectionDetails;
    private final IConnectionPullConfiguration connectionPullC3P0Configuration;
    private final IConnectionPullConfiguration connectionPullHikariConfiguration;
    private final IConnectionPullConfiguration connectionPullProxoolConfiguration;
    private final IConnectionPullConfiguration connectionPullDBCP2Configuration;

    public ConnectionPullManager() {
        this.propertiesProvider = new PropertiesProvider();
        this.connectionPullC3P0Configuration = new ConnectionPullC3P0Configuration();
        this.connectionPullHikariConfiguration = new ConnectionPullHikariConfiguration();
        this.connectionPullProxoolConfiguration = new ConnectionPullProxoolConfiguration();
        this.connectionPullDBCP2Configuration = new ConnectionPullDBCP2Configuration();

    }

    @Override
    public void setAnnotatedClasses(Class<?>[] annotatedClasses) {
        this.annotatedClasses = annotatedClasses;
    }

    @Override
    public void setConnectionDetails(ConnectionDetails connectionDetails) {
        this.connectionDetails = connectionDetails;
    }

    @Override
    public SessionFactory getConfigureSessionFactory() {
        if (propertiesProvider.isHibernateConfigExist()) {
            return connectionPullC3P0Configuration.createSessionFactoryWithHibernateXML();
        }
        propertiesProvider.loadProperties(CosCoreConstants.DB_PROPERTIES_FILE_NAME);
        Properties properties = propertiesProvider.getProperties();

        if (properties != null) {
            IConnectionPullConfiguration connectionPullConfiguration =
                    getConnectionPullConfigurationByConnectionProviderProperty(properties);
            connectionPullConfiguration.setPropertiesProvider(propertiesProvider);
            connectionPullConfiguration.setAnnotatedClasses(annotatedClasses);
            return connectionPullConfiguration.createSessionFactoryWithProperties();
        }
        return connectionPullHikariConfiguration.createDefaultSessionFactory(connectionDetails, annotatedClasses);
    }

    private IConnectionPullConfiguration getConnectionPullConfigurationByConnectionProviderProperty(Properties properties) {
        String connectionProviderClass = properties.getProperty(Environment.CONNECTION_PROVIDER);
        if ("org.hibernate.c3p0.internal.C3P0ConnectionProvider".equals(connectionProviderClass)) {
            return connectionPullC3P0Configuration;
        }
        if ("com.zaxxer.hikari.pool.HikariProxyConnection".equals(connectionProviderClass)) {
            return connectionPullHikariConfiguration;
        }
        if ("org.hibernate.proxool.internal.ProxoolConnectionProvider".equals(connectionProviderClass)) {
            return connectionPullProxoolConfiguration;
        }
        if ("".equals(connectionProviderClass)) {
            return connectionPullDBCP2Configuration;
        }
        throw new RuntimeException("No correct ConnectionProviderClass");
    }

}
