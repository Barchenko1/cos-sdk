package com.cos.core.config;

import com.cos.core.modal.Book;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.c3p0.internal.C3P0ConnectionProvider;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import org.hibernate.SessionFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
public class ConfigurationManagerDB {

    private Properties properties;

    public ConfigurationManagerDB() {
    }

    public ConfigurationManagerDB(Properties properties) {
        this.properties = properties;
    }

    public SessionFactory createSessionFactoryWithOutXML() {
        SessionFactory sessionFactory;
        try {
            Configuration configuration = new Configuration();

//            Properties settings = new Properties();
//            settings.put(Environment.DRIVER, "org.h2.Driver");
//            settings.put(Environment.URL, "jdbc:h2:~/gallerytest");
//            settings.put(Environment.USER, "sa");
//            settings.put(Environment.PASS, "");
//            settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
//
//            settings.put(Environment.SHOW_SQL, "true");
//            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
//            settings.put(Environment.HBM2DDL_AUTO, "create-drop");
//
//            settings.put(Environment.C3P0_MIN_SIZE, 5); //Minimum size of pool
//            settings.put(Environment.C3P0_MAX_SIZE, 20); //Maximum size of pool
//            settings.put(Environment.C3P0_ACQUIRE_INCREMENT, 1); //Number of connections acquired at a time when pool is exhausted
//            settings.put(Environment.C3P0_TIMEOUT, 1800); //Connection idle time
//            settings.put(Environment.C3P0_MAX_STATEMENTS, 150); //PreparedStatement cache size
//            settings.put(Environment.CONNECTION_PROVIDER, C3P0ConnectionProvider.class);
//            settings.put(Environment.C3P0_CONFIG_PREFIX+".initialPoolSize", 5);

            Properties settings = hibernateProperties();
            configuration.setProperties(settings);
            //mark
            configuration.addAnnotatedClass(Book.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sessionFactory;
    }

    public SessionFactory createSessionFactoryWithXML() {
        SessionFactory sessionFactory = null;

        try {
            StandardServiceRegistry standardRegistry
                    = new StandardServiceRegistryBuilder().configure()
                    .build();

            Metadata metadata = new MetadataSources(standardRegistry)
                    .getMetadataBuilder()
                    .build();

            sessionFactory = metadata.getSessionFactoryBuilder().build();

        } catch (RuntimeException e) {
            throw new ExceptionInInitializerError(e);
        }
        return sessionFactory;
    }


    private Properties hibernateProperties() {
        Properties checkedProperties = loadProperties();
        Set<String> set = checkedProperties.stringPropertyNames();
        Properties prop = new Properties();
        set.forEach(name -> {
            prop.put(name, properties.getProperty(name));
        });
        return prop;
    }

//    public DataSource dataSource() {
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        properties = loadProperties();
//        try {
//            dataSource.setDriverClass(properties.getProperty("connection.driverclass"));
//            dataSource.setJdbcUrl(properties.getProperty("connection.url"));
//            dataSource.setUser(properties.getProperty("connection.username"));
//            dataSource.setPassword(properties.getProperty("connection.password"));
//        } catch (PropertyVetoException e) {
//            throw new RuntimeException(e);
//        }
//        return dataSource;
//    }

    private Properties loadProperties() {
        return properties != null ? properties : defaultLoadProperties();
    }

    private Properties defaultLoadProperties() {
        Properties appProps = new Properties();
        String rootPath = Thread.currentThread().getContextClassLoader()
                .getResource("").getPath();
        try {
            appProps.load(new FileInputStream(rootPath + "db.properties"));
        } catch (IOException ex) {
            throw new RuntimeException();
        }
        return appProps;
    }
}
