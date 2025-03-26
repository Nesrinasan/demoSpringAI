package com.nasan.demospringai.toolcalling;

import com.nasan.demospringai.toolcalling.entity.Employee;
import com.nasan.demospringai.toolcalling.entity.EmployeeRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class EmployeeTools {


    private final EmployeeRepository employeeRepository;

    public EmployeeTools(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    /*   *//**
     * resultConverter deffault is DefaultToolCallResultConverter
     * @param id
     * @return
     *//*
    @Tool(description = "Retrieve employee information")
    Employee getEmployeeInfo(Long id) {
        return employeeService.getEmployee(id);
    }*/

//"Retrieve employee information and if he/seh is avaiable retrn summary otherwise return null mesaage"
    //@Tool(returnDirect = true)

    @Tool
    Employee getEmployeeInfo(Long id) {
        Employee employee = employeeRepository.findById(id).get();
        System.out.printf("Employee %s is %s%n", employee.getName(), employee.isAvailable() ? "available" : "not available");
        return employee;
    }
}
