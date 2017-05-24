package com.testverktyg.eclipselink.view.student;
import com.testverktyg.eclipselink.service.Test.ReadTest;
import com.testverktyg.eclipselink.service.studentAnswer.CreateStudentAnswer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Andreas.
 */
public class StudentController {
    @FXML private GridPane alternativePane;
    @FXML private GridPane contentPane;
    @FXML private Label showToStudentTestNameLabel;
    @FXML private Label showToStudentTextLabel;
    @FXML private Label showToStudentTimeTextLabel;
    @FXML private Label showToStudentTimeLabel;
    @FXML private Label showToStudentTeacherTextLabel;
    @FXML private Label showToStudentTeacherLabel;
    @FXML private Label showToStudentClassTextLabel;
    @FXML private Label showToStudentClassLabel;
    @FXML private Button showToUserStartTestButton;
    @FXML private Button showToUserNextButton;
    @FXML private Label showToStudentQuestionsLeft;
    @FXML private Label showToStudentQuestionsLeftText;
    @FXML private Label showToStudentGrade;
    @FXML private Label showToStudentGradeText;
    @FXML private Label questionPointsLabel;
    @FXML private Label questionPointsTextLabel;
    @FXML private Label timerLabel;
    @FXML private Label timeTextLabel;
    private int activeTest = 6;
    private int maxQuestions = 0;
    private int activeQuestion = 0;
    private int activeQuestionsForDB = 0;
    private ReadTest newTest = new ReadTest(activeTest);
    private CreateStudentAnswer csa = new CreateStudentAnswer();

    @FXML private ArrayList<CheckBox> checkBoxArray;
    @FXML private ArrayList<RadioButton> radioButtonArray;

    /*@FXML
    private SimpleListProperty<CheckBox> alternativeCheckBoxList = new SimpleListProperty<>(this, "alternativeCheckBoxList");
    @FXML
    private SimpleListProperty<RadioButton> alternativeRadioButtonList = new SimpleListProperty<>(this, "alternativeRadioButtonList");*/

    //Loads test information for test info scene
    @FXML
    private void getTest() {
        newTest.getActiveTest();
        maxQuestions = newTest.getAmountOfQuestions();
        showToStudentTestNameLabel.setText(newTest.getTestName());
        showToStudentTextLabel.setText(newTest.getTestDescription());
        showToStudentTimeLabel.setText(String.valueOf(newTest.getTestTimeInMinutes()));
    }

