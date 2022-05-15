package com.cos.core.config;

import com.cos.core.modal.TestEntity;
import com.cos.core.util.CosCoreConstants;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.proxool.internal.ProxoolConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ConnectionPullProxoolConfiguration extends AbstractConnectionPullConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPullProxoolConfiguration.class);

    @Override
    public SessionFactory createSessionFactoryWithProperties() {
        if (sessionFactory == null) {
            try {
                Properties settings = new Properties();
                Properties properties = propertiesProvider.getProperties();
                setGeneralDBSettings(settings);

                settings.put(Environment.PROXOOL_POOL_ALIAS,
                        properties.getOrDefault(Environment.PROXOOL_POOL_ALIAS, "poolCode"));
                settings.put(Environment.PROXOOL_EXISTING_POOL,
                        properties.getOrDefault(Environment.PROXOOL_EXISTING_POOL, "10"));
                settings.put(Environment.PROXOOL_XML,
                        properties.getOrDefault(Environment.PROXOOL_XML, "proxoolCode.xml"));

                settings.put(Environment.CONNECTION_PROVIDER,
                        properties.getOrDefault(Environment.CONNECTION_PROVIDER, ProxoolConnectionProvider.class));

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
                throw new RuntimeException();
            }
        }
        return sessionFactory;
    }

    @Override
    public SessionFactory createSessionFactoryWithHibernateXML() {
        return createSessionFactoryWithHibernateXML(CosCoreConstants.PROXOOL_HIBERNATE_XML_FILE_NAME);
    }

}
