package by.itacademy.pmakei.academy.exceptions;

import java.io.IOException;

public class IncorrectEnterException extends Exception {

    private int value;
    public int getNumber(){return value;}
    public IncorrectEnterException(String message, int num) {

        super(message);
        value = num;
    }
}
//if(num<1) throw new IncorrectEnterException("The number is less than 1", num);

//catch(IncorrectEnterException ex){
//
//        System.out.println(ex.getMessage());
//        System.out.println(ex.getNumber());