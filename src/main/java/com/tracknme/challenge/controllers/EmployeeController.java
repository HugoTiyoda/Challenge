package com.tracknme.challenge.controllers;

import com.tracknme.challenge.clients.ViaCepClient;
import com.tracknme.challenge.domain.Employee;
import com.tracknme.challenge.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ViaCepClient viaCepClient;

    @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody Employee employee) throws Exception {
        return new ResponseEntity<>(employeeService.create(employee), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Employee>> findAll(Pageable pageable) {
        return new ResponseEntity<>(employeeService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findOne(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(employeeService.findByID(id), HttpStatus.OK);
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<Page<Employee>> findByCep(@PathVariable String cep, Pageable pageable) throws Exception {
        return new ResponseEntity<>(employeeService.findByCep(cep, pageable), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Employee employee) {
        if (employeeService.findByID(id) != null) {
            employee.setId(id);
            employeeService.update(employee);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patch(@RequestBody Map<String, Object> updates,
                                   @PathVariable("id") Long id) {
        employeeService.patch(updates,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
