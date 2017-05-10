package com.testverktyg.eclipselink.view.student;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Created by Grodfan on 2017-05-01.
 */
public class StudentController {
    @FXML
    private BorderPane roo;
    @FXML
    private GridPane alternativePane;
    @FXML
    private GridPane contentPane;
    @FXML
    private Label showToStudentTestNameLabel;
    @FXML
    private Label showToStudentTextLabel;
    @FXML
    private Label showToStudentTimeTextLabel;
    @FXML
    private Label showToStudentTimeLabel;
    @FXML
    private Label showToStudentTeacherTextLabel;
    @FXML
    private Label showToStudentTeacherLabel;
    @FXML
    private Label showToStudentClassTextLabel;
    @FXML
    private Label showToStudentClassLabel;
    @FXML
    private Button showToUserStartTestButton;
    @FXML
    private Button showToUserNextButton;


    @FXML
    private void startTest() {
        System.out.println("Test started");
        contentPane.getChildren().removeAll(showToUserStartTestButton, showToStudentTimeTextLabel, showToStudentTimeLabel, showToStudentTeacherTextLabel, showToStudentTeacherLabel, showToStudentClassTextLabel, showToStudentClassLabel);
        this.printAlternatives();
        showToUserNextButton.setVisible(true);
    }

    @FXML
    private void printAlternatives() {
        String typeOfQuestion = "Annat";
        short numberOfAlternatives = 2;
        short correctAnswer = 0;
        ToggleGroup toggleGroup = new ToggleGroup();

            if (typeOfQuestion.equals("Flervals")) {
                for (int i = 0; i < numberOfAlternatives; i++) {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setId(String.valueOf(i));
                    Label alternativeText = new Label();
                    alternativeText.setText("Text here");
                    alternativePane.add(checkBox, 0, i);
                    alternativePane.add(alternativeText, 1, i);
                }
            }else {
                for (int y = 0; y < numberOfAlternatives; y++) {
                    RadioButton radioButton = new RadioButton();
                    radioButton.setId(String.valueOf(y));
                    radioButton.setToggleGroup(toggleGroup);
                    Label alternativeText = new Label();
                    alternativeText.setText("Text here");
                    alternativePane.add(radioButton, 0, y);
                    alternativePane.add(alternativeText, 1, y);
                }
            }
    }
}

