package com.cos.core.util;

public interface CosCoreConstants {

    String DB_PROPERTIES_FILE_NAME = "db.properties";

    String HIKARI_PROPERTIES_FILE_NAME = "hikari.db.properties";
    String C3P0_PROPERTIES_FILE_NAME = "c3p0.db.properties";
    String DBCP2_PROPERTIES_FILE_NAME = "dbcp2.db.properties";
    String VIBUR_PROPERTIES_FILE_NAME = "vibur.db.properties";

    String C3P0_HIBERNATE_XML_FILE_NAME = "c3p0.hibernate.cfg.xml";
    String DBCP2_HIBERNATE_XML_FILE_NAME = "dbcp2.hibernate.cfg.xml";
    String HIKARI_HIBERNATE_XML_FILE_NAME = "hikari.hibernate.cfg.xml";
    String VIBUR_HIBERNATE_XML_FILE_NAME = "vibur.hibernate.cfg.xml";

    String C3P0_CONNECTION_PROVIDER = "org.hibernate.c3p0.internal.C3P0ConnectionProvider";
    String HIKARI_CONNECTION_PROVIDER = "com.zaxxer.hikari.hibernate.HikariConnectionProvider";
    String VIBUR_CONNECTION_PROVIDER = "org.hibernate.vibur.internal.ViburDBCPConnectionProvider";
    String DBCP2_CONNECTION_PROVIDER = "";
}
