package by.itacademy.pmakei.academy.utils;

import by.itacademy.pmakei.academy.entity.Human;
import java.util.Comparator;

public class ComparatorHumanBySurname implements Comparator<Human> {

    @Override
    public int compare(Human h1, Human h2) {
        String str1 = h1.getSurname();
        String str2 = h2.getSurname();
        return str1.compareTo(str2);
    }

}
