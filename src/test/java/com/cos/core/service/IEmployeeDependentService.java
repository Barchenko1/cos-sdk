package com.cos.core.service;


import com.cos.core.modal.TestDependent;
import com.cos.core.modal.TestEmployee;
import com.cos.core.test.modal.EmployeeDependentTestDto;

import java.util.List;

public interface IEmployeeDependentService {

    void saveTransactionalEntities(TestEmployee employee, List<TestDependent> dependent);
    void saveIncorrectTransactionalEntities(TestEmployee employee, List<TestDependent> dependent);
    List<EmployeeDependentTestDto> getEmployeeDependenTestDtoList();
}
