package com.cos.core.connect;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDaoConnector {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractDaoConnector.class);
    private final IConnectionPullManager connectionPullManager;

    public AbstractDaoConnector() {
        this.connectionPullManager = new ConnectionPullManager();
    }

    protected SessionFactory getSessionFactory() {
        LOG.info("getSessionFactory");
        return connectionPullManager.getConfigureSessionFactory();
    }
}
