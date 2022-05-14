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

import java.util.List;
import java.util.Properties;
import java.util.Set;

public class ConnectionPullManager implements IConnectionPullManager {

    private static final Set<String> xmlConfigNameCollection = Set.of(
            CosCoreConstants.C3P0_HIBERNATE_XML_FILE_NAME,
            CosCoreConstants.HIKARI_HIBERNATE_XML_FILE_NAME,
            CosCoreConstants.PROXOOL_HIBERNATE_XML_FILE_NAME,
            CosCoreConstants.DBCP2_HIBERNATE_XML_FILE_NAME
    );
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
        if (isHibernateConfigExist()) {
            return getSessionFactoryConfigurationByXmlConfig().createSessionFactoryWithHibernateXML();
        }
        Properties properties = propertiesProvider.loadProperties();
        if (properties != null) {
            return getSessionFactoryConfigurationByProperties(properties).createSessionFactoryWithProperties();
        }
        return connectionPullHikariConfiguration.createDefaultSessionFactory(connectionDetails, annotatedClasses);
    }

    @Override
    public SessionFactory getConfigureSessionFactoryByProperties(String propertiesName) {
        Properties properties = propertiesProvider.loadPropertiesByName(propertiesName);
        if (properties != null) {
            return getSessionFactoryConfigurationByProperties(properties).createSessionFactoryWithProperties();
        }
        return null;
    }

    @Override
    public SessionFactory getConfigureSessionFactoryByXML(String xmlConfigName) {
        if (isHibernateConfigExist(xmlConfigName)) {
            return getSessionFactoryConfigurationByXmlConfig(xmlConfigName).createSessionFactoryWithHibernateXML();
        }
        return null;
    }

    @Override
    public SessionFactory getConfigureSessionFactoryByDefault() {
        return connectionPullHikariConfiguration.createDefaultSessionFactory(connectionDetails, annotatedClasses);
    }

    private boolean isHibernateConfigExist(String xmlConfigName) {
        return propertiesProvider.isHibernateConfigExistsByName(xmlConfigName);
    }

    private boolean isHibernateConfigExist() {
        boolean isExist;
        isExist = xmlConfigNameCollection.stream()
                .map(propertiesProvider::isHibernateConfigExistsByName)
                .findFirst()
                .orElse(false);
        return isExist;
    }

    private IConnectionPullConfiguration getSessionFactoryConfigurationByXmlConfig() {
        if (propertiesProvider.isHibernateConfigExistsByName(CosCoreConstants.C3P0_HIBERNATE_XML_FILE_NAME)) {
            return connectionPullC3P0Configuration;
        }
        if (propertiesProvider.isHibernateConfigExistsByName(CosCoreConstants.HIKARI_HIBERNATE_XML_FILE_NAME)) {
            return connectionPullHikariConfiguration;
        }
        if (propertiesProvider.isHibernateConfigExistsByName(CosCoreConstants.PROXOOL_HIBERNATE_XML_FILE_NAME)) {
            return connectionPullProxoolConfiguration;
        }
        if (propertiesProvider.isHibernateConfigExistsByName(CosCoreConstants.DBCP2_HIBERNATE_XML_FILE_NAME)) {
            return connectionPullDBCP2Configuration;
        }
        throw new RuntimeException("No correct ConnectionProviderClass");
    }

    private IConnectionPullConfiguration getSessionFactoryConfigurationByXmlConfig(String xmlConfigName) {
        if (CosCoreConstants.C3P0_HIBERNATE_XML_FILE_NAME.equals(xmlConfigName)) {
            return connectionPullC3P0Configuration;
        }
        if (CosCoreConstants.HIKARI_HIBERNATE_XML_FILE_NAME.equals(xmlConfigName)) {
            return connectionPullHikariConfiguration;
        }
        if (CosCoreConstants.PROXOOL_HIBERNATE_XML_FILE_NAME.equals(xmlConfigName)) {
            return connectionPullProxoolConfiguration;
        }
        if (CosCoreConstants.DBCP2_HIBERNATE_XML_FILE_NAME.equals(xmlConfigName)) {
            return connectionPullDBCP2Configuration;
        }
        throw new RuntimeException("No correct ConnectionProviderClass");
    }

    private IConnectionPullConfiguration getSessionFactoryConfigurationByProperties(Properties properties) {
        IConnectionPullConfiguration connectionPullConfiguration =
                getConnectionPullConfigurationByConnectionProviderProperty(properties);
        connectionPullConfiguration.setPropertiesProvider(propertiesProvider);
        connectionPullConfiguration.setAnnotatedClasses(annotatedClasses);
        return connectionPullConfiguration;
    }

    private IConnectionPullConfiguration getConnectionPullConfigurationByConnectionProviderProperty(
            Properties properties) {
        String connectionProviderClass = properties.getProperty(Environment.CONNECTION_PROVIDER);
        if ("org.hibernate.c3p0.internal.C3P0ConnectionProvider".equals(connectionProviderClass)) {
            return connectionPullC3P0Configuration;
        }
        if ("com.zaxxer.hikari.hibernate.HikariConnectionProvider".equals(connectionProviderClass)) {
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
