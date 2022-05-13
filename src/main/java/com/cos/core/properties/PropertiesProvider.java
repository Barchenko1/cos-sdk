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
