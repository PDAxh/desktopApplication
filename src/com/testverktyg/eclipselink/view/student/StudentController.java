package com.testverktyg.eclipselink.view.student;
import com.testverktyg.eclipselink.entity.Test;
import com.testverktyg.eclipselink.entity.User;
import com.testverktyg.eclipselink.entity.UserTests;
import com.testverktyg.eclipselink.service.Test.ReadTest;
import com.testverktyg.eclipselink.service.studentAnswer.CreateStudentAnswer;
import com.testverktyg.eclipselink.service.studentAnswer.ReadStudentAnswer;
import com.testverktyg.eclipselink.service.user.ReadUser;
import com.testverktyg.eclipselink.service.userTests.ReadUserTests;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Andreas.
 */
public class StudentController {
    @FXML private Button showToUserStartTestButton;
    @FXML private Button showToUserNextButton;
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
    @FXML private Label showToStudentQuestionsLeft;
    @FXML private Label showToStudentQuestionsLeftText;
    @FXML private Label showToStudentGrade;
    @FXML private Label showToStudentGradeText;
    @FXML private Label questionPointsLabel;
    @FXML private Label questionPointsTextLabel;
    @FXML private Label timerLabel;
    @FXML private Label timeTextLabel;
    private int activeTest;
    private int maxQuestions = 0;
    private int activeQuestion = 1;
    private int activeQuestionsForDB = 0;
    private ReadTest newTest;// = new ReadTest(activeTest);
    private CreateStudentAnswer csa = new CreateStudentAnswer();

    @FXML private BorderPane showStudentTestsBorderPane;
    @FXML private VBox showStudentTestVbox;
    @FXML private Button doSelectedTestButton;
    @FXML private Button showResultButton;
    private RadioButton setSelectTestToDo[];
    private int userId;
    private RadioButton alternativeRadioButtons[];
    private CheckBox alternativeCheckBox[];

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
        timer.setTestTime(getNewTest().getTestTimeInMinutes());
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
        int testTime;
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

        private int getTestTime() {
            return testTime;
        }

