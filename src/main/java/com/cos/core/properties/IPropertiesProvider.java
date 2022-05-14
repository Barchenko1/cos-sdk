package com.cos.core.properties;

import java.util.Properties;

public interface IPropertiesProvider {
    boolean isHibernateConfigExistsByName(String xmlConfigName);
    Properties getProperties();
    Properties loadProperties();
}
