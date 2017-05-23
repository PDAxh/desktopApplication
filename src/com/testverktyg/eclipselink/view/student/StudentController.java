package com.testverktyg.eclipselink.view.student;
import com.testverktyg.eclipselink.entity.Test;
import com.testverktyg.eclipselink.entity.UserTests;
import com.testverktyg.eclipselink.service.Test.ReadTest;
import com.testverktyg.eclipselink.service.studentAnswer.CreateStudentAnswer;
import com.testverktyg.eclipselink.service.userTests.ReadUserTests;
import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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


    @FXML
    private SimpleListProperty<CheckBox> alternativeCheckBoxList = new SimpleListProperty<>(this, "alternativeCheckBoxList");
    @FXML
    private SimpleListProperty<RadioButton> alternativeRadioButtonList = new SimpleListProperty<>(this, "alternativeRadioButtonList");

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
        alternativeCheckBoxList.clear();
        alternativeRadioButtonList.clear();


        ToggleGroup toggleGroup = new ToggleGroup();

        if (typeOfQuestion.equals("Flervals")) {
            for (int i = 0; i < newTest.getActiveAlternativeId().size(); i++) {
                CheckBox checkBox = new CheckBox();
                checkBox.setId(String.valueOf(i));
                Label alternativeText = new Label();
                alternativeText.setText(String.valueOf(newTest.getActiveAlternativeText().get(i)));
                alternativePane.add(checkBox, 0, i);
                alternativePane.add(alternativeText, 1, i);
                alternativeCheckBoxList.add(checkBox);
            }
        } else {
            for (int y = 0; y < newTest.getActiveAlternativeId().size(); y++) {
                RadioButton radioButton = new RadioButton();
                radioButton.setId(String.valueOf(y));
                radioButton.setToggleGroup(toggleGroup);
                Label alternativeText = new Label();
                alternativeText.setText(String.valueOf(newTest.getActiveAlternativeText().get(y)));
                alternativePane.add(radioButton, 0, y);
                alternativePane.add(alternativeText, 1, y);
                alternativeRadioButtonList.add(radioButton);
            }
        }
    }

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

    private void createAnswer(int selectedAlternative) {
        int selectedAlternativeId = newTest.getActiveAlternativeId().get(selectedAlternative).getAlternativeId();
        int currentQuestionId = newTest.getActiveQuestionId().get(activeQuestion).getQuestionId();
        csa.createNewStudentAnswer(activeTest, currentQuestionId, selectedAlternativeId, 3);
    }

    //--Testkod-Jonas---

    @FXML private VBox showStudentTestVbox;
    @FXML private Button doSelectedTestButton;
    @FXML private Button showResultButton;
    private RadioButton setSelectTestToDo[];
    private int userId;

    private VBox getShowStudentTestVbox() {
        return showStudentTestVbox;
    }

    private void setShowStudentTestVbox(VBox showStudentTestVbox) {
        this.showStudentTestVbox = showStudentTestVbox;
    }

    private Button getDoSelectedTestButton() {
        return doSelectedTestButton;
    }

    private Button getShowResultButton() {
        return showResultButton;
    }

    private RadioButton[] getSetSelectTestToDo() {
        return setSelectTestToDo;
    }

    private void setSetSelectTestToDo(RadioButton[] setSelectTestToDo) {
        this.setSelectTestToDo = setSelectTestToDo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void getStudentTests(){
        getShowStudentTestVbox().getChildren().clear();
        ReadTest readTest = new ReadTest();
        ReadUserTests readUserTests = new ReadUserTests(getUserId());
        ToggleGroup toggleGroup = new ToggleGroup();
        setSetSelectTestToDo(new RadioButton[readUserTests.getUserTestsList().size()]);
        int counter = 0;
        for(UserTests userTests: readUserTests.getUserTestsList()){

            readTest.getTest(userTests.getTestId());
            for(Test test : readTest.getTestList()){
                HBox hBoxLeft = new HBox();
                HBox hBoxRight = new HBox();
                BorderPane borderPane = new BorderPane();
                hBoxLeft.setSpacing(50.0);
                getSetSelectTestToDo()[counter] = new RadioButton();
                getSetSelectTestToDo()[counter].setToggleGroup(toggleGroup);
                getSetSelectTestToDo()[counter].setId(String.valueOf(test.getTestId()));
                hBoxLeft.getChildren().addAll(new Label("Prov: " + test.getTestName()), new Label(" Beskrivning: " + test.getTestDescription()),
                        new Label(" Datum: " + test.getLastDate()), new Label(" Tid: " + String.valueOf(test.getTimeForTestMinutes())));

                LocalDate localDate = LocalDate.now();
                String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);
                if(test.getLastDate().equals(date)){
                    hBoxRight.getChildren().addAll( new Label("Välj:"), getSetSelectTestToDo()[counter]);
                }

                borderPane.setStyle("-fx-border-color: black;");
                borderPane.setPadding(new Insets(10));
                borderPane.setLeft(hBoxLeft);
                borderPane.setRight(hBoxRight);
                getShowStudentTestVbox().getChildren().add(borderPane);
                counter++;
            }
        }
    }

    @FXML
    private void getSelectedTestToDo(){

    }

    //--------
}

