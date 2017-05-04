package com.testverktyg.eclipselink.view.teacher;

import javafx.fxml.FXML;


import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Created by Grodfan on 2017-05-01.
 *
 */
public class TeacherController {

    @FXML private CheckBox selfCorrect;
    @FXML private CheckBox showResultsToStudent;
    @FXML private Label showResultsToStudentLabel;

    @FXML
    private void setShowResultToStudent(){
        if(getSelfCorrect().isSelected()){
            getShowResultsToStudent().setDisable(false);
            showResultsToStudentLabel.setTextFill(Color.web("#000000"));
        }
        else{
            getShowResultsToStudent().setSelected(false);
            getShowResultsToStudent().setDisable(true);
            showResultsToStudentLabel.setTextFill(Color.web("#d3d3d3"));
        }
    }

    private CheckBox getSelfCorrect() {
        return selfCorrect;
    }

    private CheckBox getShowResultsToStudent() {
        return showResultsToStudent;
    }
}
