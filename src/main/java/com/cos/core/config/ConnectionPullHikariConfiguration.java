package com.cos.core.config;

import com.cos.core.modal.Book;
import com.cos.core.util.cp.HikariSettings;
import com.zaxxer.hikari.pool.HikariProxyConnection;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class ConnectionPullHikariConfiguration extends AbstractConnectionPullConfiguration {
    private ServiceRegistry serviceRegistry;
    private SessionFactory sessionFactory;

    @Override
    public SessionFactory createSessionFactoryWithProperties() {
        if (sessionFactory == null) {
            try {
                Properties settings = new Properties();
                Properties properties = propertiesProvider.getProperties();
                setGeneralDBSettings(settings);

                // Maximum waiting time for a connection from the pool
                settings.put(HikariSettings.HIBERNATE_HIKARI_CONNECTION_TIMEOUT,
                        properties.getOrDefault(HikariSettings.HIBERNATE_HIKARI_CONNECTION_TIMEOUT, "20000"));
                // Minimum number of ideal connections in the pool
                settings.put(HikariSettings.HIBERNATE_HIKARI_MINIMUM_IDLE,
                        properties.getOrDefault(HikariSettings.HIBERNATE_HIKARI_MINIMUM_IDLE, "10"));
                // Maximum number of actual connection in the pool
                settings.put(HikariSettings.HIBERNATE_HIKARI_MAXIMUM_PULL_SIZE,
                        properties.getOrDefault(HikariSettings.HIBERNATE_HIKARI_MAXIMUM_PULL_SIZE, "20"));
                settings.put(Environment.CONNECTION_PROVIDER, HikariProxyConnection.class);

                // Maximum time that a connection is allowed to sit ideal in the pool
                settings.put(HikariSettings.HIBERNATE_HIKARI_IDLE_TIMEOUT,
                        properties.getOrDefault(HikariSettings.HIBERNATE_HIKARI_IDLE_TIMEOUT, "300000"));

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
