package by.clevertec;

import by.clevertec.model.Person;

import java.util.Comparator;

public class RecruitmentGroupComparator implements Comparator<Person> {
    @Override
    public int compare(Person a, Person b) {

        return a.getRecruitmentGroup() - b.getRecruitmentGroup();
    }
}
