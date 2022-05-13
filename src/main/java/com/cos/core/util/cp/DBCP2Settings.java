package com.cos.core.util.cp;

public interface DBCP2Settings {
    String HIBERNATE_DBCP_INITIAL_SIZE = "hibernate.dbcp.initialSize";
    String HIBERNATE_DBCP_MAX_TOTAL = "hibernate.dbcp.maxTotal";
    String HIBERNATE_DBCP_MAX_IDLE = "hibernate.dbcp.maxIdle";
    String HIBERNATE_DBCP_MIN_IDLE = "hibernate.dbcp.minIdle";
    String HIBERNATE_DBCP_MAX_WAIT_MS = "hibernate.dbcp.maxWaitMillis";
}
