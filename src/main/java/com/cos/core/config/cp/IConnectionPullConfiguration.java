package com.cos.core.config.cp;

import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.details.ConnectionDetails;
import org.hibernate.SessionFactory;

public interface IConnectionPullConfiguration {

    SessionFactory createSessionFactoryWithProperties();
    SessionFactory createSessionFactoryWithHibernateXML();
//    SessionFactory createSessionFactoryWithHibernateXML(String fileName);
    SessionFactory createSessionFactoryWithClassDetails();
    void setConnectionDetails(ConnectionDetails connectionDetails);
    void setPropertiesProvider(IPropertiesProvider propertiesProvider);
    void setAnnotatedClasses(Class<?>[] classes);
}
