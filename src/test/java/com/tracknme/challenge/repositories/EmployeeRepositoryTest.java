package com.tracknme.challenge.repositories;

import com.tracknme.challenge.domain.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    Pageable pageable = null;

    @Test
    void shouldFindByCep() {
        Employee employee = new Employee(
                1, "name", 18, "","01536000",
                "", "", "", "", ""
        );
        employeeRepository.save(employee);

        Page<Employee> content = employeeRepository.findByCep(employee.getCep(), pageable);
        assertThat(content.getContent().get(0)).isNotNull();
    }

    @Test
    void shouldNotFindByCep() {
        Page<Employee> content = employeeRepository.findByCep("01536000", pageable);
        assertThat(content.getContent().isEmpty());
    }
}