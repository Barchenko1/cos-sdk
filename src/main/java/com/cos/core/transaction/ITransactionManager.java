package com.cos.core.transaction;

public interface ITransactionManager {
    <E> void useTransaction(E values);
}
