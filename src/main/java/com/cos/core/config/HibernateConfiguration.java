package com.cos.core.config;

import com.cos.core.modal.Book;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.c3p0.internal.C3P0ConnectionProvider;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import org.hibernate.SessionFactory;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
public class HibernateConfiguration {

    private Properties properties;

    private SessionFactory sessionFactory;

    public HibernateConfiguration() {
    }

    public HibernateConfiguration(Properties properties) {
        this.properties = properties;
    }

    //    @Autowired
//    public void setEnvironment(Environment environment) {
//        this.environment = environment;
//    }

//    @Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource());
//        sessionFactory.setPackagesToScan("com.barchenko.project.entity");
//        sessionFactory.setHibernateProperties(hibernateProperties());
//
//        return sessionFactory;
//    }

//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource());
//        sessionFactory.setPackagesToScan("com.barchenko.project.entity");
//        sessionFactory.setHibernateProperties(hibernateProperties());
//
//        return sessionFactory;
//    }

    public SessionFactory buildSessionFactory() {
//        final BootstrapServiceRegistry bootstrapServiceRegistry = new BootstrapServiceRegistryBuilder().enableAutoClose()
//                .applyIntegrator(MetadataExtractorIntegrator.INSTANCE).build();
//
//        final StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder(bootstrapServiceRegistry).configure().build();
//        return new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();

        SessionFactory sessionFactory = null;
        properties = properties != null ? properties : defaultLoadProperty();

        properties.put(Environment.DRIVER, "org.h2.Driver");
        properties.put(Environment.URL, "jdbc:h2:~/gallerytest");
        properties.put(Environment.USER, "sa");
        properties.put(Environment.PASS, "");
        properties.put(Environment.HBM2DDL_AUTO, "create-drop");
        properties.put(Environment.SHOW_SQL, true);

        StandardServiceRegistry standardRegistry
                = new StandardServiceRegistryBuilder()
                .applySettings(hibernateProperties())
                .build();

        Metadata metadata = new MetadataSources(standardRegistry)
                .addAnnotatedClasses(Book.class)
                .getMetadataBuilder()
                .build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
        return sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.h2.Driver");
                settings.put(Environment.URL, "jdbc:h2:~/gallerytest");
                settings.put(Environment.USER, "sa");
                settings.put(Environment.PASS, "");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                // c3p0 configuration
                settings.put(Environment.C3P0_MIN_SIZE, 5); //Minimum size of pool
                settings.put(Environment.C3P0_MAX_SIZE, 20); //Maximum size of pool
                settings.put(Environment.C3P0_ACQUIRE_INCREMENT, 1); //Number of connections acquired at a time when pool is exhausted
                settings.put(Environment.C3P0_TIMEOUT, 1800); //Connection idle time
                settings.put(Environment.C3P0_MAX_STATEMENTS, 150); //PreparedStatement cache size
                settings.put(Environment.CONNECTION_PROVIDER, C3P0ConnectionProvider.class);
                settings.put(Environment.C3P0_CONFIG_PREFIX+".initialPoolSize", 5);

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(Book.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }


    private Properties hibernateProperties() {
        Set<String> set = properties.stringPropertyNames();
        Properties prop = new Properties();
        set.forEach(name -> {
            prop.put(name, properties.getProperty(name));
        });
//        properties.put("hibernate.dialect", properties.getProperty("hibernate.dialect"));
//        properties.put("hibernate.show_sql", properties.getProperty("hibernate.show_sql"));
//        properties.put("hibernate.hbm2ddl.auto", properties.getProperty("hibernate.hbm2ddl.auto"));
        return prop;
    }

    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        properties = properties != null ? properties : defaultLoadProperty();
        try {
            dataSource.setDriverClass(properties.getProperty("connection.driverclass"));
            dataSource.setJdbcUrl(properties.getProperty("connection.url"));
            dataSource.setUser(properties.getProperty("connection.username"));
            dataSource.setPassword(properties.getProperty("connection.password"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
//        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
//        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
//        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
//        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }

//    @Bean
//    public HibernateTransactionManager transactionManager() {
//        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(sessionFactory().getObject());
//        return transactionManager;
//    }

    private Properties defaultLoadProperty() {
        Properties appProps = new Properties();
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

        try {
            appProps.load(new FileInputStream(rootPath + "db.properties"));
        } catch (IOException ex) {
            throw new RuntimeException();
        }
        return appProps;
    }
}
