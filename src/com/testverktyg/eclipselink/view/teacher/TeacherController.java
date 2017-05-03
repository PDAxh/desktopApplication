package com.testverktyg.eclipselink.view.teacher;

import javafx.fxml.FXML;


import javafx.scene.control.CheckBox;

/**
 * Created by Grodfan on 2017-05-01.
 */
public class TeacherController {

    @FXML private CheckBox selfCorrect;
    @FXML private CheckBox showResultsToStudent;

    @FXML
    private void setShowResultToStudent(){
        if(selfCorrect.isSelected()){
            this.showResultsToStudent.setDisable(false);
        }
        else{
            showResultsToStudent.setSelected(false);
            this.showResultsToStudent.setDisable(true);
        }



    }


}
