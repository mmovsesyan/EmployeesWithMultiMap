package com.movsisyan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Employee implements Comparable<Employee> {
    private  String surname;
    private String name;
    private int number;
    private int score;

    public Employee(String surname, String name, int number, int score) {
        this.surname = surname;
        this.name = name;
        this.number = number;
        this.score = score;
    }

    @Override
    public int compareTo(Employee o) {
        return Integer.compare(this.getScore(), o.getScore());
    }
}
