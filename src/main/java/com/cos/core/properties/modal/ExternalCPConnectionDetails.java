package com.cos.core.properties.modal;

public class ExternalCPConnectionDetails extends AbstractConnectionDetails{
    private Class<?> connectionPullProviderClass;

    private ExternalCPConnectionDetails() {
    }

    public Class<?> getConnectionPullProviderClass() {
        return connectionPullProviderClass;
    }

    public static Builder newBuilder() {
        return new ExternalCPConnectionDetails().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setDriver(String driver) {
            ExternalCPConnectionDetails.this.driver = driver;
            return this;
        }

        public Builder setUrl(String url) {
            ExternalCPConnectionDetails.this.url = url;
            return this;
        }

        public Builder setUserName(String username) {
            ExternalCPConnectionDetails.this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            ExternalCPConnectionDetails.this.password = password;
            return this;
        }

        public Builder setDialect(String dialect) {
            ExternalCPConnectionDetails.this.dialect = dialect;
            return this;
        }

        public Builder setShowSQL(String showSQL) {
            ExternalCPConnectionDetails.this.showSQL = showSQL;
            return this;
        }

        public Builder setCurrentSessionContextClass(String currentSessionContextClass) {
            ExternalCPConnectionDetails.this.currentSessionContextClass = currentSessionContextClass;
            return this;
        }

        public Builder setHBM2ddlAuto(String hbm2ddlAuto) {
            ExternalCPConnectionDetails.this.hbm2ddlAuto = hbm2ddlAuto;
            return this;
        }

        public Builder setConnectionPullProviderClass(Class<?> connectionPullProviderClass) {
            ExternalCPConnectionDetails.this.connectionPullProviderClass = connectionPullProviderClass;
            return this;
        }

        public ExternalCPConnectionDetails build() {
            return ExternalCPConnectionDetails.this;
        }

    }
}
