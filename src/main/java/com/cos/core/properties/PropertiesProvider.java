package com.cos.core.properties;

import com.cos.core.util.CosCoreConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

public class PropertiesProvider implements IPropertiesProvider {
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesProvider.class);
    private Properties properties;

    public PropertiesProvider() {

    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public boolean isHibernateConfigExistsByName(String xmlConfigName) {
        Optional<URL> res = Optional.ofNullable(
                getClass().getClassLoader().getResource(xmlConfigName));
        boolean isExist = false;
        if (res.isPresent()) {
            File f = new File(res.get().getFile());
            isExist = f.exists() && !f.isDirectory();
        }
        return isExist;
    }

    @Override
    public Properties loadProperties() {
        loadPropertiesByPropertyName(CosCoreConstants.DB_PROPERTIES_FILE_NAME);
        return properties;
    }

    @Override
    public Properties loadPropertiesByName(String name) {
        loadPropertiesByPropertyName(name);
        return properties;
    }

    private void loadPropertiesByPropertyName(String name) {
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
