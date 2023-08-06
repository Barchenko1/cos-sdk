package com.cos.core.test.modal;

public class EmployeeDependentTestDto {
    private Long employee_id;
    private String employee_name;
    private Long dependent_id;
    private String dependent_name;
    private String dependent_status;

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public Long getDependent_id() {
        return dependent_id;
    }

    public void setDependent_id(Long dependent_id) {
        this.dependent_id = dependent_id;
    }

    public String getDependent_name() {
        return dependent_name;
    }

    public void setDependent_name(String dependent_name) {
        this.dependent_name = dependent_name;
    }

    public String getDependent_status() {
        return dependent_status;
    }

    public void setDependent_status(String dependent_status) {
        this.dependent_status = dependent_status;
    }
}
