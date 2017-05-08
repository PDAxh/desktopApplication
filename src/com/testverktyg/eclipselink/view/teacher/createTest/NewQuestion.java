package com.testverktyg.eclipselink.view.teacher.createTest;

/**
 * Created by Grodfan on 2017-05-04.
 *
 */
public class NewQuestion {

    private boolean gradeG;
    private boolean gradeVG;
    private String questionName;
    private String typeOfQuestion;
    private int questionId;

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(String typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public boolean isGradeG() {
        return gradeG;
    }

    public void setGradeG(boolean gradeG) {
        this.gradeG = gradeG;
    }

    public boolean isGradeVG() {
        return gradeVG;
    }

    public void setGradeVG(boolean gradeVG) {
        this.gradeVG = gradeVG;
    }
}
