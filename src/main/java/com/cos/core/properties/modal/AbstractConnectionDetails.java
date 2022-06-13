package com.cos.core.properties.modal;

public abstract class AbstractConnectionDetails {
    protected String driver;
    protected String url;
    protected String username;
    protected String password;
    protected String dialect;
    protected String showSQL;
    protected String currentSessionContextClass;
    protected String hbm2ddlAuto;

    public AbstractConnectionDetails() {
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

    public String getShowSQL() {
        return showSQL;
    }

    public String getCurrentSessionContextClass() {
        return currentSessionContextClass;
    }

    public String getHbm2ddlAuto() {
        return hbm2ddlAuto;
    }

    public int getMinIdle() {
        return 0;
    }

    public Class<?> getConnectionPullProviderClass() {
        return null;
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
