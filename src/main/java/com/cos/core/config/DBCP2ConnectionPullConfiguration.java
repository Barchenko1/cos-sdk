package com.cos.core.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;

import javax.sql.DataSource;
import java.util.Properties;

public class DBCP2ConnectionPullConfiguration implements IConnectionPullConfiguration{

    private final Properties properties;

    public DBCP2ConnectionPullConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Override
    public SessionFactory createDefaultSessionFactory() {
        return null;
    }

    @Override
    public SessionFactory createSessionFactoryWithProperties() {
        return null;
    }

    @Override
    public SessionFactory createSessionFactoryWithHibernateXML() {
        return null;
    }

    private DataSource getDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(properties.getProperty(Environment.DRIVER));
        ds.setUrl(properties.getProperty(Environment.URL));
        ds.setUsername(properties.getProperty(Environment.USER));
        ds.setPassword(properties.getProperty(Environment.PASS));


        return ds;
    }
}
