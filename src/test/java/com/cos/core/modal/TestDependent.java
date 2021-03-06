package com.cos.core.modal;

import com.cos.core.test.modal.TestEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@Table(name = "TestDependent")
@NamedNativeQueries({
        @NamedNativeQuery(name = "getTestDependentAll",
                query = "select * from testDependent",
                resultClass = com.cos.core.test.modal.TestEntity.class),
        @NamedNativeQuery(name = "getTestDependentByName",
                query = "select * from testDependent d where d.name=?",
                resultClass = TestEntity.class),
})
public class TestDependent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;
    @Column
    private String name;
    @Column
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    private TestEmployee testEmployee;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TestEmployee getTestEmployee() {
        return testEmployee;
    }

    public void setTestEmployee(TestEmployee testEmployee) {
        this.testEmployee = testEmployee;
    }
}
