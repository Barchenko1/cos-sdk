package com.cos.core.config;

import com.cos.core.modal.Book;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.proxool.internal.ProxoolConnectionProvider;

import java.util.Properties;

public class ConnectionPullProxoolConfiguration extends AbstractConnectionPullConfiguration {

    @Override
    public SessionFactory createSessionFactoryWithProperties() {
        if (sessionFactory == null) {
            try {
                Properties settings = new Properties();
                Properties properties = propertiesProvider.getProperties();
                setGeneralDBSettings(settings);

                settings.put(Environment.HBM2DDL_AUTO, properties.getProperty(Environment.HBM2DDL_AUTO));
                settings.put(Environment.SHOW_SQL, properties.getProperty(Environment.SHOW_SQL));

                settings.put(Environment.PROXOOL_POOL_ALIAS, properties.getProperty(Environment.PROXOOL_POOL_ALIAS));
                settings.put(Environment.PROXOOL_EXISTING_POOL, properties.getProperty(Environment.PROXOOL_EXISTING_POOL));
                settings.put(Environment.PROXOOL_PROPERTIES, properties.getProperty(Environment.PROXOOL_PROPERTIES));

                settings.put(Environment.CONNECTION_PROVIDER, ProxoolConnectionProvider.class);

                serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(settings).build();

                Metadata metadata = new MetadataSources(serviceRegistry)
                        .addAnnotatedClass(Book.class)
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
