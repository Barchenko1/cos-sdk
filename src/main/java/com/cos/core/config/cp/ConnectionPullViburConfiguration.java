package com.cos.core.config.cp;

import com.cos.core.util.CosCoreConstants;
import com.cos.core.util.setting.ViburSetting;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.vibur.internal.ViburDBCPConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ConnectionPullViburConfiguration  extends AbstractConnectionPullConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPullViburConfiguration.class);

    @Override
    public SessionFactory createSessionFactoryWithProperties() {
        if (sessionFactory == null) {
            try {
                Properties settings = new Properties();
                Properties properties = propertiesProvider.getProperties();
                setGeneralDBSettings(settings);

                settings.put(ViburSetting.HIBERNATE_VIBUR_POOL_INITIAL_SIZE,
                        properties.getOrDefault(ViburSetting.HIBERNATE_VIBUR_POOL_INITIAL_SIZE, "10"));
                settings.put(ViburSetting.HIBERNATE_VIBUR_POOL_MAX_SIZE,
                        properties.getOrDefault(ViburSetting.HIBERNATE_VIBUR_POOL_MAX_SIZE, "100"));
                settings.put(ViburSetting.HIBERNATE_VIBUR_CONNECTION_IDLE_LIMIT_IN_SECONDS,
                        properties.getOrDefault(ViburSetting.HIBERNATE_VIBUR_CONNECTION_IDLE_LIMIT_IN_SECONDS, "30"));
                settings.put(ViburSetting.HIBERNATE_VIBUR_TEST_CONNECTION_QUERY,
                        properties.getOrDefault(ViburSetting.HIBERNATE_VIBUR_TEST_CONNECTION_QUERY, "isValid"));
                settings.put(ViburSetting.HIBERNATE_VIBUR_LOG_QUERY_EXECUTION_LONGER_THAN_MS,
                        properties.getOrDefault(ViburSetting.HIBERNATE_VIBUR_LOG_QUERY_EXECUTION_LONGER_THAN_MS, "500"));
                settings.put(ViburSetting.HIBERNATE_VIBUR_LOG_STACK_TRACE_FOR_LONG_QUERY_EXECUTION,
                        properties.getOrDefault(ViburSetting.HIBERNATE_VIBUR_LOG_STACK_TRACE_FOR_LONG_QUERY_EXECUTION, "true"));

                settings.put(Environment.CONNECTION_PROVIDER,
                        properties.getOrDefault(Environment.CONNECTION_PROVIDER, ViburDBCPConnectionProvider.class));

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
        LOG.info("Virub createSessionFactoryWithHibernateXML");
        return super.createSessionFactoryWithHibernateXML(CosCoreConstants.VIBUR_HIBERNATE_XML_FILE_NAME);
    }

}
