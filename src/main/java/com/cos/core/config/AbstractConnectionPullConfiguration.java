package com.cos.core.config;

import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.modal.ConnectionDetails;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public abstract class AbstractConnectionPullConfiguration implements IConnectionPullConfiguration {

    protected ServiceRegistry serviceRegistry;
    protected SessionFactory sessionFactory;
    protected IPropertiesProvider propertiesProvider;

    @Override
    public void setPropertiesProvider(IPropertiesProvider propertiesProvider) {
        this.propertiesProvider = propertiesProvider;
    }

    @Override
    public SessionFactory createSessionFactoryWithHibernateXML() {
        if (sessionFactory == null) {
            try {
                serviceRegistry = new StandardServiceRegistryBuilder()
                        .configure()
                        .build();

                Metadata metadata = new MetadataSources(serviceRegistry)
                        .getMetadataBuilder()
                        .build();

                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (RuntimeException e) {
                if (serviceRegistry != null) {
                    StandardServiceRegistryBuilder.destroy(serviceRegistry);
                }
                throw new RuntimeException();
            }
        }
        return sessionFactory;
    }

    @Override
    public SessionFactory createDefaultSessionFactory(ConnectionDetails connectionDetails, Class<?>[] annotatedClasses) {
        if (sessionFactory == null) {
            try {
                Properties settings = propertiesProvider.getDefaultConnectionPoolProperties(connectionDetails);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
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

    protected void setGeneralDBSettings(Properties settings) {
        Properties properties = propertiesProvider.getProperties();
        settings.put(Environment.DRIVER, properties.getProperty(Environment.DRIVER));
        settings.put(Environment.URL, properties.getProperty(Environment.URL));
        settings.put(Environment.USER, properties.getProperty(Environment.USER));
        settings.put(Environment.PASS, properties.getProperty(Environment.PASS));
        settings.put(Environment.DIALECT, properties.getProperty(Environment.DIALECT));
    }
}
