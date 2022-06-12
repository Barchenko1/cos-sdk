package com.cos.core.config;

import com.cos.core.util.CosCoreConstants;
import com.cos.core.util.cp.DBCP2Settings;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Properties;

public class ConnectionPullDBCP2Configuration extends AbstractConnectionPullConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPullDBCP2Configuration.class);

    @Override
    public SessionFactory createSessionFactoryWithProperties() {
        if (sessionFactory == null) {
            try {
                Properties settings = new Properties();
                Properties properties = propertiesProvider.getProperties();
                settings.put(Environment.DATASOURCE, getDataSource());
                settings.put(Environment.SHOW_SQL, properties.getProperty(Environment.SHOW_SQL));
                settings.put(Environment.HBM2DDL_AUTO, properties.getProperty(Environment.HBM2DDL_AUTO));

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
        LOG.info("C3P0 createSessionFactoryWithHibernateXML");
        return createSessionFactoryWithHibernateXML(CosCoreConstants.DBCP2_HIBERNATE_XML_FILE_NAME);
    }

    private DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        Properties properties = propertiesProvider.getProperties();
        dataSource.setDriverClassName(properties.getProperty(Environment.DRIVER));
        dataSource.setUrl(properties.getProperty(Environment.URL));
        dataSource.setUsername(properties.getProperty(Environment.USER));
        dataSource.setPassword(properties.getProperty(Environment.PASS));

        // Connection pooling properties
        dataSource.setInitialSize(Integer.parseInt(properties.getProperty(DBCP2Settings.HIBERNATE_DBCP_INITIAL_SIZE)));
        dataSource.setMaxIdle(Integer.parseInt(properties.getProperty(DBCP2Settings.HIBERNATE_DBCP_MAX_IDLE)));
        dataSource.setMaxTotal(Integer.parseInt(properties.getProperty(DBCP2Settings.HIBERNATE_DBCP_MAX_TOTAL)));
        dataSource.setMinIdle(Integer.parseInt(properties.getProperty(DBCP2Settings.HIBERNATE_DBCP_MIN_IDLE)));
        dataSource.setMaxWaitMillis(Integer.parseInt(properties.getProperty(DBCP2Settings.HIBERNATE_DBCP_MAX_WAIT_MS)));
        return dataSource;
    }
}
