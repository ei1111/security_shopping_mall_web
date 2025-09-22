package com.web.fastcampous.deparment.repository;

import com.web.fastcampous.deparment.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
