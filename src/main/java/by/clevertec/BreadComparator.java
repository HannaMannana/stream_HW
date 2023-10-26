package by.clevertec;

import by.clevertec.model.Animal;

import java.util.Comparator;

public class BreadComparator implements Comparator<Animal> {

    @Override
    public int compare(Animal a, Animal b) {

        return a.getBread().compareTo(b.getBread());
    }
}
