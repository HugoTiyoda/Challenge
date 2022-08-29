package com.tracknme.challenge.services;

import com.tracknme.challenge.clients.ViaCepClient;
import com.tracknme.challenge.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {


    @Mock
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @Mock
    private ViaCepClient viaCepClient;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeService(employeeRepository, viaCepClient);

    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canFindAll() {
        Pageable pageable = null;
        employeeService.findAll(pageable);
        verify(employeeRepository).findAll(pageable);
    }

    @Test
    void canFindByCep() {
        Pageable pageable = null;
        String cep = "01536000";
        employeeService.findByCep(cep, pageable);
        verify(employeeRepository).findByCep(cep, pageable);
    }


//    void shouldCreateEmployee() {
//        Employee employee = new Employee(
//                1, "name", 18, "","01536000",
//                "", "", "", "", ""
//        );
//        employeeService.create(employee);
//        verify(employeeRepository).save(employee);
//    }

}