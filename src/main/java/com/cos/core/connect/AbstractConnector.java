package com.cos.core.connect;

import java.sql.Connection;

public abstract class AbstractConnector {

    ConnectionManager connectionManager;

    protected Connection getConnector() {
        return null;
    }
}
