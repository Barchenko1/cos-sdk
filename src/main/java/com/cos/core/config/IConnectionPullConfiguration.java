package com.cos.core.config;

import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.modal.ConnectionDetails;
import org.hibernate.SessionFactory;

public interface IConnectionPullConfiguration {

    SessionFactory createSessionFactoryWithProperties();
    SessionFactory createSessionFactoryWithHibernateXML();
    SessionFactory createDefaultSessionFactory(ConnectionDetails connectionDetails, Class<?>[] annotatedClasses);
    void setPropertiesProvider(IPropertiesProvider propertiesProvider);
    void setAnnotatedClasses(Class<?>[] classes);
}
