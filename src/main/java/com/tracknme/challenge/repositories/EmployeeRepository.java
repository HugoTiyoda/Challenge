package com.tracknme.challenge.repositories;

import com.tracknme.challenge.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    Page<Employee> findByCep(String cep, Pageable pageable);


}
