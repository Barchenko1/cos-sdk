package com.cos.core.connect;

import com.cos.core.properties.modal.ConnectionDetails;
import org.hibernate.SessionFactory;

import java.sql.Connection;

public interface IConnectionPullManager {

    SessionFactory getConfigureSessionFactory();
    void setAnnotatedClasses(Class<?>[] annotatedClasses);
    void setConnectionDetails(ConnectionDetails connectionDetails);


    //test
    SessionFactory getConfigureSessionFactoryByProperties(String propertiesName);
    SessionFactory getConfigureSessionFactoryByXML(String xmlConfigName);
    SessionFactory getConfigureSessionFactoryByDefault();

}
