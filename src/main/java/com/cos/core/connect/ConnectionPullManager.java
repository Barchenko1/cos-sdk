package com.cos.core.connect;

import com.cos.core.config.ConnectionPullC3P0Configuration;
import com.cos.core.config.ConnectionPullDBCP2Configuration;
import com.cos.core.config.ConnectionPullHikariConfiguration;
import com.cos.core.config.ConnectionPullProxoolConfiguration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.PropertiesProvider;
import com.cos.core.properties.modal.ConnectionDetails;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class ConnectionPullManager implements IConnectionPullManager {

    private final IPropertiesProvider propertiesProvider;
    private final IConnectionPullConfiguration connectionPullC3P0Configuration;
    private final IConnectionPullConfiguration connectionPullHikariConfiguration;
    private final IConnectionPullConfiguration connectionPullProxoolConfiguration;
    private final IConnectionPullConfiguration connectionPullDBCP2Configuration;
    private Class<?>[] annotatedClasses;

    public ConnectionPullManager() {
        this.propertiesProvider = new PropertiesProvider();
        this.connectionPullC3P0Configuration = new ConnectionPullC3P0Configuration();
        this.connectionPullHikariConfiguration = new ConnectionPullHikariConfiguration();
        this.connectionPullProxoolConfiguration = new ConnectionPullProxoolConfiguration();
        this.connectionPullDBCP2Configuration = new ConnectionPullDBCP2Configuration();

    }

    public void setAnnotatedClasses(Class<?>[] annotatedClasses) {
        this.annotatedClasses = annotatedClasses;
    }

    @Override
    public SessionFactory getConfigureSessionFactory() {
        Properties properties = propertiesProvider.loadProperties("db.properties");

        if (propertiesProvider.isHibernateConfigExist()) {
            return connectionPullC3P0Configuration.createSessionFactoryWithHibernateXML();
        }
        if (properties != null) {
            return getConnectionPullConfigurationByConnectionProviderProperty(properties)
                    .createSessionFactoryWithProperties(annotatedClasses);
        }
        return null;
    }

    @Override
    public SessionFactory getDefaultSessionFactory(ConnectionDetails connectionDetails) {
        return connectionPullC3P0Configuration.createDefaultSessionFactory(connectionDetails, annotatedClasses);
    }

    private IConnectionPullConfiguration getConnectionPullConfigurationByConnectionProviderProperty(Properties properties) {
        String connectionProviderClass = properties.getProperty(Environment.CONNECTION_PROVIDER);
        if ("org.hibernate.c3p0.internal.C3P0ConnectionProvider".equals(connectionProviderClass)) {
            return connectionPullC3P0Configuration;
        }
        if ("".equals(connectionProviderClass)) {
            return connectionPullHikariConfiguration;
        }
        if ("".equals(connectionProviderClass)) {
            return connectionPullProxoolConfiguration;
        }
        if ("".equals(connectionProviderClass)) {
            return connectionPullDBCP2Configuration;
        }
        throw new RuntimeException("No correct ConnectionProviderClass");
    }

}
