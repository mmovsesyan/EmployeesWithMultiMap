package com.movsisyan.program;

import com.movsisyan.repository.EmployeeRepository;

import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        try {
            EmployeeRepository repository = new EmployeeRepository("employees.csv");
//            System.out.println(repository.getCoolestEmployees());
//            System.out.println(repository.getMaxScores());
//            System.out.println(repository.getCountCoolestEmployees());
//            System.out.println(repository.getAvarageScores());
//            System.out.println(repository.getCoolestEmployeesAll());
//            System.out.println(repository.getSubCoolestEmployees());
//            System.out.println(repository.getMaxScoresSubCoolestEmployees());
//            System.out.println(repository.getMaxScoreAll());
//            System.out.println(repository.getMaxCountDepartments());
//            System.out.println(repository.getMinCountDepartments());
//            System.out.println(repository.sortBySurName());
//            System.out.println(repository.sortByRatingDec());
//            System.out.println(repository.departmentCoolestEmployees());
//            System.out.println(repository.greatAverageScoreDepartments());
//            System.out.println(repository.maxAverageScoreDepartments());
//            System.out.println(repository.sortByCountEmployees());
            System.out.println(repository.maxCountCoolestEmployeeDepartments());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
