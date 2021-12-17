package by.itacademy.pmakei.academy.utils;

import by.itacademy.pmakei.academy.entity.Human;

import java.util.Comparator;

public class ComparatorHumanByName implements Comparator<Human> {

    @Override
    public int compare(Human o1, Human o2) {
        String str1 = o1.getName();
        String str2 = o2.getName();
        return str1.compareTo(str2);
    }
}
