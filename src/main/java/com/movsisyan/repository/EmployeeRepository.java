package com.movsisyan.repository;

import com.movsisyan.comparators.EmployeeComparatorBySurName;
import com.movsisyan.comparators.EmployeeComparatorRate;
import com.movsisyan.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EmployeeRepository {
    private final Map<Integer, ArrayList<Employee>> employees = new LinkedHashMap<>();

    public EmployeeRepository(String name) throws IOException {
        load(name);
    }

    /**
     * 1.	load
     * Произвести сохранение объектов из .csv файла в соответствующую коллекцию данных,
     * удовлетворяющую решениям нижестоящих задач. На основании загруженной коллекции
     * производить вычисления
     */
    private void load(String name) throws IOException {
        try (BufferedReader bf = new BufferedReader(new FileReader(name))) {
            while (bf.ready()) {
                String s = bf.readLine();
                String[] split = s.split(";");
                int f = Integer.parseInt(split[0]);
                Employee emp = new Employee(split[1], split[2], Integer.parseInt(split[3]),
                        Integer.parseInt(split[4]));

                ArrayList<Employee> list = employees.getOrDefault(f, new ArrayList<>());
                list.add(emp);
                employees.put(f, list);
            }
        }
    }

    /**
     * 2.getCoolestEmployees
     * Определить самых успешных сотрудников по каждому отделу
     */
    public Map<Integer, ArrayList<Employee>> getCoolestEmployees() {
        double f = Double.MIN_VALUE;
        for (ArrayList<Employee> value : this.employees.values()) {
            for (Employee employee : value) {
                if (employee.getScore() > f) {
                    f = employee.getScore();
                }
            }
        }
        Map<Integer, ArrayList<Employee>> employees = new HashMap<>();
        for (var entry : this.employees.entrySet()) {
            for (Employee employee : entry.getValue()) {
                if (employee.getScore() == f) {
                    ArrayList<Employee> employeeArrayList = employees.getOrDefault(entry.getKey(), new ArrayList<>());
                    employeeArrayList.add(employee);
                    employees.put(entry.getKey(), employeeArrayList);
                }
            }
        }
        return employees;
    }

    /**
     * 3.getMaxScores
     * Вернуть в виде коллекции рейтинг самых успешных сотрудников в каждом отделе
     */
    public Map<Integer, Integer> getMaxScores() {
        Map<Integer, ArrayList<Employee>> coolestEmployees = getCoolestEmployees();
        Map<Integer, Integer> ratings = new HashMap<>();
        for (var entry : coolestEmployees.entrySet()) {
            ratings.put(entry.getKey(), entry.getValue().get(0).getScore());
        }
        return ratings;
    }

    /**
     * 4.getAverageScores
     * Вернуть в виде коллекции средний рейтинг сотрудников по каждому отделу
     */
    //?
    public Map<Integer, Double> getAvarageScores() {
        Map<Integer, Double> ratings = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<Employee>> entry : employees.entrySet()) {
            int sum = 0;
            for (Employee employee : entry.getValue()) {
                sum += employee.getScore();
            }
            double f = (double) sum / employees.size();
            ratings.put(entry.getKey(), f);
        }
        return ratings;
    }

    /**
     * 5.getCountCoolestEmployees
     * Определить количество самых успешных сотрудников по каждому отделу
     */
    public Map<Integer, Integer> getCountCoolestEmployees() {
        Map<Integer, Integer> depart = new HashMap<>();
        Map<Integer, ArrayList<Employee>> coolestEmployees = getCoolestEmployees();
        for (var entry : coolestEmployees.entrySet()) {
            depart.put(entry.getKey(), entry.getValue().size());
        }
        return depart;
    }

    /**
     * 6.getCoolestEmployeesAll
     * Определить самых успешных сотрудников по всем отделам
     */
    public ArrayList<Employee> getCoolestEmployeesAll() {
        Map<Integer, ArrayList<Employee>> coolestEmployees = getCoolestEmployees();
        ArrayList<Employee> employees = new ArrayList<>();
        int score = 0;
        for (ArrayList<Employee> value : coolestEmployees.values()) {
            for (Employee employee : value) {
                if (employee.getScore() > score) {
                    score = employee.getScore();
                }
            }
        }
        for (ArrayList<Employee> value : coolestEmployees.values()) {
            for (Employee employee : value) {
                if (employee.getScore() == score) {
                    employees.add(employee);
                }
            }
        }
        return employees;
    }

    /**
     * 7.getSubCoolestEmployees
     * Определить сотрудников, не ставших самыми успешными,
     * но следующих сразу же после успешных по каждому отделу
     */
    public Map<Integer, ArrayList<Employee>> getSubCoolestEmployees() {
        Map<Integer, Integer> maxScores = getMaxScores();
        Map<Integer, ArrayList<Employee>> res = new HashMap<>();
        for (var entry : this.employees.entrySet()) {
            int secMax = Integer.MIN_VALUE;
            int max = maxScores.get(entry.getKey());
            for (Employee employee : entry.getValue()) {
                if (employee.getScore() > secMax && employee.getScore() < max) {
                    secMax = employee.getScore();
                }
            }

            for (Employee employee : entry.getValue()) {
                if (employee.getScore() == secMax) {
                    ArrayList<Employee> list = res.getOrDefault(entry.getKey(), new ArrayList<>());
                    list.add(employee);
                    res.put(entry.getKey(), list);
                }
            }
        }
        return res;
    }

    /**
     * 8.getMaxScoresSubCoolestEmployees
     * Определить рейтинг сотрудников, не ставших самыми успешными,
     * но следующих сразу же после успешных по каждому отделу и по всем отделам
     */
    public Map<Integer, Integer> getMaxScoresSubCoolestEmployees() {
        Map<Integer, ArrayList<Employee>> subCoolestEmployees = getSubCoolestEmployees();
        Map<Integer, Integer> rating = new HashMap<>();
        for (var entry : subCoolestEmployees.entrySet()) {
            rating.put(entry.getKey(), entry.getValue().get(0).getScore());
        }
        return rating;
    }

    /**
     * 9.getMaxScoreAll
     * Определить рейтинг самых успешных сотрудников по всем отделам
     */
    public int getMaxScoreAll() {
        int rate = Integer.MIN_VALUE;
        ArrayList<Employee> coolestEmployeesAll = getCoolestEmployeesAll();
        for (Employee employee : coolestEmployeesAll) {
            if (employee.getScore() > rate) {
                rate = employee.getScore();
            }
        }
        return rate;
    }

    /**
     * 10.	getMaxCountDepartments
     * Вернуть в порядке возрастания номера департаментов, где работает больше всего сотрудников
     */
    public ArrayList<Integer> getMaxCountDepartments() {
        int number = Integer.MIN_VALUE;
        for (ArrayList<Employee> value : employees.values()) {
            if (value.size() > number) {
                number = value.size();
            }
        }
        ArrayList<Integer> list = new ArrayList<>();
        for (var entry : this.employees.entrySet()) {
            if (entry.getValue().size() == number) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    /**
     * 11.getMinCountDepartments
     * Вернуть в порядке возрастания номера департаментов, где работает меньше всего сотрудников
     */
    public ArrayList<Integer> getMinCountDepartments() {
        int number = Integer.MAX_VALUE;
        for (ArrayList<Employee> value : employees.values()) {
            if (value.size() < number) {
                number = value.size();
            }
        }
        ArrayList<Integer> list = new ArrayList<>();
        for (var entry : this.employees.entrySet()) {
            if (entry.getValue().size() == number) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    /**
     * 12.sort
     * Отсортировать коллекцию сотрудников по фамилии, при равенстве фамилии по имени
     */
    public ArrayList<Employee> sortBySurName() {
        ArrayList<Employee> employees = new ArrayList<>();
        for (ArrayList<Employee> value : this.employees.values()) {
            employees.addAll(value);
        }
        employees.sort(new EmployeeComparatorBySurName());
        return employees;
    }

    /**
     * 13.sort
     * Отсортировать коллекцию сотрудников:
     * •По убыванию рейтинга
     * •При равных значениях рейтинга - по фамилии в лексикографическом порядке
     * •При совпадении рейтинга и фамилии - по имени в лексикографическом порядке
     */
    public ArrayList<Employee> sortByRatingDec() {
        ArrayList<Employee> employees = new ArrayList<>();
        for (ArrayList<Employee> value : this.employees.values()) {
            employees.addAll(value);
        }
        employees.sort(new EmployeeComparatorRate());
        return employees;
    }

    /**
     * 14.departmentCoolestEmployees
     * Вычислить номера департаментов в порядке возрастания, в которых есть хотя бы один сотрудник,
     * ставший самым успешным по всем департаментам
     */
    public ArrayList<Integer> departmentCoolestEmployees() {
        int maxScoreAll = getMaxScoreAll();
        ArrayList<Integer> departments = new ArrayList<>();
        for (var entry : employees.entrySet()) {
            for (Employee employee : entry.getValue()) {
                if (employee.getScore() == maxScoreAll) {
                    departments.add(entry.getKey());
                    break;
                }
            }
        }
        return departments;
    }

    /**
     * 15.	greatAverageScoreDepartments
     * Вычислить в порядке возрастания номера департаментов, средний рейтинг сотрудников которых выше,
     * чем средний рейтинг всех сотрудников в компании. То есть необходимо вычислить средний рейтинг для каждого
     * департамента отдельно и средний рейтинг по всем департаментам вместе взятых. Модернизировать метод из
     * п. 4 таким образом, чтобы он возвращал среднее не только по отделам, но и по департаментам
     */
    public ArrayList<Integer> greatAverageScoreDepartments() {
        double f = 0;
        for (ArrayList<Employee> value : employees.values()) {
            for (Employee employee : value) {
                f += employee.getScore();
            }
        }
        f = f / employees.values().size();
        ArrayList<Integer> departments = new ArrayList<>();
        Map<Integer, Double> avarageScores = getAvarageScores();
        for (Map.Entry<Integer, Double> entry : avarageScores.entrySet()) {
            if (entry.getValue() > f) {
                departments.add(entry.getKey());
            }
        }
        return departments;
    }

    /**
     * 16.	maxAverageScoreDepartments
     * Вычислить в порядке возрастания номера департаментов, средний рейтинг сотрудников которых максимален
     */
    public Set<Integer> maxAverageScoreDepartments() {
        Map<Integer, Double> avarageScores = getAvarageScores();
        double f = Double.MIN_VALUE;
        for (Double value : avarageScores.values()) {
            if (value > f) {
                f = value;
            }
        }
        Set<Integer> res = new TreeSet<>();
        for (Map.Entry<Integer, Double> entry : avarageScores.entrySet()) {
            if (entry.getValue() == f) {
                res.add(entry.getKey());
            }
        }
        return res;
    }

    /**
     * 17.	sortByCountEmployees
     * Вернуть номера департаментов в порядке убывания количества сотрудников,
     * работающих в этих департаментах. Если в двух департаментах работает одинаковое
     * количество сотрудников, то их номера выводятся в порядке возрастания номера департамента
     */
    public ArrayList<Integer> sortByCountEmployees() {
        ArrayList<Map.Entry<Integer, ArrayList<Employee>>> list = new ArrayList<>(employees.entrySet());
        list.sort(new Comparator<Map.Entry<Integer, ArrayList<Employee>>>() {
            @Override
            public int compare(Map.Entry<Integer, ArrayList<Employee>> o1, Map.Entry<Integer, ArrayList<Employee>> o2) {
                if (o1.getValue().size() == o2.getValue().size()) {
                    return Integer.compare(o1.getKey(), o2.getKey());
                }
                return -Integer.compare(o1.getValue().size(), o2.getValue().size());
            }
        });
        ArrayList<Integer> res = new ArrayList<>();
        for (Map.Entry<Integer, ArrayList<Employee>> entry : list) {
            res.add(entry.getKey());
        }
        return res;
    }

    /**
     * 18.	sortByAverageScores
     * Вернуть номера департаментов в порядке убывания среднего рейтинга
     * сотрудников, работающих в этих департаментах. Если в двух департаментах
     * имеется одинаковый средний рейтинг сотрудников, то их номера выводятся
     * в порядке возрастания номера департамента
     */
    public ArrayList<Integer> sortByAverageScores() {
        Map<Integer, Double> avarageScores = getAvarageScores();
        List<Map.Entry<Integer, Double>> entries = new ArrayList<>(avarageScores.entrySet());

        entries.sort(new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                if (Objects.equals(o1.getValue(), o2.getValue())) {
                    return Integer.compare(o1.getKey(), o2.getKey());
                }
                return -Double.compare(o1.getValue(), o2.getValue());
            }
        });

        ArrayList<Integer> res = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : entries) {
            res.add(entry.getKey());
        }
        return res;
    }

    /**
     * 19.	maxCountCoolestEmployeeDepartments
     * Вернуть в порядке возрастания номера департаментов,
     * из которых наибольшее количество сотрудников стало
     * самыми успешными
     */
    public Set<Integer> maxCountCoolestEmployeeDepartments() {
        ArrayList<Employee> all = getCoolestEmployeesAll();
        Map<Integer, ArrayList<Employee>> employees = new HashMap<>();

        for (Map.Entry<Integer, ArrayList<Employee>> entry : this.employees.entrySet()) {
            for (Employee employee : entry.getValue()) {
                for (Employee emp : all) {
                    if (employee.getScore() == emp.getScore()) {
                        ArrayList<Employee> list = employees.getOrDefault(entry.getKey(), new ArrayList<>());
                        list.add(employee);
                        employees.put(entry.getKey(), list);
                    }
                }
            }
        }

        int f = Integer.MIN_VALUE;
        for (Map.Entry<Integer, ArrayList<Employee>> entry : employees.entrySet()) {
            if (entry.getValue().size() > f) {
                f = entry.getValue().size();
            }
        }

        Set<Integer> departments = new HashSet<>();
        for (Map.Entry<Integer, ArrayList<Employee>> entry : employees.entrySet()) {
            if (entry.getValue().size() == f) {
                departments.add(entry.getKey());
            }
        }

        return departments;
    }
}
