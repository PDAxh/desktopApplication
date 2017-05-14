package com.testverktyg.eclipselink.view.teacher.createTest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Jonas Johansson, Java2 on 2017-05-04.
 *
 */
public class NewQuestion {

    private boolean gradeG;
    private boolean gradeVG;
    private String questionName;
    private String typeOfQuestion;
    private int numberOfAlternatives;
    private int points;
    private ObservableList<NewAlternativ> alternativObservableList = FXCollections.observableArrayList();

    public NewQuestion(){}

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

    public int getNumberOfAlternatives() {
        return numberOfAlternatives;
    }

    public void setNumberOfAlternatives(int numberOfAlternatives) {
        this.numberOfAlternatives = numberOfAlternatives;
    }

    public ObservableList<NewAlternativ> getAlternativObservableList() {
        return alternativObservableList;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
