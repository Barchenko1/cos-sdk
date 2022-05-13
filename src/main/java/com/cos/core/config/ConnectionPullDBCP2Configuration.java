package com.cos.core.config;

import com.cos.core.modal.Book;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConnectionPullDBCP2Configuration extends AbstractConnectionPullConfiguration{

    private ServiceRegistry serviceRegistry;
    private SessionFactory sessionFactory;

    @Override
    public SessionFactory createSessionFactoryWithProperties(Class<?>[] annotatedClasses) {
        if (sessionFactory == null) {
            try {

                Map<String, Object> settings = new HashMap<>();
                settings.put(Environment.DATASOURCE, getDataSource());
                settings.put(Environment.HBM2DDL_AUTO, "update");
                settings.put(Environment.SHOW_SQL, "true");

                serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(settings)
                        .build();

                MetadataSources sources = new MetadataSources(serviceRegistry)
                        .addAnnotatedClass(Book.class);

                Metadata metadata = sources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                if (serviceRegistry != null) {
                    StandardServiceRegistryBuilder.destroy(serviceRegistry);
                }
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    private DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        Properties properties = propertiesProvider.getProperties();
        dataSource.setDriverClassName(properties.getProperty(Environment.DRIVER));
        dataSource.setUrl(properties.getProperty(Environment.URL));
        dataSource.setUsername(properties.getProperty(Environment.USER));
        dataSource.setPassword(properties.getProperty(Environment.PASS));

        // Connection pooling properties
        dataSource.setInitialSize(0);
        dataSource.setMaxIdle(5);
        dataSource.setMaxTotal(5);
        dataSource.setMinIdle(0);
        return dataSource;
    }
}
