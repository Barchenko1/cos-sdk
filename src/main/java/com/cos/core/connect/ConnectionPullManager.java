package com.cos.core.connect;

import com.cos.core.config.ConnectionPullC3P0Configuration;
import com.cos.core.config.ConnectionPullDBCP2Configuration;
import com.cos.core.config.ConnectionPullHikariConfiguration;
import com.cos.core.config.ConnectionPullProxoolConfiguration;
import com.cos.core.config.IConnectionPullConfiguration;
import com.cos.core.dao.AbstractDaoConnector;
import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.PropertiesProvider;
import com.cos.core.properties.modal.ConnectionDetails;
import com.cos.core.util.CosCoreConstants;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;
import java.util.Set;

public class ConnectionPullManager implements IConnectionPullManager {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractDaoConnector.class);
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
            LOG.info("getting hibernate.cfg.xml configuration");
            return getSessionFactoryConfigurationByXmlConfig().createSessionFactoryWithHibernateXML();
        }
        Properties properties = propertiesProvider.loadProperties();
        if (properties != null) {
            LOG.info("getting custom properties configuration");
            return getSessionFactoryConfigurationByProperties(properties).createSessionFactoryWithProperties();
        }
        LOG.info("getting default hikari configuration");
        return connectionPullHikariConfiguration.createDefaultSessionFactory(connectionDetails, annotatedClasses);
    }

    @Override
    public SessionFactory getConfigureSessionFactoryByProperties(String propertiesName) {
        Properties properties = propertiesProvider.loadPropertiesByName(propertiesName);
        if (properties != null) {
            LOG.info("getting custom properties configuration");
            return getSessionFactoryConfigurationByProperties(properties)
                    .createSessionFactoryWithProperties();
        }
        return null;
    }

    @Override
    public SessionFactory getConfigureSessionFactoryByXML(String xmlConfigName) {
        Properties properties = propertiesProvider.loadPropertiesByName(xmlConfigName);
        if (isHibernateConfigExist(xmlConfigName)) {
            LOG.info("getting hibernate.cfg.xml configuration");
            return getSessionFactoryConfigurationByXmlConfig(xmlConfigName)
                    .createSessionFactoryWithHibernateXML();
        }
        return null;
    }

    @Override
    public SessionFactory getConfigureSessionFactoryByDefault() {
        LOG.info("getting default hikari configuration");
        return connectionPullHikariConfiguration
                .createDefaultSessionFactory(connectionDetails, annotatedClasses);
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
        LOG.info("No correct ConnectionProviderClas");
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
        LOG.info("No correct hibernate.cfg.xml configuration");
        throw new RuntimeException("No correct hibernate.cfg.xml configuration");
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
        if (CosCoreConstants.C3P0_CONNECTION_PROVIDER.equals(connectionProviderClass)) {
            return connectionPullC3P0Configuration;
        }
        if (CosCoreConstants.HIKARI_CONNECTION_PROVIDER.equals(connectionProviderClass)) {
            return connectionPullHikariConfiguration;
        }
        if (CosCoreConstants.PROXOOL_CONNECTION_PROVIDER.equals(connectionProviderClass)) {
            return connectionPullProxoolConfiguration;
        }
        if (connectionProviderClass == null) {
            return connectionPullDBCP2Configuration;
        }
        LOG.info("No correct ConnectionProviderClass");
        throw new RuntimeException("No correct ConnectionProviderClass");
    }

}
