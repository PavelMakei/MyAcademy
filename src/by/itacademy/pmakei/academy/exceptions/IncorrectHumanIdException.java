package by.itacademy.pmakei.academy.exceptions;

/**
 * @author Pavel Makei
 */
public class IncorrectHumanIdException extends Exception{
    private int enteredIdvalue;
    public int getNumber(){return enteredIdvalue;}
    public IncorrectHumanIdException (String message, int id) {

        super(message);
        enteredIdvalue = id;
    }
}
