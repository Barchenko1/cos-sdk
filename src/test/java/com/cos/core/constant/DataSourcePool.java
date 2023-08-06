package com.cos.core.constant;

import com.cos.core.properties.modal.AbstractConnectionDetails;
import com.cos.core.properties.modal.DBCP2ConnectionDetails;
import com.cos.core.properties.modal.ExternalCPConnectionDetails;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.c3p0.internal.C3P0ConnectionProvider;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;
import org.hibernate.vibur.internal.ViburDBCPConnectionProvider;
import org.vibur.dbcp.ViburDBCPDataSource;

import javax.sql.DataSource;

import java.beans.PropertyVetoException;

import static com.cos.core.constant.Constant.*;
import static com.cos.core.constant.DataSourcePoolType.*;

public class DataSourcePool {
    public static DataSource getDataSource(DataSourcePoolType dsType) {
        if (HIKARI_DATASOURCE.equals(dsType)) {
            getHikariDS();
        }
        if (C3PO_DATASOURCE.equals(dsType)) {
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
        if (DBCP2_DATASOURCE.equals(dsType)) {
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
        if (VIBUR_DATASOURCE.equals(dsType)) {
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
        return getHikariDS();
    }

    public static AbstractConnectionDetails getConnectionDetails(DataSourcePoolType dsType) {
        if (HIKARI_DATASOURCE.equals(dsType)) {
            return getHikariConnectionDetails();
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
        return getHikariConnectionDetails();
    }

    private static DataSource getHikariDS() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(POSTGRES_DB_URL);
        dataSource.setUsername(POSTGRES_USERNAME);
        dataSource.setPassword(POSTGRES_PASSWORD);
        dataSource.setDriverClassName(POSTGRES_DRIVER);
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(5);
        return dataSource;
    }

    private static AbstractConnectionDetails getHikariConnectionDetails() {
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
}
