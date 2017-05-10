package com.testverktyg.eclipselink.view.teacher.createTest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Jonas Johansson, Java2, on 2017-05-04.
 *
 */
public class NewTest {

    private ObservableList<NewQuestion> questionObservableList = FXCollections.observableArrayList();

    public NewTest(){}

    public ObservableList<NewQuestion> getQuestionObservableList() {
        return questionObservableList;
    }
}
