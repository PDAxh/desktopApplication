package com.testverktyg.eclipselink.view.teacher.createTest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by Grodfan on 2017-05-04.
 *
 */
public class NewTest {

    private String testName;
    private ArrayList<NewQuestion> questionArrayList = new ArrayList<>();
    private ArrayList<NewAlternativ> alternativArrayList = new ArrayList<>();

    public NewTest(){}

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public ArrayList<NewQuestion> getQuestionArrayList() {
        return questionArrayList;
    }

    public ArrayList<NewAlternativ> getAlternativArrayList() {
        return alternativArrayList;
    }
}
