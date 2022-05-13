package com.cos.core.connect;

import org.hibernate.SessionFactory;

public abstract class AbstractDaoConnector {

    private final IConnectionPullManager connectionPullManager;

    public AbstractDaoConnector() {
        this.connectionPullManager = new ConnectionPullManager();
    }

    protected SessionFactory getSessionFactory() {
        return connectionPullManager.getConfigureSessionFactory();
    }
}
