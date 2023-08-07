package com.cos.core.test.modal;

public class EmployeeDependentTestDto {
    private Long employeeId;
    private String employeeName;
    private Long dependentId;
    private String dependentName;
    private String dependentStatus;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Long getDependentId() {
        return dependentId;
    }

    public void setDependentId(Long dependentId) {
        this.dependentId = dependentId;
    }

    public String getDependentName() {
        return dependentName;
    }

    public void setDependentName(String dependentName) {
        this.dependentName = dependentName;
    }

    public String getDependentStatus() {
        return dependentStatus;
    }

    public void setDependentStatus(String dependentStatus) {
        this.dependentStatus = dependentStatus;
    }
}
