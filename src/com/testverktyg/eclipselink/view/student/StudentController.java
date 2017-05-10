package com.testverktyg.eclipselink.view.student;

import com.testverktyg.eclipselink.service.Test.ReadTest;
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
    @FXML private GridPane testList;
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
    private Button getTestButton;
    ReadTest newTest;

    int maxQuestions=0;
    int activeQuestion=1;
    int activeQuestionsForDB=0;
    String questionGrade="";

    @FXML
    private void getTest() {
        ReadTest newTest = new ReadTest(2);
        newTest.getActiveTest();
        maxQuestions=newTest.getAmountOfQuestions();
        showToStudentTestNameLabel.setText(newTest.getTestName());
        showToStudentTextLabel.setText(newTest.getTestDescription());
        showToStudentTimeLabel.setText(String.valueOf(newTest.getTestTimeInMinutes()));
    }

    @FXML
    private void startTest() {
        ReadTest newTest = new ReadTest(2);
        newTest.getActiveTest();
        System.out.println("Test started");
        contentPane.getChildren().removeAll(showToUserStartTestButton, showToStudentTimeTextLabel, showToStudentTimeLabel, showToStudentTeacherTextLabel, showToStudentTeacherLabel, showToStudentClassTextLabel, showToStudentClassLabel);
        this.printAlternatives();
        showToUserNextButton.setVisible(true);
        showToStudentTextLabel.setText(String.valueOf(newTest.getActiveQuestionText().get(activeQuestionsForDB)));
        questionGrade=newTest.getGradeOnActiveQuestion();//CREATE NODE

    }

    @FXML
    private void printAlternatives() {
        ReadTest newTest = new ReadTest(2);
        newTest.getActiveTest();
        showToStudentTestNameLabel.setText(newTest.getTestName());
        String typeOfQuestion = "Flervals";
        short numberOfAlternatives = 2;
        short correctAnswer = 0;
        ToggleGroup toggleGroup = new ToggleGroup();

            if (typeOfQuestion.equals("Flervals")) {
                for (int i = 0; i < newTest.getActiveAlternativeId().size(); i++) {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setId(String.valueOf(i));
                    Label alternativeText = new Label();
                    alternativeText.setText(String.valueOf(newTest.getActiveAlternativeText().get(i)));
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
    @FXML
    private void nextQuestion() {
        ReadTest newTest = new ReadTest(2);
        newTest.getActiveTest();
        activeQuestion++;
        activeQuestionsForDB++;
        questionGrade="";
        showToStudentTextLabel.setText("");
        alternativePane.getChildren().clear();
        newTest.getNextActiveQuestion();
        if(activeQuestion==maxQuestions){
            showToUserNextButton.setDisable(true);
            showToStudentTextLabel.setText(String.valueOf(newTest.getActiveQuestionText().get(activeQuestionsForDB)));
            questionGrade=newTest.getGradeOnActiveQuestion();//CREATE NODE
            this.printAlternatives();
        }else{
            showToStudentTextLabel.setText(String.valueOf(newTest.getActiveQuestionText().get(activeQuestionsForDB)));
            questionGrade=newTest.getGradeOnActiveQuestion();//CREATE NODE
            this.printAlternatives();
        }
    }

}

