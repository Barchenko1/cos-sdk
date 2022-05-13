package com.cos.core.properties;

import com.cos.core.properties.modal.ConnectionDetails;

import java.util.Properties;

public interface IPropertiesProvider {
    Properties getDefaultConnectionPoolProperties(ConnectionDetails connectionDetails);
    boolean isHibernateConfigExist();
    Properties getProperties();
    Properties loadProperties(String name);
}
