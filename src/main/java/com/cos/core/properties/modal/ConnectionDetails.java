package com.cos.core.properties.modal;

public class ConnectionDetails {
    private String driver;
    private String url;
    private String username;
    private String password;
    private String dialect;
    private int initialSize;
    private int minIdle;
    private int maxIdle;
    private int maxTotal;
    private Class<?> connectionPullProviderClass;

    private ConnectionDetails() {
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

    public Class<?> getConnectionPullProviderClass() {
        return connectionPullProviderClass;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public int getMixIdle() {
        return minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public static Builder newBuilder() {
        return new ConnectionDetails().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setDriver(String driver) {
            ConnectionDetails.this.driver = driver;
            return this;
        }

        public Builder setUrl(String url) {
            ConnectionDetails.this.url = url;
            return this;
        }

        public Builder setUserName(String username) {
            ConnectionDetails.this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            ConnectionDetails.this.password = password;
            return this;
        }

        public Builder setDialect(String dialect) {
            ConnectionDetails.this.dialect = dialect;
            return this;
        }

        public Builder setInitialSize(int initialSize) {
            ConnectionDetails.this.initialSize = initialSize;
            return this;
        }

        public Builder setMinIdle(int minIdle) {
            ConnectionDetails.this.minIdle = minIdle;
            return this;
        }

        public Builder setMaxIdle(int maxIdle) {
            ConnectionDetails.this.maxIdle = maxIdle;
            return this;
        }

        public Builder setMaxTotal(int maxTotal) {
            ConnectionDetails.this.maxTotal = maxTotal;
            return this;
        }

        public Builder setConnectionPullProviderClass(Class<?> connectionPullProviderClass) {
            ConnectionDetails.this.connectionPullProviderClass = connectionPullProviderClass;
            return this;
        }

        public ConnectionDetails build() {
            return ConnectionDetails.this;
        }

    }
}
