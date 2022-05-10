package com.cos.core.connect;

import java.sql.Connection;

public class ConnectionManager {

    private final Connection connection;

    public ConnectionManager(Connection connection) {
        this.connection = connection;
    }


}
