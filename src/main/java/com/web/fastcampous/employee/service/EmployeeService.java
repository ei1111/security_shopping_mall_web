package com.web.fastcampous.employee.service;

import com.web.fastcampous.employee.entity.Employee;
import com.web.fastcampous.employee.entity.Employee.EmployeeBuilder;
import com.web.fastcampous.employee.form.EmployeeRequest;
import com.web.fastcampous.employee.repository.EmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public List<Employee> listEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .departmentId(employeeRequest.getDepartmentId())
                .build();

        return employeeRepository.save(employee);
    }
}
