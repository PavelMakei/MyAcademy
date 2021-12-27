package by.itacademy.pmakei.academy.exceptions;

/**
 * @author Pavel Makei
 */
public class IncorrectHumanIdException extends Exception{
    private int enteredIdvalue;

    public IncorrectHumanIdException (String message, int id) {

        super(message + " " + id);
        enteredIdvalue = id;
    }

}
