package com.cos.core.constant;

public interface Constant {
    String H2_DRIVER = "org.h2.Driver";
    String H2_DB_URL = "jdbc:h2:mem:test";
    String H2_USERNAME = "sa";
    String H2_PASSWORD = "sa";
    String H2_DIALECT = "org.hibernate.dialect.H2Dialect";

    String POSTGRES_DRIVER = "org.postgresql.Driver";
    String POSTGRES_DB_URL = "jdbc:postgresql://172.17.0.2:5432/test_db";
    String POSTGRES_USERNAME = "sa";
    String POSTGRES_PASSWORD = "sa";
    String POSTGRES_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";

}
