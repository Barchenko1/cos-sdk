package com.cos.core.config;

import com.cos.core.properties.IPropertiesProvider;
import org.hibernate.SessionFactory;

public class ConnectionPullProxoolConfiguration extends AbstractConnectionPullConfiguration {

    @Override
    public SessionFactory createSessionFactoryWithProperties(Class<?>[] annotatedClasses) {
        return null;
    }

}
