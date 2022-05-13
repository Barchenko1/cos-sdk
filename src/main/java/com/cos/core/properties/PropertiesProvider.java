package com.cos.core.properties;

import com.cos.core.properties.modal.ConnectionDetails;
import org.hibernate.cfg.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

public class PropertiesProvider implements IPropertiesProvider {

    private Properties properties;

    public PropertiesProvider() {

    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public Properties getDefaultConnectionPoolProperties(ConnectionDetails connectionDetails) {
        Properties settings = new Properties();

        settings.put(Environment.DRIVER, connectionDetails.getDriver());
        settings.put(Environment.URL, connectionDetails.getUrl());
        settings.put(Environment.USER, connectionDetails.getUsername());
        settings.put(Environment.PASS, connectionDetails.getPassword());
        settings.put(Environment.DIALECT, connectionDetails.getDialect());

        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "create-drop");

        settings.put(Environment.C3P0_MIN_SIZE, 5); //Minimum size of pool
        settings.put(Environment.C3P0_MAX_SIZE, 20); //Maximum size of pool
        settings.put(Environment.C3P0_ACQUIRE_INCREMENT, 1); //Number of connections acquired at a time when pool is exhausted
        settings.put(Environment.C3P0_TIMEOUT, 1800); //Connection idle time
        settings.put(Environment.C3P0_MAX_STATEMENTS, 150); //PreparedStatement cache size
        settings.put(Environment.CONNECTION_PROVIDER, connectionDetails.getConnectionPullProviderClass());
        settings.put(Environment.C3P0_CONFIG_PREFIX+".initialPoolSize", 5);

        return settings;
    }

    @Override
    public boolean isHibernateConfigExist() {
        Optional<URL> res = Optional.ofNullable(
                getClass().getClassLoader().getResource("hibernate.cfg.xml"));
        boolean isExist = false;
        if (res.isPresent()) {
            File f = new File(res.get().getFile());
            isExist = f.exists() && !f.isDirectory();
        }
        return isExist;
    }

    @Override
    public void loadProperties(String name) {
        Properties appProps = new Properties();
        String rootPath = Thread.currentThread().getContextClassLoader()
                .getResource("").getPath();
        try {
            appProps.load(new FileInputStream(rootPath + name));
        } catch (IOException ex) {
            throw new RuntimeException();
        }
        properties = appProps;
    }
}
