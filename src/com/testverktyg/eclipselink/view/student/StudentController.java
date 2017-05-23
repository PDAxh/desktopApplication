package com.testverktyg.eclipselink.view.student;

import com.testverktyg.eclipselink.service.Test.ReadTest;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Andreas.
 */
public class StudentController {
    @FXML
    private BorderPane roo;
    @FXML
    private GridPane alternativePane;
    @FXML
    private GridPane contentPane;
    @FXML
    private GridPane testList;
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
    @FXML private
    Label showToStudentQuestionsLeft;
    @FXML private
    Label showToStudentQuestionsLeftText;
    @FXML private
    Label showToStudentGrade;
    @FXML private
    Label showToStudentGradeText;
    @FXML private
    Label questionPointsLabel;
    @FXML private
    Label questionPointsTextLabel;
    @FXML private
    Label timerLabel;
    @FXML private
    Label timeTextLabel;

    int maxQuestions=0;
    int activeQuestion=1;
    int activeQuestionsForDB=0;
    String questionGrade="";
    ReadTest newTest = new ReadTest(1);

    @FXML
    private void getTest() {
        newTest.getActiveTest();
        maxQuestions=newTest.getAmountOfQuestions();
        showToStudentTestNameLabel.setText(newTest.getTestName());
        showToStudentTextLabel.setText(newTest.getTestDescription());
        showToStudentTimeLabel.setText(String.valueOf(newTest.getTestTimeInMinutes()));
    }

    @FXML
    private void startTest() {
        newTest.getActiveTest();
        System.out.println("Test started");
        contentPane.getChildren().removeAll(showToUserStartTestButton, showToStudentTimeTextLabel, showToStudentTimeLabel, showToStudentTeacherTextLabel, showToStudentTeacherLabel, showToStudentClassTextLabel, showToStudentClassLabel);
        showToStudentQuestionsLeftText.setVisible(true);
        showToStudentQuestionsLeft.setVisible(true);
        showToStudentGradeText.setVisible(true);
        showToStudentGrade.setVisible(true);
        timeTextLabel.setVisible(true);
        timerLabel.setVisible(true);
        questionPointsTextLabel.setVisible(true);
        questionPointsLabel.setVisible(true);

        showToStudentQuestionsLeft.setText(activeQuestion+"/"+newTest.getAmountOfQuestions()+"    ");
        showToStudentGrade.setText(newTest.getGradeOnActiveQuestion());
        questionPointsLabel.setText("");
        this.printAlternatives();
        showToUserNextButton.setVisible(true);
        showToStudentTextLabel.setText(String.valueOf(newTest.getActiveQuestionText().get(activeQuestionsForDB)));
        questionPointsLabel.setText(String.valueOf(newTest.getActiveQuestionPoints().get(0)+"   "));

        Timer timer = new Timer();

        Executor exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t ;
        });
        exec.execute(timer::run);
    }

    public class Timer implements Runnable{

        int testid =0;
        int testTime =30;
        int seconds = testTime * 60;

        @Override
        public void run() {

            while (seconds > 0) {
                Platform.runLater(() -> timerLabel.setText(seconds / 3600 + ":" + ((seconds / 60) % 60) + ":" + (seconds % 60)));
                try {
                    seconds--;
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                }
            }
        }
    }

    @FXML
    private void printAlternatives() {
        newTest.getActiveTest();
        showToStudentTestNameLabel.setText(newTest.getTestName());
        String typeOfQuestion = newTest.getActiveQuestionType().toString();
        ToggleGroup toggleGroup = new ToggleGroup();

            if (typeOfQuestion.equals("[Flervals]")) {
                for (int i = 0; i < newTest.getActiveAlternativeId().size(); i++) {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setId(String.valueOf(i));
                    Label alternativeText = new Label();
                    alternativeText.setText(String.valueOf(newTest.getActiveAlternativeText().get(i)));
                    alternativePane.add(checkBox, 0, i);
                    alternativePane.add(alternativeText, 1, i);
                }
            }else {
                for (int y = 0; y < newTest.getActiveAlternativeId().size(); y++) {
                    RadioButton radioButton = new RadioButton();
                    radioButton.setId(String.valueOf(y));
                    radioButton.setToggleGroup(toggleGroup);
                    Label alternativeText = new Label();
                    alternativeText.setText(String.valueOf(newTest.getActiveAlternativeText().get(y)));
                    alternativePane.add(radioButton, 0, y);
                    alternativePane.add(alternativeText, 1, y);
                }
            }
    }
    @FXML
    private void nextQuestion() {
        newTest.getActiveTest();
        activeQuestion++;
        activeQuestionsForDB++;
        newTest.getNextActiveQuestion();
        showToStudentTextLabel.setText("");
        alternativePane.getChildren().clear();
        showToStudentQuestionsLeft.setText(activeQuestion+"/"+newTest.getAmountOfQuestions()+"    ");
        showToStudentGrade.setText(newTest.getGradeOnActiveQuestion());
        questionPointsLabel.setText(String.valueOf(newTest.getActiveQuestionPoints().get(0)+"   "));
        if(activeQuestion==maxQuestions){
            showToUserNextButton.onActionProperty();
            showToUserNextButton.setOnAction(event -> {

            });
            showToStudentTextLabel.setText(String.valueOf(newTest.getActiveQuestionText().get(activeQuestionsForDB)));
            this.printAlternatives();
        }else{
            showToStudentTextLabel.setText(String.valueOf(newTest.getActiveQuestionText().get(activeQuestionsForDB)));
            this.printAlternatives();
        }
    }
    @FXML
    private void timer(){

    }
}