        public void setTestTime(int testTime) {
            this.testTime = testTime;
        }
    }

    //Loads next question
    @FXML
    private void getNewQuestion() {
        if (newTest.getQuestionCount() == 0){
            newTest.getActiveTest();
        }else {
            newTest.getNextActiveQuestion();
        }
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
                showToUserNextButton.setDisable(true);
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
            setAlternativeCheckBox(new CheckBox[newTest.getActiveAlternativeId().size()]);
            for (int i = 0; i < newTest.getActiveAlternativeId().size(); i++) {
                getAlternativeCheckBox()[i] = new CheckBox();
                getAlternativeCheckBox()[i].setId(String.valueOf(i));
                getAlternativeCheckBox()[i].setUserData(newTest.getActiveAlternativeId().get(i).getAlternativeId());
                alternativePane.add(getAlternativeCheckBox()[i], 0, i);
                alternativePane.add(new Label(String.valueOf(newTest.getActiveAlternativeText().get(i))), 1, i);
            }
        } else {
            setAlternativeRadioButtons(new RadioButton[newTest.getActiveAlternativeId().size()]);
            for (int y = 0; y < newTest.getActiveAlternativeId().size(); y++) {
                ToggleGroup toggleGroup = new ToggleGroup();
                getAlternativeRadioButtons()[y] = new RadioButton();
                getAlternativeRadioButtons()[y].setId(String.valueOf(y));
                getAlternativeRadioButtons()[y].setToggleGroup(toggleGroup);
                alternativePane.add(getAlternativeRadioButtons()[y], 0, y);
                alternativePane.add(new Label(String.valueOf(newTest.getActiveAlternativeText().get(y))), 1, y);
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

    //----------------------
    private ToggleGroup studentTestToggleGroup;

    public void getStudentTests(){
        getShowStudentTestVbox().getChildren().clear();
        ReadTest readTest = new ReadTest();
        ReadUserTests readUserTests = new ReadUserTests(getUserId());
        setStudentTestToggleGroup(new ToggleGroup());
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
                getSetSelectTestToDo()[counter].setToggleGroup(getStudentTestToggleGroup());
                getSetSelectTestToDo()[counter].setUserData(String.valueOf(test.getTestId()));
                hBoxLeft.getChildren().addAll(new Label("Prov: " + test.getTestName()), new Label(" Beskrivning: " + test.getTestDescription()),
                        new Label(" Datum: " + test.getLastDate()), new Label(" Tid: " + String.valueOf(test.getTimeForTestMinutes())));
                hBoxRight.getChildren().addAll( new Label("Välj:"), getSetSelectTestToDo()[counter]);

                borderPane.setStyle("-fx-border-color: black;");
                borderPane.setPadding(new Insets(10));
                borderPane.setLeft(hBoxLeft);
                borderPane.setRight(hBoxRight);
                getShowStudentTestVbox().getChildren().add(borderPane);
                counter++;
            }
        }

        getStudentTestToggleGroup().selectedToggleProperty().addListener(event ->{
            if(getStudentTestToggleGroup().getSelectedToggle().isSelected()){
                int testId = Integer.parseInt(getStudentTestToggleGroup().getSelectedToggle().getUserData().toString());
                readTest.getTest(testId);
                LocalDate localDate = LocalDate.now();
                String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);
                ReadStudentAnswer readStudentAnswer = new ReadStudentAnswer();
                readStudentAnswer.getStudentAnswerFromSpecificStudent(getUserId(), testId);
                getDoSelectedTestButton().setDisable(true);
                getShowResultButton().setDisable(true);

                for(Test test : readTest.getTestList()){
                    if((test.getTestId() == testId) && (test.getLastDate().equals(date))){
                        getDoSelectedTestButton().setDisable(false);
                        getShowResultButton().setDisable(true);
                        break;
                    }
                    else if(!readStudentAnswer.getStudentAnswersList().isEmpty()){
                        getDoSelectedTestButton().setDisable(true);
                        getShowResultButton().setDisable(false);
                        break;
                    }
                }
            }
        });
    }

    @FXML
    private void getSelectedTestToDo() throws IOException{
        ReadUser readUser = new ReadUser();
        ReadUserTests readUserTests = new ReadUserTests();
        readUser.readOnlyTeacher();
        readUser.getFindClassWithUserId(getUserId());
        setActiveTest(Integer.parseInt(getStudentTestToggleGroup().getSelectedToggle().getUserData().toString()));
        readUserTests.findAllUsersForOneTest(getActiveTest());
        setNewTest(new ReadTest(getActiveTest()));
        getNewTest().getActiveTest();
        getShowStudentTestsBorderPane().setLeft(null);
        getShowStudentTestsBorderPane().setTop(null);
        getContentPane().setVisible(true);

        getShowToStudentTestNameLabel().setText(getNewTest().getTestName());
        getShowToStudentTextLabel().setText(getNewTest().getTestDescription());
        getShowToStudentTimeLabel().setText(String.valueOf(getNewTest().getTestTimeInMinutes()));

        for(User user : readUser.getTeacherList()){
            for(UserTests userTests: readUserTests.getUserTestListByTestId()){
                if(user.getUserId() == userTests.getUserId()){
                    getShowToStudentTeacherLabel().setText(user.getFirstname() + " " + user.getLastname());
                    break;
                }
            }
        }

        getShowToStudentClassLabel().setText(readUser.getFindClassWithUserIdList().get(0).getKlass());
    }

    private ToggleGroup getStudentTestToggleGroup() {
        return studentTestToggleGroup;
    }

    private void setStudentTestToggleGroup(ToggleGroup studentTestToggleGroup) {
        this.studentTestToggleGroup = studentTestToggleGroup;
    }

    private VBox getShowStudentTestVbox() {
        return showStudentTestVbox;
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

    private BorderPane getShowStudentTestsBorderPane() {
        return showStudentTestsBorderPane;
    }

    private RadioButton[] getAlternativeRadioButtons() {
        return alternativeRadioButtons;
    }

    private void setAlternativeRadioButtons(RadioButton[] alternativeRadioButtons) {
        this.alternativeRadioButtons = alternativeRadioButtons;
    }

    private CheckBox[] getAlternativeCheckBox() {
        return alternativeCheckBox;
    }

    private void setAlternativeCheckBox(CheckBox[] alternativeCheckBox) {
        this.alternativeCheckBox = alternativeCheckBox;
    }

    private int getActiveTest() {
        return activeTest;
    }

    private void setActiveTest(int activeTest) {
        this.activeTest = activeTest;
    }

    private Label getShowToStudentTextLabel() {
        return showToStudentTextLabel;
    }

    private void setShowToStudentTextLabel(Label showToStudentTextLabel) {
        this.showToStudentTextLabel = showToStudentTextLabel;
    }

    private Label getShowToStudentTestNameLabel() {
        return showToStudentTestNameLabel;
    }

    private void setShowToStudentTestNameLabel(Label showToStudentTestNameLabel) {
        this.showToStudentTestNameLabel = showToStudentTestNameLabel;
    }

    private GridPane getContentPane() {
        return contentPane;
    }

    private void setContentPane(GridPane contentPane) {
        this.contentPane = contentPane;
    }

    private ReadTest getNewTest() {
        return newTest;
    }

    private void setNewTest(ReadTest newTest) {
        this.newTest = newTest;
    }

    private Label getShowToStudentTimeLabel() {
        return showToStudentTimeLabel;
    }

    private void setShowToStudentTimeLabel(Label showToStudentTimeLabel) {
        this.showToStudentTimeLabel = showToStudentTimeLabel;
    }

    private Label getShowToStudentTeacherLabel() {
        return showToStudentTeacherLabel;
    }

    private void setShowToStudentTeacherLabel(Label showToStudentTeacherLabel) {
        this.showToStudentTeacherLabel = showToStudentTeacherLabel;
    }

    private Label getShowToStudentClassLabel() {
        return showToStudentClassLabel;
    }

    private void setShowToStudentClassLabel(Label showToStudentClassLabel) {
        this.showToStudentClassLabel = showToStudentClassLabel;
    }
}

