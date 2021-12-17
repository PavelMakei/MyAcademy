package by.itacademy.pmakei.academy.utils;

import by.itacademy.pmakei.academy.entity.Human;

import java.util.Comparator;

public class ComparatorHumanBySurname implements Comparator<Human> {

    @Override
    public int compare(Human o1, Human o2) {
        String str1 = o1.getSurname();
        String str2 = o2.getSurname();
        return str1.compareTo(str2);
    }
}
