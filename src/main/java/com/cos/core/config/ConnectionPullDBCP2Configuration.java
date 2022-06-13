package com.cos.core.config;

import com.cos.core.properties.modal.AbstractConnectionDetails;
import com.cos.core.util.CosCoreConstants;
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
        LOG.info("DBCP2 createSessionFactoryWithHibernateXML");
        return createSessionFactoryWithHibernateXML(CosCoreConstants.DBCP2_HIBERNATE_XML_FILE_NAME);
    }

    @Override
    public SessionFactory createClassDetailsSessionFactory(AbstractConnectionDetails connectionDetails, Class<?>[] annotatedClasses) {
        if (sessionFactory == null) {
            try {
                Properties settings = new Properties();
                settings.put(Environment.DATASOURCE, getClassDBCPConnectionPullSettings(connectionDetails));

                settings.put(Environment.SHOW_SQL, connectionDetails.getShowSQL());
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,
                        connectionDetails.getCurrentSessionContextClass());
                settings.put(Environment.HBM2DDL_AUTO, connectionDetails.getHbm2ddlAuto());

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

    private DataSource getClassDBCPConnectionPullSettings(AbstractConnectionDetails connectionDetails) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(connectionDetails.getDriver());
        dataSource.setUrl(connectionDetails.getUrl());
        dataSource.setUsername(connectionDetails.getUsername());
        dataSource.setPassword(connectionDetails.getPassword());

        return dataSource;
    }

    private DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        Properties properties = propertiesProvider.getProperties();
        dataSource.setDriverClassName(properties.getProperty(Environment.DRIVER));
        dataSource.setUrl(properties.getProperty(Environment.URL));
        dataSource.setUsername(properties.getProperty(Environment.USER));
        dataSource.setPassword(properties.getProperty(Environment.PASS));
        return dataSource;
    }
}
