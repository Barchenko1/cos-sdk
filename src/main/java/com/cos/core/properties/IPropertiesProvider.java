package com.cos.core.properties;

import com.cos.core.properties.modal.ConnectionDetails;

import java.util.Properties;

public interface IPropertiesProvider {
    boolean isHibernateConfigExist();
    Properties getProperties();
    void loadProperties(String name);
}
