package com.web.fastcampous.deparment.service;

import com.web.fastcampous.deparment.entity.Department;
import com.web.fastcampous.deparment.repository.DepartmentRepository;
import com.web.fastcampous.employee.entity.Employee;
import com.web.fastcampous.employee.repository.EmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> listDepartments() {
        return departmentRepository.findAll();
    }
}
