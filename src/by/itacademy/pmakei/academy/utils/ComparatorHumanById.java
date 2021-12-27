package by.itacademy.pmakei.academy.utils;

import by.itacademy.pmakei.academy.entity.Human;

import java.util.Comparator;

/**
 * @author Pavel Makei
 */
public class ComparatorHumanById implements Comparator<Human> {

    @Override
    public int compare(Human h1, Human h2) {
        return h1.getPersonalId() - (h2.getPersonalId());
    }

}
