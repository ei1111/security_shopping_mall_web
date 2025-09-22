package com.web.fastcampous.employee.repository;

import com.web.fastcampous.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
