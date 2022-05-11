package com.cos.core.connect;

import com.cos.core.config.ConnectionPullConfiguration;
import com.cos.core.config.IConnectionPullConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.boot.cfgxml.internal.ConfigLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

public abstract class AbstractDaoConnector {

    private final Properties properties;
    private final IConnectionPullConfiguration connectionPullConfiguration;
    private final IConnectionPullConfiguration dbcp2ConnectionPullConfiguration;

    public AbstractDaoConnector() {
        this.properties = defaultLoadProperties();
        this.connectionPullConfiguration = new ConnectionPullConfiguration(properties);
        this.dbcp2ConnectionPullConfiguration = new ConnectionPullConfiguration(properties);
    }

    public AbstractDaoConnector(Properties properties) {
        this.properties = properties;
        this.connectionPullConfiguration = new ConnectionPullConfiguration(properties);
        this.dbcp2ConnectionPullConfiguration = new ConnectionPullConfiguration(properties);
    }

    private Properties loadProperties() {
        return properties != null ? properties : defaultLoadProperties();
    }

    private Properties defaultLoadProperties() {
        Properties appProps = new Properties();
        String rootPath = Thread.currentThread().getContextClassLoader()
                .getResource("").getPath();
        try {
            appProps.load(new FileInputStream(rootPath + "db.properties"));
        } catch (IOException ex) {
            throw new RuntimeException();
        }
        return appProps;
    }

    public SessionFactory getSessionFactory() {
        if (isHibernateConfigExist()) {
            return connectionPullConfiguration.createSessionFactoryWithHibernateXML();
        }
        if (properties != null) {
            return connectionPullConfiguration.createSessionFactoryWithProperties();
        }
        return connectionPullConfiguration.createDefaultSessionFactory();
    }

    private boolean isHibernateConfigExist() {
        Optional<URL> res = Optional.ofNullable(
                getClass().getClassLoader().getResource("hibernate.cfg.xml"));
        boolean isExist = false;
        if (res.isPresent()) {
            File f = new File(res.get().getFile());
            isExist = f.exists() && !f.isDirectory();
        }
        return isExist;
    }

}
