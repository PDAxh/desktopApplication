package com.testverktyg.eclipselink.view.teacher.createTest;

/**
 * Created by Jonas Johansson, Java2 on 2017-05-04.
 *
 */
public class NewAlternativ {

    private String alternative;
    private boolean rightAnswer;

    public NewAlternativ(){}

    public String getAlternative() {
        return alternative;
    }

    public void setAlternative(String alternative) {
        this.alternative = alternative;
    }

    public boolean getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
