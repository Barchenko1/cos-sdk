package com.cos.core.config.cp;

import com.cos.core.util.CosCoreConstants;
import com.cos.core.util.setting.HikariSetting;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ConnectionPullConfiguration extends AbstractConnectionPullConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPullConfiguration.class);

    private final String configFileName;

    public ConnectionPullConfiguration(String configFileName) {
        this.configFileName = configFileName;
    }

    @Override
    public SessionFactory createSessionFactoryWithProperties() {
        if (sessionFactory == null) {
            try {
                Properties settings = new Properties();
                Properties properties = propertiesProvider.getProperties();
                setGeneralDBSettings(settings);

                // Maximum waiting time for a connection from the pool
                settings.put(HikariSetting.HIBERNATE_HIKARI_CONNECTION_TIMEOUT,
                        properties.getOrDefault(HikariSetting.HIBERNATE_HIKARI_CONNECTION_TIMEOUT, "20000"));
                // Minimum number of ideal connections in the pool
                settings.put(HikariSetting.HIBERNATE_HIKARI_MINIMUM_IDLE,
                        properties.getOrDefault(HikariSetting.HIBERNATE_HIKARI_MINIMUM_IDLE, "10"));
                // Maximum number of actual connection in the pool
                settings.put(HikariSetting.HIBERNATE_HIKARI_MAXIMUM_PULL_SIZE,
                        properties.getOrDefault(HikariSetting.HIBERNATE_HIKARI_MAXIMUM_PULL_SIZE, "20"));
                settings.put(Environment.CONNECTION_PROVIDER,
                        properties.getOrDefault(Environment.CONNECTION_PROVIDER, HikariCPConnectionProvider.class));

                // Maximum time that a connection is allowed to sit ideal in the pool
                settings.put(HikariSetting.HIBERNATE_HIKARI_IDLE_TIMEOUT,
                        properties.getOrDefault(HikariSetting.HIBERNATE_HIKARI_IDLE_TIMEOUT, "300000"));

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
                LOG.warn("properties error {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return sessionFactory;
    }

    @Override
    public SessionFactory createSessionFactoryWithHibernateXML() {
        LOG.info("Hikari createSessionFactoryWithHibernateXML {}", configFileName);
        return super.createSessionFactoryWithHibernateXMLByConfig(configFileName);
    }

}
