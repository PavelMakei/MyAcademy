package by.itacademy.pmakei.academy.entity;

import java.io.Serializable;

public abstract class Human implements Comparable, Serializable {

    private static int humanIdCount = 1;

    private String name;
    private String surname;
    private int age;
    private final int PERSONAL_ID;


    public Human(int id, String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.PERSONAL_ID = id;
        humanIdCount++;
    }

    public static int getHumanId() {
        return humanIdCount;
    }

    public int getPersonalId() {
        return PERSONAL_ID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public static void setHumanIdCount(int id){
        humanIdCount = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return age == human.age && PERSONAL_ID == human.PERSONAL_ID && name.equals(human.name) && surname.equals(human.surname);
    }

    @Override
    public int hashCode() {
        return PERSONAL_ID;
    }

    @Override
    public int compareTo(Object o) {
        Human human = (Human) o;
        return this.getName().compareTo(human.getName());

    }
}
