package com.cos.core.transaction;

import java.util.List;

public interface ITransactionManager {
    void useTransaction(List<?> values);
}
