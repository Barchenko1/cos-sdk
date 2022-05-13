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
    protected Class<?>[] annotatedClasses;
    protected ConnectionDetails connectionDetails;

    @Override
    public void setPropertiesProvider(IPropertiesProvider propertiesProvider) {
        this.propertiesProvider = propertiesProvider;
    }

    @Override
    public void setAnnotatedClasses(Class<?>[] annotatedClasses) {
        this.annotatedClasses = annotatedClasses;
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
                Properties settings = getDefaultConnectionPullSettings(connectionDetails);

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

    private Properties getDefaultConnectionPullSettings(ConnectionDetails connectionDetails) {
        Properties settings = new Properties();

        settings.put(Environment.DRIVER, connectionDetails.getDriver());
        settings.put(Environment.URL, connectionDetails.getUrl());
        settings.put(Environment.USER, connectionDetails.getUsername());
        settings.put(Environment.PASS, connectionDetails.getPassword());
        settings.put(Environment.DIALECT, connectionDetails.getDialect());

        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "create-drop");

        settings.put(Environment.C3P0_MIN_SIZE, 5); //Minimum size of pool
        settings.put(Environment.C3P0_MAX_SIZE, 20); //Maximum size of pool
        settings.put(Environment.C3P0_ACQUIRE_INCREMENT, 1); //Number of connections acquired at a time when pool is exhausted
        settings.put(Environment.C3P0_TIMEOUT, 1800); //Connection idle time
        settings.put(Environment.C3P0_MAX_STATEMENTS, 150); //PreparedStatement cache size
        settings.put(Environment.CONNECTION_PROVIDER, connectionDetails.getConnectionPullProviderClass());
        settings.put(Environment.C3P0_CONFIG_PREFIX+".initialPoolSize", 5);
        return settings;
    }

    protected void setGeneralDBSettings(Properties settings) {
        Properties properties = propertiesProvider.getProperties();
        settings.put(Environment.DRIVER, properties.getProperty(Environment.DRIVER));
        settings.put(Environment.URL, properties.getProperty(Environment.URL));
        settings.put(Environment.USER, properties.getProperty(Environment.USER));
        settings.put(Environment.PASS, properties.getProperty(Environment.PASS));
        settings.put(Environment.DIALECT, properties.getProperty(Environment.DIALECT));

        settings.put(Environment.SHOW_SQL, properties.getProperty(Environment.SHOW_SQL));
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,
                properties.getProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS));
        settings.put(Environment.HBM2DDL_AUTO, properties.getProperty(Environment.HBM2DDL_AUTO));
    }
}
