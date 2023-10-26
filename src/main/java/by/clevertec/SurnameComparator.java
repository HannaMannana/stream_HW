package by.clevertec;

import by.clevertec.model.Student;

import java.util.Comparator;

public class SurnameComparator implements Comparator<Student> {
    @Override
    public int compare(Student a, Student b) {
        return a.getSurname().compareTo(b.getSurname());
    }
}
