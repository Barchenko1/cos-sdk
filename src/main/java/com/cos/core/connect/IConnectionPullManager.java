package com.cos.core.connect;

import com.cos.core.properties.modal.ConnectionDetails;
import org.hibernate.SessionFactory;

public interface IConnectionPullManager {

    SessionFactory getConfigureSessionFactory();

    SessionFactory getDefaultSessionFactory(ConnectionDetails connectionDetails);

}
