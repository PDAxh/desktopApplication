package com.testverktyg.eclipselink.view.student;
import com.testverktyg.eclipselink.entity.Test;
import com.testverktyg.eclipselink.entity.UserTests;
import com.testverktyg.eclipselink.service.Test.ReadTest;
import com.testverktyg.eclipselink.service.studentAnswer.CreateStudentAnswer;
import com.testverktyg.eclipselink.service.studentAnswer.ReadStudentAnswer;
import com.testverktyg.eclipselink.service.userTests.ReadUserTests;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private int activeTest = 3;
    private int maxQuestions = 0;
    private int activeQuestion = 1;
    private int activeQuestionsForDB = 0;
    private ReadTest newTest = new ReadTest(activeTest);
    private CreateStudentAnswer csa = new CreateStudentAnswer();

    @FXML private BorderPane showStudentTestsBorderPane;
    @FXML private VBox showStudentTestVbox;
    @FXML private Button doSelectedTestButton;
    @FXML private Button showResultButton;
    private RadioButton setSelectTestToDo[];
    private int userId;
    private RadioButton alternativeRadioButtons[];
    private CheckBox alternativeCheckBox[];
    private boolean isFirstQuestion=true;

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

    //Loads next question student
    @FXML
    private void getNewQuestion() {
        if (newTest.getQuestionCount()!=0){
            setStudentAnswer();
        }
        if (isFirstQuestion){
            newTest.getActiveTest();
            isFirstQuestion=false;
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
                showToUserNextButton.setText("Lämna in prov");
                showToUserNextButton.onActionProperty();
                showToUserNextButton.setOnAction(event -> {
                    handInTest();
                });
                showToStudentTextLabel.setText(String.valueOf(newTest.getActiveQuestionText().get(activeQuestionsForDB)));
                printAlternatives();
                //stud showToUserNextButton.setDisable(true);
            } else {
                showToStudentTextLabel.setText(String.valueOf(newTest.getActiveQuestionText().get(activeQuestionsForDB)));
                printAlternatives();
            }
            activeQuestion++;
            activeQuestionsForDB++;
        System.out.println("ACTIVE QUESTION IS "+newTest.getQuestionCount());
    }

    //Prints question alternatives
    @FXML
    private void printAlternatives() {
        newTest.getActiveTest();
        System.out.println("ACTIVE QUESTION IS "+newTest.getQuestionCount());
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

    @FXML
    private void setStudentAnswer() {
        if (newTest.isSelfCorrecting()) {
            if (newTest.getActiveQuestionType().toString().equals("Flervals")) {
                for (int i = 0; i < newTest.getActiveAlternativeId().size(); i++) {
                    if (getAlternativeCheckBox()[i].isSelected()) {
                        int selectedAlternative = Integer.parseInt(getAlternativeCheckBox()[i].getId());
                        createAnswer(selectedAlternative);
                    }
                }
            }else {
                for (int i = 0; i < newTest.getActiveAlternativeId().size(); i++) {
                    if (getAlternativeRadioButtons()[i].isSelected()) {
                        int selectedAlternative = Integer.parseInt(getAlternativeRadioButtons()[i].getId());
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

        toggleGroup.selectedToggleProperty().addListener(event ->{
            if(toggleGroup.getSelectedToggle().isSelected()){
                int testId = Integer.parseInt(toggleGroup.getSelectedToggle().getUserData().toString());
                ReadStudentAnswer readStudentAnswer = new ReadStudentAnswer();
                readStudentAnswer.getStudentAnswerFromSpecificStudent(getUserId(), testId);
                getDoSelectedTestButton().setDisable(true);
                getShowResultButton().setDisable(true);

                for(Test test : readTest.getTestList()){
                    LocalDate localDate = LocalDate.now();
                    String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);
                    if((test.getTestId() == testId) && (test.getLastDate().equals(date))){
                        getDoSelectedTestButton().setDisable(false);
                        getShowResultButton().setDisable(true);
                    }
                    else if(!readStudentAnswer.getStudentAnswersList().isEmpty()){
                        getDoSelectedTestButton().setDisable(true);
                        getShowResultButton().setDisable(false);
                    }
                }
            }
        });
    }
    @FXML
    private void handInTest(){
        ReadStudentAnswer rsa = new ReadStudentAnswer();
        contentPane.getChildren().clear();
        contentPane.add(showToStudentTestNameLabel, 0, 0);
        Label resultLabel = new Label("Provets poäng:");
        contentPane.add(resultLabel, 0,1);

        HBox firstBox = new HBox();
        contentPane.add(firstBox, 0,2);
        Label VGQuestionLabel = new Label("VG Frågor: ");
        Label VGQuestionResultLabel = new Label();
        Label GQuestionLabel = new Label("G Frågor: ");
        Label GQuestionResultLabel = new Label();
        Label TotalLabel = new Label("Totalt: ");
        Label TotalResultLabel = new Label();
        firstBox.getChildren().addAll(
                VGQuestionLabel,
                VGQuestionResultLabel,
                GQuestionLabel,
                GQuestionResultLabel,
                TotalLabel,
                TotalResultLabel);

        HBox secondBox = new HBox();
        contentPane.add(secondBox, 0,3);
        Label VGQuestionPointsLabel = new Label("VG Poäng: ");
        Label VGQuestionPointsResultLabel = new Label();
        Label GQuestionPointsLabel = new Label("G Poäng: ");
        Label GQuestionPointsResultLabel = new Label();
        Label TotalPointsLabel = new Label("Totala Poäng ");
        Label TotalPointsResultLabel = new Label();
        secondBox.getChildren().addAll(
                VGQuestionPointsLabel,
                VGQuestionPointsResultLabel,
                GQuestionPointsLabel,
                GQuestionPointsResultLabel,
                TotalPointsLabel,
                TotalPointsResultLabel);

        VBox StudentResultBox = new VBox();
        contentPane.add(StudentResultBox,0,4);
        Label StudentPointsLabel = new Label("Ditt resultat:");
        Label StudentGPointsLabel = new Label("G poäng: ");
        Label StudentGPointsResultLabel = new Label();
        Label StudentVGPointsLabel = new Label("VG Poäng: ");
        Label StudentVGPointsResultLabel = new Label();
        Label GradeLabel = new Label("Betyg:");
        Label GradeResultLabel = new Label();
        Label StudentTotalPointsLabel = new Label("Poäng: ");
        Label StudentTotalPointsResultLabel = new Label();
        StudentResultBox.getChildren().addAll(
                StudentPointsLabel,
                StudentGPointsLabel,
                StudentGPointsResultLabel,
                StudentVGPointsLabel,
                StudentVGPointsResultLabel,
                GradeLabel,
                GradeResultLabel,
                StudentTotalPointsLabel,
                StudentTotalPointsResultLabel
                );
    }

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
}

