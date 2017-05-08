package com.testverktyg.eclipselink.view.teacher.createTest;

/**
 * Created by Grodfan on 2017-05-04.
 *
 */
public class NewQuestion {

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
}
