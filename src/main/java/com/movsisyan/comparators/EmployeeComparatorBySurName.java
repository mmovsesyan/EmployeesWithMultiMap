package com.movsisyan.comparators;

import com.movsisyan.model.Employee;

import java.util.Comparator;

public class EmployeeComparatorBySurName implements Comparator<Employee> {

    @Override
    public int compare(Employee o1, Employee o2) {
        if (o1.getSurname().compareTo(o2.getSurname()) < 0) {
            return o1.getSurname().compareTo(o2.getSurname());
        }
        return o1.getName().compareTo(o2.getName());
    }
}
