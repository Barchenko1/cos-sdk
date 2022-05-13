package com.cos.core.config;

import com.cos.core.modal.Book;
import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.PropertiesProvider;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.c3p0.internal.C3P0ConnectionProvider;
import org.hibernate.cfg.Environment;

import org.hibernate.SessionFactory;

import java.util.Properties;

public class ConnectionPullC3P0Configuration extends AbstractConnectionPullConfiguration {

    @Override
    public SessionFactory createSessionFactoryWithProperties(Class<?>[] annotatedClasses) {
        if (sessionFactory == null) {
            try {
                Properties settings = new Properties();
                Properties properties = propertiesProvider.getProperties();
                setGeneralDBSettings(settings);

                settings.put(Environment.SHOW_SQL, properties.getProperty(Environment.SHOW_SQL));
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,
                        properties.getProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS));
                settings.put(Environment.HBM2DDL_AUTO, properties.getProperty(Environment.HBM2DDL_AUTO));

                settings.put(Environment.C3P0_MIN_SIZE, properties.getProperty(Environment.C3P0_MIN_SIZE)); //Minimum size of pool
                settings.put(Environment.C3P0_MAX_SIZE, properties.getProperty(Environment.C3P0_MAX_SIZE)); //Maximum size of pool
                settings.put(Environment.C3P0_ACQUIRE_INCREMENT, properties.getProperty(Environment.C3P0_ACQUIRE_INCREMENT)); //Number of connections acquired at a time when pool is exhausted
                settings.put(Environment.C3P0_TIMEOUT, properties.getProperty(Environment.C3P0_TIMEOUT)); //Connection idle time
                settings.put(Environment.C3P0_MAX_STATEMENTS, properties.getProperty(Environment.C3P0_MAX_STATEMENTS)); //PreparedStatement cache size
                settings.put(Environment.CONNECTION_PROVIDER, C3P0ConnectionProvider.class);
                settings.put(Environment.C3P0_CONFIG_PREFIX+".initialPoolSize",
                        properties.getProperty(Environment.C3P0_CONFIG_PREFIX+".initialPoolSize"));

    //            settings.put(Environment.SHOW_SQL, "true");
    //            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
    //            settings.put(Environment.HBM2DDL_AUTO, "create-drop");
    //
    //            settings.put(Environment.C3P0_MIN_SIZE, 5); //Minimum size of pool
    //            settings.put(Environment.C3P0_MAX_SIZE, 20); //Maximum size of pool
    //            settings.put(Environment.C3P0_ACQUIRE_INCREMENT, 1); //Number of connections acquired at a time when pool is exhausted
    //            settings.put(Environment.C3P0_TIMEOUT, 1800); //Connection idle time
    //            settings.put(Environment.C3P0_MAX_STATEMENTS, 150); //PreparedStatement cache size
    //            settings.put(Environment.CONNECTION_PROVIDER, C3P0ConnectionProvider.class);
    //            settings.put(Environment.C3P0_CONFIG_PREFIX+".initialPoolSize", 5);

                    serviceRegistry = new StandardServiceRegistryBuilder()
                            .applySettings(settings).build();

                    Metadata metadata = new MetadataSources(serviceRegistry)
                            .addAnnotatedClasses(annotatedClasses)
                            .getMetadataBuilder()
                            .build();

                    sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                if (serviceRegistry != null) {
                    StandardServiceRegistryBuilder.destroy(serviceRegistry);
                }
                throw new RuntimeException();
            }
        }
        return sessionFactory;
    }

}
