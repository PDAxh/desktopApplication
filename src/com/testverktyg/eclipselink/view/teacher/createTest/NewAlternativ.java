package com.testverktyg.eclipselink.view.teacher.createTest;

/**
 * Created by Grodfan on 2017-05-04.
 *
 */
public class NewAlternativ {

    private String alternative;
    private boolean rightAnswer;
    private int alternativeId;

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

    public int getAlternativeId() {
        return alternativeId;
    }

    public void setAlternativeId(int alternativeId) {
        this.alternativeId = alternativeId;
    }
}
