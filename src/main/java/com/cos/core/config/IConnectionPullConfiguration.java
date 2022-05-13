package com.cos.core.config;

import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.PropertiesProvider;
import com.cos.core.properties.modal.ConnectionDetails;
import org.hibernate.SessionFactory;

public interface IConnectionPullConfiguration {

    SessionFactory createSessionFactoryWithProperties(Class<?>[] annotatedClasses);
    SessionFactory createSessionFactoryWithHibernateXML();
    SessionFactory createDefaultSessionFactory(ConnectionDetails connectionDetails, Class<?>[] annotatedClasses);

    void setPropertiesProvider(IPropertiesProvider propertiesProvider);
}
