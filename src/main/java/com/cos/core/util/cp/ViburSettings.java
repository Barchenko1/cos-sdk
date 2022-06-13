package com.cos.core.util.cp;

public interface ViburSettings {
    // HikariCP settings

    String HIBERNATE_VIBUR_POOL_INITIAL_SIZE = "hibernate.vibur.poolInitialSize";
    String HIBERNATE_VIBUR_POOL_MAX_SIZE = "hibernate.vibur.poolMaxSize";
    String HIBERNATE_VIBUR_CONNECTION_IDLE_LIMIT_IN_SECONDS = "hibernate.vibur.connectionIdleLimitInSeconds";
    String HIBERNATE_VIBUR_TEST_CONNECTION_QUERY = "hibernate.vibur.testConnectionQuery";
    String HIBERNATE_VIBUR_LOG_QUERY_EXECUTION_LONGER_THAN_MS = "hibernate.vibur.logQueryExecutionLongerThanMs";
    String HIBERNATE_VIBUR_LOG_STACK_TRACE_FOR_LONG_QUERY_EXECUTION = "hibernate.vibur.logStackTraceForLongQueryExecution";


}
