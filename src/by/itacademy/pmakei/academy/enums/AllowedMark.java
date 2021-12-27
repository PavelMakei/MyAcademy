package by.itacademy.pmakei.academy.enums;

public enum AllowedMark {

    FAILING("Failing"),
    BELOW_AVERAGE ("Below average"),
    AVERAGE("Average"),
    VERY_GOOD("Very good"),
    EXCELLENT("Excellent");

private String markName;


    AllowedMark(String markName) {
        this.markName = markName;
    }

    @Override
    public String toString() {
        return markName + " (" + (ordinal() + 1)+
                ')';
    }

}
