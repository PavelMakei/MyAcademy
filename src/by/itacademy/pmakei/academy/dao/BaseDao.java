package by.itacademy.pmakei.academy.dao;

import by.itacademy.pmakei.academy.entity.Human;
import by.itacademy.pmakei.academy.interfaces.Dao;

import java.util.ArrayList;
import java.util.List;

public class BaseDao<T> implements Dao<T> {

    private List humanList;
//    private Human human;

    public BaseDao(List humanList) {
        this.humanList = humanList;

    }

    @Override
    public void add(Human human) {
        humanList.add(human);

    }
}
