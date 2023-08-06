package com.cos.core.properties.cd;

public abstract class ConnectionDetails {
    protected String driver;
    protected String url;
    protected String username;
    protected String password;
    protected String dialect;
    protected boolean showSQL;
    protected String currentSessionContextClass;
    protected String hbm2ddlAuto;
    protected boolean autoCommit;
    protected Class<?> connectionPullProviderClass;

    public ConnectionDetails() {
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDialect() {
        return dialect;
    }

    public boolean getShowSQL() {
        return showSQL;
    }

    public String getCurrentSessionContextClass() {
        return currentSessionContextClass;
    }

    public String getHbm2ddlAuto() {
        return hbm2ddlAuto;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public Class<?> getConnectionPullProviderClass() {
        return connectionPullProviderClass;
    }

    public int getMinIdle() {
        return 0;
    }

    public int getInitialSize() {
        return 0;
    }

    public int getMixIdle() {
        return 0;
    }

    public int getMaxIdle() {
        return 0;
    }

    public int getMaxTotal() {
        return 0;
    }

}
