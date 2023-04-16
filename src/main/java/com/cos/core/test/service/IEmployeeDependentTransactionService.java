package com.cos.core.test.service;


import com.cos.core.test.modal.TestDependent;
import com.cos.core.test.modal.TestEmployee;

import java.util.List;

public interface IEmployeeDependentTransactionService {

    void setClasses(Class<?>[] classes);
    void saveTransactionalEntities(TestEmployee employee, List<TestDependent> dependent);

}
