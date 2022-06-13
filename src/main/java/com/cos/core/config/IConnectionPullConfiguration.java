package com.cos.core.config;

import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.modal.AbstractConnectionDetails;
import org.hibernate.SessionFactory;

public interface IConnectionPullConfiguration {

    SessionFactory createSessionFactoryWithProperties();
    SessionFactory createSessionFactoryWithHibernateXML();
    SessionFactory createClassDetailsSessionFactory(AbstractConnectionDetails connectionDetails, Class<?>[] annotatedClasses);
    void setPropertiesProvider(IPropertiesProvider propertiesProvider);
    void setAnnotatedClasses(Class<?>[] classes);
}
