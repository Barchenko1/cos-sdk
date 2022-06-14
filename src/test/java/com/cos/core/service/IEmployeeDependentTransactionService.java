package com.cos.core.service;


import com.cos.core.modal.TestDependent;
import com.cos.core.modal.TestEmployee;

import java.util.List;

public interface IEmployeeDependentTransactionService {

    void setClasses(Class<?>[] classes);
    void saveTransactionalEntities(TestEmployee employee, List<TestDependent> dependent);

}
