package com.cos.core.util;

import com.cos.core.config.cp.ConnectionPullC3P0Configuration;
import com.cos.core.constant.DataSourcePoolType;
import com.cos.core.properties.details.ConnectionDetails;
import com.cos.core.properties.details.DBCP2ConnectionDetails;
import com.cos.core.properties.details.ExternalCPConnectionDetails;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.c3p0.internal.C3P0ConnectionProvider;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;
import org.hibernate.vibur.internal.ViburDBCPConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vibur.dbcp.ViburDBCPDataSource;

import javax.sql.DataSource;

import java.beans.PropertyVetoException;

import static com.cos.core.constant.Constant.POSTGRES_DB_URL;
import static com.cos.core.constant.Constant.POSTGRES_DIALECT;
import static com.cos.core.constant.Constant.POSTGRES_DRIVER;
import static com.cos.core.constant.Constant.POSTGRES_PASSWORD;
import static com.cos.core.constant.Constant.POSTGRES_USERNAME;
import static com.cos.core.constant.DataSourcePoolType.C3PO_DATASOURCE;
import static com.cos.core.constant.DataSourcePoolType.DBCP2_DATASOURCE;
import static com.cos.core.constant.DataSourcePoolType.HIKARI_DATASOURCE;
import static com.cos.core.constant.DataSourcePoolType.VIBUR_DATASOURCE;

public final class DataSourcePool {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPullC3P0Configuration.class);

    public static DataSource getDataSource(DataSourcePoolType dsType) {
        if (HIKARI_DATASOURCE.equals(dsType)) {
            return getHikariDataSource();
        }
        if (C3PO_DATASOURCE.equals(dsType)) {
            return getComboPooledDataSource();
        }
        if (DBCP2_DATASOURCE.equals(dsType)) {
            return getBasicDataSource();
        }
        if (VIBUR_DATASOURCE.equals(dsType)) {
            return getViburDataSource();
        }

        LOG.warn("wasn't select datasource type");
        throw new RuntimeException();
    }

    public static ConnectionDetails getConnectionDetails(DataSourcePoolType dsType) {
        if (HIKARI_DATASOURCE.equals(dsType)) {
            return ExternalCPConnectionDetails.newBuilder()
                    .setDriver(POSTGRES_DRIVER)
                    .setUrl(POSTGRES_DB_URL)
                    .setUserName(POSTGRES_USERNAME)
                    .setPassword(POSTGRES_PASSWORD)
                    .setDialect(POSTGRES_DIALECT)
                    .setShowSQL(true)
                    .setCurrentSessionContextClass("thread")
                    .setHBM2ddlAuto("update")
                    .setAutoCommit(false)
                    .setConnectionPullProviderClass(HikariCPConnectionProvider.class)
                    .build();
        }
        if (C3PO_DATASOURCE.equals(dsType)) {
            return ExternalCPConnectionDetails.newBuilder()
                    .setDriver(POSTGRES_DRIVER)
                    .setUrl(POSTGRES_DB_URL)
                    .setUserName(POSTGRES_USERNAME)
                    .setPassword(POSTGRES_PASSWORD)
                    .setDialect(POSTGRES_DIALECT)
                    .setShowSQL(true)
                    .setCurrentSessionContextClass("thread")
                    .setHBM2ddlAuto("update")
                    .setAutoCommit(false)
                    .setConnectionPullProviderClass(C3P0ConnectionProvider.class)
                    .build();
        }
        if (DBCP2_DATASOURCE.equals(dsType)) {
            return DBCP2ConnectionDetails.newBuilder()
                    .setDriver(POSTGRES_DRIVER)
                    .setUrl(POSTGRES_DB_URL)
                    .setUserName(POSTGRES_USERNAME)
                    .setPassword(POSTGRES_PASSWORD)
                    .setDialect(POSTGRES_DIALECT)
                    .setShowSQL(true)
                    .setCurrentSessionContextClass("thread")
                    .setHBM2ddlAuto("update")
                    .setAutoCommit(false)
                    .setConnectionPullProviderClass(DatasourceConnectionProviderImpl.class)
                    .setInitialSize(0)
                    .setMinIdle(5)
                    .setMaxIdle(5)
                    .setMaxTotal(0)
                    .build();
        }
        if (VIBUR_DATASOURCE.equals(dsType)) {
            return ExternalCPConnectionDetails.newBuilder()
                    .setDriver(POSTGRES_DRIVER)
                    .setUrl(POSTGRES_DB_URL)
                    .setUserName(POSTGRES_USERNAME)
                    .setPassword(POSTGRES_PASSWORD)
                    .setDialect(POSTGRES_DIALECT)
                    .setShowSQL(true)
                    .setCurrentSessionContextClass("thread")
                    .setHBM2ddlAuto("update")
                    .setAutoCommit(false)
                    .setConnectionPullProviderClass(ViburDBCPConnectionProvider.class)
                    .build();
        }
        LOG.warn("wasn't select ConnectionDetails");
        throw new RuntimeException();
    }

    private static HikariDataSource getHikariDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(POSTGRES_DB_URL);
        dataSource.setUsername(POSTGRES_USERNAME);
        dataSource.setPassword(POSTGRES_PASSWORD);
        dataSource.setDriverClassName(POSTGRES_DRIVER);
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(5);
        return dataSource;
    }

    private static ComboPooledDataSource getComboPooledDataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(POSTGRES_DB_URL);
        dataSource.setUser(POSTGRES_USERNAME);
        dataSource.setPassword(POSTGRES_PASSWORD);
        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(20);
        dataSource.setAcquireIncrement(1);
        dataSource.setMaxIdleTime(1800);
        dataSource.setMaxStatements(150);
        try {
            dataSource.setDriverClass(POSTGRES_DRIVER);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }

    private static BasicDataSource getBasicDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(POSTGRES_DB_URL);
        dataSource.setUsername(POSTGRES_USERNAME);
        dataSource.setPassword(POSTGRES_PASSWORD);
        dataSource.setDriverClassName(POSTGRES_DRIVER);
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(20);
        dataSource.setMaxTotal(100);
        dataSource.setMaxWaitMillis(5000);
        return dataSource;
    }

    private static DataSource getViburDataSource() {
        ViburDBCPDataSource dataSource = new ViburDBCPDataSource();
        dataSource.setJdbcUrl(POSTGRES_DB_URL);
        dataSource.setUsername(POSTGRES_USERNAME);
        dataSource.setPassword(POSTGRES_PASSWORD);
        dataSource.setDriverClassName(POSTGRES_DRIVER);
        dataSource.setPoolInitialSize(5);
        dataSource.setPoolMaxSize(20);
        dataSource.setConnectionTimeoutInMs(5000);

        dataSource.start();
        return dataSource;
    }
}
