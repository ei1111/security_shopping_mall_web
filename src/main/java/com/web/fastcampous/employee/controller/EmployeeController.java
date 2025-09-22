package com.web.fastcampous.employee.controller;

import com.web.fastcampous.employee.entity.Employee;
import com.web.fastcampous.employee.form.EmployeeRequest;
import com.web.fastcampous.employee.service.EmployeeService;
import java.net.http.HttpResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> listEmployees() {
        return ResponseEntity.ok().body(employeeService.listEmployees());
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> create(EmployeeRequest employeeRequest) {
        Employee employee = employeeService.createEmployee(employeeRequest);
        return ResponseEntity.ok().body(employee);
    }
}
