package com.tracknme.challenge.services;

import com.tracknme.challenge.clients.ViaCepClient;
import com.tracknme.challenge.domain.Employee;
import com.tracknme.challenge.domain.ViaCepDTO;
import com.tracknme.challenge.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.Map;

@AllArgsConstructor
@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    @Autowired
    private ViaCepClient viaCepClient;

    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Page<Employee> findByCep(String cep, Pageable pageable) {
        return employeeRepository.findByCep(cep, pageable);
    }

    public Employee findByID(Long id) {
        try {
            return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Funcionario nao encontrado", e.getCause()
            );
        }
    }

    public Employee create(Employee employee) {
        try {
            fillAddress(employee);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "CEP invalido"
            );
        }
        return employeeRepository.save(employee);
    }


    public Employee update(Employee employee) {

        return employeeRepository.save(employee);
    }

    public void delete(Long id) {
        boolean exists = employeeRepository.existsById(id);
        if (exists != false) {
            employeeRepository.deleteById(id);
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Funcionario nao encontrado");

    }

    public void patch(Map<String, Object> updates, Long id) {
        Employee employee = findByID(id);
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Employee.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, employee, value);
        });
        if (updates.containsKey("cep")) {
            fillAddress(employee);
        }
        update(employee);
    }

    private void fillAddress(@NotNull Employee employee) {
        ViaCepDTO cep = viaCepClient.findCep(employee.getCep());
        employee.setBairro(cep.getBairro());
        employee.setUf(cep.getUf());
        employee.setComplemento(cep.getComplemento());
        employee.setLogradouro(cep.getLogradouro());
        employee.setLocalidade(cep.getLocalidade());
    }


}
