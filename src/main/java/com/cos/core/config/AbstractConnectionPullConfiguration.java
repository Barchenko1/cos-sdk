package com.cos.core.config;

import com.cos.core.properties.IPropertiesProvider;
import com.cos.core.properties.modal.ConnectionDetails;
import com.cos.core.util.cp.HikariSettings;
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

    @Override
    public void setPropertiesProvider(IPropertiesProvider propertiesProvider) {
        this.propertiesProvider = propertiesProvider;
    }

    @Override
    public void setAnnotatedClasses(Class<?>[] annotatedClasses) {
        this.annotatedClasses = annotatedClasses;
    }

    protected SessionFactory createSessionFactoryWithHibernateXML(String xmlConfigFileName) {
        if (sessionFactory == null) {
            try {
                serviceRegistry = new StandardServiceRegistryBuilder()
                        .configure(xmlConfigFileName)
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
                        .applySettings(settings)
                        .build();

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

        settings.put(Environment.SHOW_SQL, connectionDetails.getShowSQL());
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, connectionDetails.getCurrentSessionContextClass());
        settings.put(Environment.HBM2DDL_AUTO, connectionDetails.getHbm2ddlAuto());

        // Maximum waiting time for a connection from the pool
        settings.put(HikariSettings.HIBERNATE_HIKARI_CONNECTION_TIMEOUT, "20000");
        // Minimum number of ideal connections in the pool
        settings.put(HikariSettings.HIBERNATE_HIKARI_MINIMUM_IDLE, "10");
        // Maximum number of actual connection in the pool
        settings.put(HikariSettings.HIBERNATE_HIKARI_MAXIMUM_PULL_SIZE, "20");

        // Maximum time that a connection is allowed to sit ideal in the pool
        settings.put(HikariSettings.HIBERNATE_HIKARI_IDLE_TIMEOUT, "300000");
        //change def conn provide class
        settings.put(Environment.CONNECTION_PROVIDER, connectionDetails.getConnectionPullProviderClass());
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
        settings.put(Environment.HBM2DDL_AUTO, properties.getProperty(Environment.HBM2DDL_AUTO));
        settings.put(Environment.AUTOCOMMIT, properties.getProperty(Environment.AUTOCOMMIT));
    }
}
