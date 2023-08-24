package com.cos.core.properties.details;

public class DBCP2ConnectionDetails extends ConnectionDetails {
    private int initialSize;
    private int minIdle;
    private int maxIdle;
    private int maxTotal;

    private DBCP2ConnectionDetails() {
    }

    public int getMinIdle() {
        return minIdle;
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
        return new DBCP2ConnectionDetails().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setDriver(String driver) {
            DBCP2ConnectionDetails.this.driver = driver;
            return this;
        }

        public Builder setUrl(String url) {
            DBCP2ConnectionDetails.this.url = url;
            return this;
        }

        public Builder setUserName(String username) {
            DBCP2ConnectionDetails.this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            DBCP2ConnectionDetails.this.password = password;
            return this;
        }

        public Builder setDialect(String dialect) {
            DBCP2ConnectionDetails.this.dialect = dialect;
            return this;
        }

        public Builder setShowSQL(boolean showSQL) {
            DBCP2ConnectionDetails.this.showSQL = showSQL;
            return this;
        }

        public Builder setCurrentSessionContextClass(String currentSessionContextClass) {
            DBCP2ConnectionDetails.this.currentSessionContextClass = currentSessionContextClass;
            return this;
        }

        public Builder setHBM2ddlAuto(String hbm2ddlAuto) {
            DBCP2ConnectionDetails.this.hbm2ddlAuto = hbm2ddlAuto;
            return this;
        }

        public Builder setAutoCommit(boolean autoCommit) {
            DBCP2ConnectionDetails.this.autoCommit = autoCommit;
            return this;
        }

        public Builder setInitialSize(int initialSize) {
            DBCP2ConnectionDetails.this.initialSize = initialSize;
            return this;
        }

        public Builder setMinIdle(int minIdle) {
            DBCP2ConnectionDetails.this.minIdle = minIdle;
            return this;
        }

        public Builder setMaxIdle(int maxIdle) {
            DBCP2ConnectionDetails.this.maxIdle = maxIdle;
            return this;
        }

        public Builder setMaxTotal(int maxTotal) {
            DBCP2ConnectionDetails.this.maxTotal = maxTotal;
            return this;
        }

        public Builder setConnectionPullProviderClass(Class<?> connectionPullProviderClass) {
            DBCP2ConnectionDetails.this.connectionPullProviderClass = connectionPullProviderClass;
            return this;
        }

        public DBCP2ConnectionDetails build() {
            return DBCP2ConnectionDetails.this;
        }

    }
}
