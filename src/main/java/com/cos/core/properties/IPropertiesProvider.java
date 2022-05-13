package com.cos.core.properties;

import java.util.Properties;

public interface IPropertiesProvider {
    boolean isHibernateConfigExist();
    Properties getProperties();
    void loadProperties(String name);
}
