package com.testverktyg.eclipselink.view.teacher;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.io.IOException;


/**
 * Created by Grodfan on 2017-05-01.
 *
 */
public class TeacherController{

    @FXML private CheckBox selfCorrect;
    @FXML private CheckBox showResultsToStudent;
    @FXML private Label showResultsToStudentLable;
    @FXML private BorderPane roo;

    @FXML
    private void setShowResultToStudent(){
        if(getSelfCorrect().isSelected()){
            getShowResultsToStudent().setDisable(false);
            showResultsToStudentLable.setTextFill(Color.web("#000000"));
        }
        else{
            getShowResultsToStudent().setSelected(false);
            getShowResultsToStudent().setDisable(true);
            showResultsToStudentLable.setTextFill(Color.web("#d3d3d3"));
        }
    }

    private CheckBox getSelfCorrect() {
        return selfCorrect;
    }

    private CheckBox getShowResultsToStudent() {
        return showResultsToStudent;
    }

    @FXML
    private void addQuestion() throws IOException{
        GridPane gridPane = FXMLLoader.load(getClass().getResource("layout/createNewQuestion.fxml"));
        roo.setBottom(gridPane);
    }

}
