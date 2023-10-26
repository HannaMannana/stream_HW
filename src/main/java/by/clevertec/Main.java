package by.clevertec;

import by.clevertec.model.*;
import by.clevertec.util.Util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
        task17();
        task18();
        task19("C-3");
        task20();
        task21();
        task22();
    }

    public static void task1() {
        List<Animal> animals = Util.getAnimals();

        animals.stream().filter(a ->a.getAge()>=10 && a.getAge()<=20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(7 *2)
                .limit(7)
                .forEach(System.out::println);
    }

    public static void task2() {
        List<Animal> animals = Util.getAnimals();

        List<String> anim = animals.stream()
                .filter(a -> "Japanese".equals(a.getOrigin()))
                .filter(a -> "Female".equals(a.getGender()))
                .map(Animal::getBread)
                .map(String::toUpperCase)
                .toList();
        System.out.println(anim);

    }

    public static void task3() {
        List<Animal> animals = Util.getAnimals();
        animals.stream().filter(a ->a.getAge()>30)
                .map(Animal::getOrigin)
                .distinct()
                .sorted()
                .filter( a-> {
                    return a.charAt(0) == 'A';
                })
                .forEach(System.out::println);
    }

    public static void task4() {
        List<Animal> animals = Util.getAnimals();

        long count = animals.stream().filter(a -> a.getGender().equals("Female")).count();
        System.out.println(count);
    }

    public static void task5() {
        List<Animal> animals = Util.getAnimals();

        boolean isExist = animals.stream().filter(a ->a.getAge()>=20 && a.getAge()<=30)
                .anyMatch(o -> "Hungarian".equals(o.getOrigin()));
        System.out.println(isExist);
    }

    public static void task6() {
        List<Animal> animals = Util.getAnimals();

        boolean list = animals.stream().allMatch(a -> a.getGender().equals("Female") && a.getGender().equals("Male"));
        System.out.println(list);
    }

    public static void task7() {
        List<Animal> animals = Util.getAnimals();

        boolean list = animals.stream().anyMatch(o -> "Oceania".equals(o.getOrigin()));
        System.out.println(list);
    }

    public static void task8() {
        List<Animal> animals = Util.getAnimals();

        long age = animals.stream()
                .sorted(new BreadComparator())
                .limit(100)
                .map(Animal::getAge)
                .max(Integer::compareTo).get();
        System.out.println(age);
    }

    public static void task9() {
        List<Animal> animals = Util.getAnimals();

        OptionalLong min = animals.stream()
                .map(Animal::getBread)
                .map(s -> s.split(""))
                .mapToLong(s -> s.length)
                .min();
        System.out.println(min.orElseThrow());
    }

    public static void task10() {
        List<Animal> animals = Util.getAnimals();

        int age = animals.stream().map(Animal::getAge)
                .reduce(0, Integer::sum);
        System.out.println(age);
    }

    public static void task11() {
        List<Animal> animals = Util.getAnimals();

        Double age = animals.stream()
                .filter((s) -> s.getOrigin().equals("Indonesian"))
                .mapToInt(Animal::getAge).average().getAsDouble();
        System.out.println(age);

    }

    public static void task12() {
        List<Person> persons = Util.getPersons();

        persons.stream().filter(a -> a.getGender().equals("Male"))
                .filter(a -> calculateAge(a.getDateOfBirth(), LocalDate.now()) >= 18 && calculateAge(a.getDateOfBirth(), LocalDate.now()) <= 27)
                .sorted(new RecruitmentGroupComparator())
                .limit(200)
                .forEach(System.out::println);
    }

    public static void task13() {
        List<House> houses = Util.getHouses();

        houses.stream()
                .flatMap(house -> house.getPersonList().stream()
                        .filter(person -> calculateAge(person.getDateOfBirth(), LocalDate.now()) <= 18
                                || calculateAge(person.getDateOfBirth(), LocalDate.now()) >= 60
                                || Objects.equals(house.getBuildingType(), "Hospital")))
                .limit(500)
                .forEach(System.out::println);

    }

    public static void task14() {
        List<Car> cars = Util.getCars();

        Double totalSum = cars.stream().map(car -> {
                    Map<String, Car> map = new HashMap<>();
                    if(car.getCarMake().equals("Jaguar") || car.getColor().equals("White")){
                        map.put("Turkmenistan", car);
                    } else if (car.getMass() <= 1500 || (car.getCarMake().equals("BMW") || car.getCarMake().equals("Lexus")
                            || car.getCarMake().equals("Chrysler") || car.getCarMake().equals("Toyota"))){
                        map.put("Uzbekistan", car);
                    } else if ((car.getColor().equals("Black") && car.getMass() >= 4000)
                            || car.getCarMake().equals("GMC") || car.getCarMake().equals("Dodge")){
                        map.put("Kazakhstan", car);
                    } else if (car.getReleaseYear() < 1982 || car.getCarModel().equals("Civic") || car.getCarModel().equals("Cherokee")) {
                        map.put("Kyrgyzstan", car);
                    } else if (!car.getColor().equals("Yellow") || !car.getColor().equals("Red") || !car.getColor().equals("Green")
                            || !car.getColor().equals("Blue") || car.getPrice() > 40000) {
                        map.put("Russia", car);
                    } else if (car.getVin().contains("59")) {
                        map.put("Mongolia", car);
                    }
                    return map;
                })
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
                .values().stream()
                .map(value -> value.stream()
                        .mapToDouble(Car::getMass)
                        .sum() * 7.14 / 1000)
                .peek(System.out::println)
                .mapToDouble(price -> price)
                .sum();
        System.out.println(totalSum);
    }

    public static void task15() {
        List<Flower> flowers = Util.getFlowers();

        double sum = flowers
                .stream()
                .sorted(Comparator
                        .comparing(Flower::getOrigin)
                        .thenComparing(Comparator
                                .comparingInt(Flower::getPrice).reversed()
                                .thenComparing(Comparator
                                        .comparingDouble(Flower::getWaterConsumptionPerDay).reversed())))
                .filter(flower -> Pattern.compile("^[^ABTUVWXYZ]").matcher(flower.getCommonName().toUpperCase(Locale.ROOT)).find())
                .filter(flower -> flower.isShadePreferred() &&
                        (flower.getFlowerVaseMaterial().contains("Glass") ||
                                flower.getFlowerVaseMaterial().contains("Steel") ||
                                flower.getFlowerVaseMaterial().contains("Aluminum")))
                .mapToDouble(flower -> {
                    double waterConsumptionPerDay = flower.getWaterConsumptionPerDay();
                    double waterConsumptionFiveYears = waterConsumptionPerDay * 365 * 5;
                    double waterCost = waterConsumptionFiveYears * 1.39;
                    return waterCost + flower.getPrice();
                }).sum();

        System.out.println(sum + "$");
    }

    public static void task16() {
        List<Student> students = Util.getStudents();

        students.stream()
                .filter(a -> a.getAge() <= 18)
                .sorted(new SurnameComparator())
                .forEach(s -> System.out.println(s.getSurname() + " " + s.getAge()));
    }

    public static void task17() {
        List<Student> students = Util.getStudents();

        students.stream()
                .map(Student::getGroup)
                .distinct()
                .forEach(System.out::println);
    }

    public static void task18() {
        List<Student> students = Util.getStudents();

        List<Map.Entry<String, Double>> collect = students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty, Collectors.averagingDouble(Student::getAge)))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingDouble(value -> value.getValue()))
                .peek(stringDoubleEntry -> {
                    Double averageAge = stringDoubleEntry.getValue();

                    BigDecimal bigDecimal = new BigDecimal(Double.toString(averageAge));
                    bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);

                    stringDoubleEntry.setValue(bigDecimal.doubleValue());
                })
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    public static void task19(String group) {
        List<Examination> examinations = Util.getExaminations();

        List<Integer>ids = examinations.stream()
                .filter(s -> s.getExam3() > 4)
                .map(Examination::getStudentId).toList();

        List<Student> students = Util.getStudents();

        students.stream()
                .filter(s -> s.getGroup().equals(group))
                .filter(s -> ids.contains(s.getId()))
                .forEach(System.out::println);
    }

    public static void task20() {
        List<Student> students = Util.getStudents();

        List<Examination> examinations = Util.getExaminations();

        students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty,
                        Collectors.averagingDouble(student -> examinations.stream()
                                .filter(ex -> ex.getStudentId() == student.getId())
                                .mapToDouble(Examination::getExam1)
                                .findFirst()
                                .orElse(0))))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
    }

    public static void task21() {
        List<Student> students = Util.getStudents();
        Map<String, Long> map = students.stream()
                .collect(Collectors.groupingBy(Student::getGroup, Collectors.counting()));
        System.out.println(map);
    }

    public static void task22() {
        List<Student> students = Util.getStudents();
        students.stream().collect(Collectors.groupingBy(Student::getFaculty,
                                Collectors.minBy((l1, l2) -> Double.compare(l1.getAge(), l2.getAge()))
                        )
                )
                .forEach((key, value) -> System.out.println(key + " " + value.orElseThrow().getAge()));
    }

    public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }

}
