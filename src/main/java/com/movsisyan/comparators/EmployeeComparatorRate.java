package com.movsisyan.comparators;

import com.movsisyan.model.Employee;

import java.util.Comparator;
import java.util.Objects;

public class EmployeeComparatorRate implements Comparator<Employee> {

    @Override
    public int compare(Employee o1, Employee o2) {
        if (o1.getScore() != o2.getScore()) {
            return -Integer.compare(o1.getScore(), o2.getScore());
        } else if (!Objects.equals(o1.getSurname(), o2.getSurname())) {
            return o1.getSurname().compareTo(o2.getSurname());
        } return o1.getName().compareTo(o2.getName());
    }
}
