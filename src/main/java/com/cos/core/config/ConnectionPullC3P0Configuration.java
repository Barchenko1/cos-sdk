package com.cos.core.config;

import com.cos.core.util.CosCoreConstants;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.c3p0.internal.C3P0ConnectionProvider;
import org.hibernate.cfg.Environment;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ConnectionPullC3P0Configuration extends AbstractConnectionPullConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPullC3P0Configuration.class);

    @Override
    public SessionFactory createSessionFactoryWithProperties() {
        LOG.info("C3P0 createSessionFactoryWithProperties");
        if (sessionFactory == null) {
            try {
                Properties settings = new Properties();
                Properties properties = propertiesProvider.getProperties();
                setGeneralDBSettings(settings);

                settings.put(Environment.C3P0_MIN_SIZE, properties.getOrDefault(Environment.C3P0_MIN_SIZE, 5)); //Minimum size of pool
                settings.put(Environment.C3P0_MAX_SIZE, properties.getOrDefault(Environment.C3P0_MAX_SIZE, 20)); //Maximum size of pool
                settings.put(Environment.C3P0_ACQUIRE_INCREMENT, properties.getOrDefault(Environment.C3P0_ACQUIRE_INCREMENT, 1)); //Number of connections acquired at a time when pool is exhausted
                settings.put(Environment.C3P0_TIMEOUT, properties.getOrDefault(Environment.C3P0_TIMEOUT, 1800)); //Connection idle time
                settings.put(Environment.C3P0_MAX_STATEMENTS, properties.getOrDefault(Environment.C3P0_MAX_STATEMENTS, 150)); //PreparedStatement cache size
                settings.put(Environment.CONNECTION_PROVIDER, properties.getOrDefault(Environment.CONNECTION_PROVIDER, C3P0ConnectionProvider.class));
                settings.put(Environment.C3P0_CONFIG_PREFIX+".initialPoolSize",
                        properties.getOrDefault(Environment.C3P0_CONFIG_PREFIX+".initialPoolSize", 5));

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
                LOG.warn("properties error {}", e.getMessage());
                throw new RuntimeException();
            }
        }
        return sessionFactory;
    }

    @Override
    public SessionFactory createSessionFactoryWithHibernateXML() {
        LOG.info("DBCP2 createSessionFactoryWithHibernateXML");
        return createSessionFactoryWithHibernateXML(CosCoreConstants.C3P0_HIBERNATE_XML_FILE_NAME);
    }

}
