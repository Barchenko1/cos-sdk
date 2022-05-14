package com.cos.core.connect;

import com.cos.core.properties.modal.ConnectionDetails;
import org.hibernate.SessionFactory;

public interface IConnectionPullManager {

    SessionFactory getConfigureSessionFactory();
    void setAnnotatedClasses(Class<?>[] annotatedClasses);
    void setConnectionDetails(ConnectionDetails connectionDetails);


    //test
    SessionFactory getConfigureSessionFactoryByProperties();
    SessionFactory getConfigureSessionFactoryByXML();
    SessionFactory getConfigureSessionFactoryByDefault();

}