    //Setup test scene and show first question
    @FXML
    private void startTest() {
        contentPane.getChildren().removeAll(showToUserStartTestButton, showToStudentTimeTextLabel, showToStudentTimeLabel, showToStudentTeacherTextLabel, showToStudentTeacherLabel, showToStudentClassTextLabel, showToStudentClassLabel);
        showToStudentQuestionsLeftText.setVisible(true);
        showToStudentQuestionsLeft.setVisible(true);
        showToStudentGradeText.setVisible(true);
        showToStudentGrade.setVisible(true);
        timeTextLabel.setVisible(true);
        timerLabel.setVisible(true);
        questionPointsTextLabel.setVisible(true);
        questionPointsLabel.setVisible(true);
        showToUserNextButton.setVisible(true);
        getNewQuestion();

        Timer timer = new Timer();
        Executor exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });
        exec.execute(timer);
    }

    //Test timer
    public class Timer implements Runnable {
        int testid = 0;
        int testTime = 30;
        int seconds = testTime * 60;

        @Override
        public void run() {
            while (seconds > 0) {
                Platform.runLater(() -> timerLabel.setText(seconds / 3600 + ":" + ((seconds / 60) % 60) + ":" + (seconds % 60)));
                try {
                    seconds--;
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    //Loads next question
    @FXML
    private void getNewQuestion() {
        newTest.getActiveTest();
        newTest.getNextActiveQuestion();
        showToStudentQuestionsLeft.setText(activeQuestion + "/" + newTest.getAmountOfQuestions() + "    ");
        showToStudentGrade.setText(newTest.getGradeOnActiveQuestion());
        questionPointsLabel.setText(String.valueOf(newTest.getActiveQuestionPoints().get(0) + "   "));
        showToStudentTextLabel.setText(String.valueOf(newTest.getActiveQuestionText().get(activeQuestionsForDB)));
        showToStudentTextLabel.setText("");
        alternativePane.getChildren().clear();
        //checkBoxArray.clear();
        //radioButtonArray.clear();
        if (activeQuestion == maxQuestions) {
            showToUserNextButton.onActionProperty();
            showToUserNextButton.setOnAction(event -> {
            });
            showToStudentTextLabel.setText(String.valueOf(newTest.getActiveQuestionText().get(activeQuestionsForDB)));
            this.printAlternatives();
        } else {
            showToStudentTextLabel.setText(String.valueOf(newTest.getActiveQuestionText().get(activeQuestionsForDB)));
            this.printAlternatives();
        }
        activeQuestion++;
        activeQuestionsForDB++;
    }

    //Prints question alternatives
    @FXML
    private void printAlternatives() {
        newTest.getActiveTest();
        showToStudentTestNameLabel.setText(newTest.getTestName());
        String typeOfQuestion = newTest.getActiveQuestionType().toString();
        if (typeOfQuestion.equals("Flervals")) {
            for (int i = 0; i < newTest.getActiveAlternativeId().size(); i++) {
                CheckBox checkBox = new CheckBox();
                checkBox.setId(String.valueOf(i));
                checkBox.setUserData(newTest.getActiveAlternativeId().get(i).getAlternativeId());
                Label alternativeText = new Label();
                alternativeText.setText(String.valueOf(newTest.getActiveAlternativeText().get(i)));
                alternativePane.add(checkBox, 0, i);
                alternativePane.add(alternativeText, 1, i);
                checkBoxArray.add(checkBox);
            }
        } else {
            for (int y = 0; y < newTest.getActiveAlternativeId().size(); y++) {
                RadioButton radioButton = new RadioButton();
                ToggleGroup toggleGroup = new ToggleGroup();
                radioButton.setId(String.valueOf(y));
                radioButton.setToggleGroup(toggleGroup);
                Label alternativeText = new Label();
                alternativeText.setText(String.valueOf(newTest.getActiveAlternativeText().get(y)));
                alternativePane.add(radioButton, 0, y);
                alternativePane.add(alternativeText, 1, y);
                radioButtonArray.add(radioButton);
            }
        }
    }
/*
    @FXML
    private void addStudentAnswer() {
        if (newTest.isSelfCorrecting()) {
            if (newTest.getActiveQuestionType().toString().equals("Flervals")) {
                for (int i = 0; i < alternativeCheckBoxList.get().size(); i++) {
                    if (alternativeCheckBoxList.get().get(i).isSelected()) {
                        int selectedAlternative = Integer.parseInt(alternativeCheckBoxList.get().get(i).getId());
                        createAnswer(selectedAlternative);
                    }
                }
            }else {
                for (int i = 0; i < alternativeRadioButtonList.get().size(); i++) {
                    if (alternativeRadioButtonList.get().get(i).isSelected()) {
                        int selectedAlternative = Integer.parseInt(alternativeRadioButtonList.get().get(i).getId());
                        createAnswer(selectedAlternative);
                    }
                }
            }
        }
    }
*/
    private void createAnswer(int selectedAlternative) {
        int selectedAlternativeId = newTest.getActiveAlternativeId().get(selectedAlternative).getAlternativeId();
        int currentQuestionId = newTest.getActiveQuestionId().get(activeQuestion).getQuestionId();
        csa.createNewStudentAnswer(activeTest, currentQuestionId, selectedAlternativeId, 3);
    }
}

