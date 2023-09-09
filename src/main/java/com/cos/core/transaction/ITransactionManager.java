package com.cos.core.transaction;

import java.util.List;

public interface ITransactionManager {
    <E> void useTransaction(E values);
    void useTransaction(List<?> values);
}
