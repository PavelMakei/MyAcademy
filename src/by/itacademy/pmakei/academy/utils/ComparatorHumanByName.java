package by.itacademy.pmakei.academy.utils;

import by.itacademy.pmakei.academy.entity.Human;
import java.util.Comparator;

public class ComparatorHumanByName implements Comparator<Human> {

    @Override
    public int compare(Human h1, Human h2) {
        String str1 = h1.getName();
        String str2 = h2.getName();
        return str1.compareTo(str2);
    }

}
