package com.nasan.demospringai.toolcalling;

import com.nasan.demospringai.toolcalling.entity.Employee;
import com.nasan.demospringai.toolcalling.entity.EmployeeRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeTools {


    private final EmployeeRepository employeeRepository;

    public EmployeeTools(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    /*   *//**
     * resultConverter deffault is DefaultToolCallResultConverter
     * @return
     *//*
    @Tool(description = "Retrieve employee information")
    Employee getEmployeeInfo(Long id) {
        return employeeService.getEmployee(id);
    }*/

//"Retrieve employee information and if he/seh is avaiable retrn summary otherwise return null mesaage"
    //@Tool(returnDirect = true)

    @Tool
    List<Employee> getAllEmplooye() {
        return employeeRepository.findAll();
    }
}
