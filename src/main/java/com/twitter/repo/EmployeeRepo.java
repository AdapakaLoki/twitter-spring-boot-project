package com.twitter.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twitter.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long>{

}
