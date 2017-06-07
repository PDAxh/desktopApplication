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
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    @FXML private BorderPane showStudentTestsBorderPane;
    @FXML private VBox showStudentTestVbox;
    @FXML private Button doSelectedTestButton;
    @FXML private Button showResultButton;
    @FXML private HBox studentResultHbox;
    private ReadTest newTest;
    private CreateStudentAnswer csa = new CreateStudentAnswer();
    private RadioButton setSelectTestToDo[];
    private RadioButton alternativeRadioButtons[];
    private CheckBox alternativeCheckBox[];
    private int activeTest;
    private int maxQuestions = 0;
    private int activeQuestion = 1;
    private int activeQuestionsForDB = 0;
    private int userId;
    private int seconds;

    //Setup test scene and show first question
    @FXML
    private void startTest() {
        contentPane.getChildren().removeAll(
                showToUserStartTestButton,
                showToStudentTimeTextLabel,
                showToStudentTimeLabel,
                showToStudentTeacherTextLabel,
                showToStudentTeacherLabel,
                showToStudentClassTextLabel,
                showToStudentClassLabel);
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
        seconds = getNewTest().getTestTimeInMinutes() * 60;

        Runnable runnable = () -> {
            while (seconds > 0) {
                Platform.runLater(() -> timerLabel.setText(seconds / 3600 + ":" + ((seconds / 60) % 60) + ":" + (seconds % 60)));
                try {
                    seconds--;
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
            Platform.runLater(this::handInTest);
        };

        Executor exec = Executors.newCachedThreadPool(runnable1 -> {
            Thread t = new Thread(runnable1);
            t.setDaemon(true);
            return t;
        });
        exec.execute(runnable);
    }

    //Loads next question
    @FXML
    private void getNewQuestion() {
        if (activeQuestion == 1){
            newTest.getActiveTest();
        }else{
            createAnswer();
            newTest.getNextActiveQuestion();
        }
        showToStudentQuestionsLeft.setText(activeQuestion + "/" + newTest.getAmountOfQuestions() + "    ");
        showToStudentGrade.setText(newTest.getGradeOnActiveQuestion());
        questionPointsLabel.setText(String.valueOf(newTest.getActiveQuestionPoints().get(0) + "   "));
        showToStudentTextLabel.setText("");
        alternativePane.getChildren().clear();
        if (activeQuestion == maxQuestions) {
            showToUserNextButton.onActionProperty();
            showToUserNextButton.setOnAction(null);
            showToUserNextButton.setOnAction(event -> {
                createAnswer();
                handInTest();
            });
            showToStudentTextLabel.setText(String.valueOf(newTest.getActiveQuestionText().get(activeQuestionsForDB)));
            this.printAlternatives();
            showToUserNextButton.setText("Lämna in");
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
        getNewTest().getActiveTest();
        showToStudentTestNameLabel.setText(getNewTest().getTestName());
        String typeOfQuestion = getNewTest().getActiveQuestionType().toString();
        System.out.println(typeOfQuestion);
        if (typeOfQuestion.equals("[Flervals]")) {
            setAlternativeCheckBox(new CheckBox[getNewTest().getActiveAlternativeId().size()]);
            for (int i = 0; i < getNewTest().getActiveAlternativeId().size(); i++) {
                getAlternativeCheckBox()[i] = new CheckBox();
                getAlternativeCheckBox()[i].setUserData(String.valueOf(getNewTest().getActiveAlternativeId().get(i)));
                getAlternativePane().add(getAlternativeCheckBox()[i], 0, i);
                getAlternativePane().add(new Label(String.valueOf(getNewTest().getActiveAlternativeText().get(i))), 1, i);
            }
        } else if(typeOfQuestion.equals("[Alternativ]")){
            setAlternativeRadioButtons(new RadioButton[getNewTest().getActiveAlternativeId().size()]);
            ToggleGroup toggleGroup = new ToggleGroup();
            for (int y = 0; y < getNewTest().getActiveAlternativeId().size(); y++) {
                getAlternativeRadioButtons()[y] = new RadioButton();
                getAlternativeRadioButtons()[y].setUserData(String.valueOf(getNewTest().getActiveAlternativeId().get(y)));
                getAlternativeRadioButtons()[y].setToggleGroup(toggleGroup);
                getAlternativePane().add(getAlternativeRadioButtons()[y], 0, y);
                getAlternativePane().add(new Label(String.valueOf(getNewTest().getActiveAlternativeText().get(y))), 1, y);
            }
        }
    }

    private void createAnswer() {
        int currentQuestionId = Integer.parseInt(String.valueOf(newTest.getActiveQuestionId().get(newTest.getQuestionCount())));
        int selectedAlternative;
        if(getNewTest().getActiveQuestionType().toString().equals("[Flervals]")){
            for(int i = 0; i < getAlternativeCheckBox().length; i++){
                if(getAlternativeCheckBox()[i].isSelected()){
                    selectedAlternative = Integer.parseInt(getAlternativeCheckBox()[i].getUserData().toString());
                    csa.createNewStudentAnswer(activeTest, currentQuestionId, selectedAlternative, getUserId());
                }
            }
        }else if(getNewTest().getActiveQuestionType().toString().equals("[Alternativ]")){
            for(int i = 0; i < getAlternativeRadioButtons().length; i++){
                if(getAlternativeRadioButtons()[i].isSelected()){
                    selectedAlternative = Integer.parseInt(getAlternativeRadioButtons()[i].getUserData().toString());
                    csa.createNewStudentAnswer(activeTest, currentQuestionId, selectedAlternative, getUserId());
                }
            }
        }
    }
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
                    if((test.getTestId() == testId) && (test.getLastDate().equals(date))&& (readStudentAnswer.getStudentAnswersList().isEmpty())){
                        getDoSelectedTestButton().setDisable(false);
                        getShowResultButton().setDisable(true);
                        break;
                    }
                    else if(!readStudentAnswer.getStudentAnswersList().isEmpty() && (test.isSeeResultAfter())){
                        getDoSelectedTestButton().setDisable(true);
                        getShowResultButton().setDisable(false);
                        break;
                    }
                }
            }
        });
    }

    @FXML
    private void handInTest(){
        if(newTest.isSeeResult()){
        ReadStudentAnswer rsa = new ReadStudentAnswer();
        contentPane.getChildren().clear();
        contentPane.add(showToStudentTestNameLabel, 0, 0);
        Label resultLabel = new Label("Provets poäng:");
        contentPane.add(resultLabel, 0,1);
        HBox secondBox = new HBox();
        contentPane.add(secondBox, 0,3);
        HBox VGBox = new HBox();
        HBox GBox = new HBox();
        HBox TotBox = new HBox();
        Label VGQuestionPointsLabel = new Label("VG Poäng: ");
        Label VGQuestionPointsResultLabel = new Label();
        Label GQuestionPointsLabel = new Label("G Poäng: ");
        Label GQuestionPointsResultLabel = new Label();
        Label TotalPointsLabel = new Label("Totala Poäng ");
        Label TotalPointsResultLabel = new Label();
            resultLabel.setFont(Font.font("DEFAULT", FontWeight.BOLD, 12));
            GQuestionPointsLabel.setFont(Font.font("DEFAULT", FontWeight.BOLD, 11));
            VGQuestionPointsLabel.setFont(Font.font("DEFAULT", FontWeight.BOLD, 11));
            TotalPointsLabel.setFont(Font.font("DEFAULT", FontWeight.BOLD, 11));
        GBox.getChildren().addAll(
                GQuestionPointsLabel,
                GQuestionPointsResultLabel
        );
        VGBox.getChildren().addAll(
                VGQuestionPointsLabel,
                VGQuestionPointsResultLabel
        );
        TotBox.getChildren().addAll(
                TotalPointsLabel,
                TotalPointsResultLabel
        );
        secondBox.getChildren().addAll(
                GBox,
                VGBox,
                TotBox);
        secondBox.setSpacing(10);
        secondBox.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        secondBox.setPadding(new Insets(10, 10, 10, 10));
        VBox StudentResultBox = new VBox();
        HBox GpointsBox = new HBox();
        HBox VGpointsBox = new HBox();
        HBox gradeBox = new HBox();
        Label StudentPointsLabel = new Label("Ditt resultat:");
        contentPane.add(StudentPointsLabel,0,4);
        HBox TotpointsBox = new HBox();
        contentPane.add(StudentResultBox,0,5);
        Label StudentGPointsLabel = new Label("G poäng: ");
        Label StudentGPointsResultLabel = new Label();
        Label StudentVGPointsLabel = new Label("VG Poäng: ");
        Label StudentVGPointsResultLabel = new Label();
        Label GradeLabel = new Label("Betyg: ");
        Label GradeResultLabel = new Label();
        Label StudentTotalPointsLabel = new Label("Poäng: ");
        Label StudentTotalPointsResultLabel = new Label();

        StudentPointsLabel.setFont(Font.font("DEFAULT", FontWeight.BOLD, 12));
        StudentGPointsLabel.setFont(Font.font("DEFAULT", FontWeight.BOLD, 11));
        StudentVGPointsLabel.setFont(Font.font("DEFAULT", FontWeight.BOLD, 11));
        GradeLabel.setFont(Font.font("DEFAULT", FontWeight.BOLD, 11));
        StudentTotalPointsLabel.setFont(Font.font("DEFAULT", FontWeight.BOLD, 11));

        GpointsBox.getChildren().addAll(
                StudentGPointsLabel,
                StudentGPointsResultLabel
        );
        VGpointsBox.getChildren().addAll(
                StudentVGPointsLabel,
                StudentVGPointsResultLabel
        );
        gradeBox.getChildren().addAll(
                GradeLabel,
                GradeResultLabel
        );
        TotpointsBox.getChildren().addAll(
                StudentTotalPointsLabel,
                StudentTotalPointsResultLabel
        );
        StudentResultBox.getChildren().addAll(
                GpointsBox,
                VGpointsBox,
                gradeBox,
                TotpointsBox
        );

        StudentResultBox.setSpacing(10);
        StudentResultBox.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        StudentResultBox.setPadding(new Insets(10, 10, 10, 10));
        Button studentPrintResultButton = new Button();
        studentPrintResultButton.setText("Spara som PDF");
        contentPane.add(studentPrintResultButton,0,6);
        studentPrintResultButton.setOnAction(null);
        studentPrintResultButton.setOnAction(event -> {

        });


        rsa.getStudentAnswerFromSpecificStudent(getUserId(),activeTest);
        rsa.getCorrectAnswers(activeTest);
        int maxG=rsa.getMaxPointsG();
        int maxVG=rsa.getMaxPointsVG();
        int stuG=rsa.getStudPointsG();
        int stuVG=rsa.getStudPointsVG();
        int gGrade = (stuG*100)/maxG;
        int vgGrade = (stuVG*100)/maxVG;
            System.out.println(gGrade);
            System.out.println(vgGrade);
        VGQuestionPointsResultLabel.setText(String.valueOf(maxVG));
        GQuestionPointsResultLabel.setText(String.valueOf(maxG));
        TotalPointsResultLabel.setText(String.valueOf(maxG+maxVG));
        StudentGPointsResultLabel.setText(String.valueOf(stuG));
        StudentVGPointsResultLabel.setText(String.valueOf(stuVG));
        StudentTotalPointsResultLabel.setText(String.valueOf(stuG+stuVG));
        if(gGrade>=60){
            if(vgGrade>=60){
                GradeResultLabel.setTextFill(Color.GREEN);
                GradeResultLabel.setText("VG");
            }else{
                GradeResultLabel.setTextFill(Color.GREEN);
                GradeResultLabel.setText("G");
            }
        }else{
            GradeResultLabel.setTextFill(Color.RED);
            GradeResultLabel.setText("IG");
        }
        }else{
            Label thankYouLabel = new Label("Tack för att du deltog\nProvet kommer att rättas av din lärare.");
            contentPane.getChildren().clear();
            contentPane.add(thankYouLabel, 0,0);
        }
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
        maxQuestions = newTest.getAmountOfQuestions();
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

    @FXML
    private void getShowResultForTest(){
        int testId = Integer.parseInt(getStudentTestToggleGroup().getSelectedToggle().getUserData().toString());
        int gGrade = 0;
        int vgGrade = 0;
        String grade;
        ReadStudentAnswer readStudentAnswer = new ReadStudentAnswer();
        VBox vBox = new VBox();
        readStudentAnswer.getStudentAnswerFromSpecificStudent(getUserId(), testId);
        readStudentAnswer.getCorrectAnswers(testId);
        if(!(readStudentAnswer.getMaxPointsG() == 0)){
            gGrade = (readStudentAnswer.getStudPointsG()*100)/readStudentAnswer.getMaxPointsG();
        }
        if(!(readStudentAnswer.getMaxPointsVG() == 0)){
            vgGrade = (readStudentAnswer.getStudPointsVG()*100)/readStudentAnswer.getMaxPointsVG();
        }
        if(gGrade>=60){
            if(vgGrade>=60){
                grade = "VG";
            }else{
                grade = "G";
            }
        }else{
            grade = "IG";
        }

        vBox.getChildren().add(new Label("Ditt resultat:"));
        vBox.getChildren().add(new Label("G poäng: :" + readStudentAnswer.getStudPointsG() + " / " + readStudentAnswer.getMaxPointsG()));
        vBox.getChildren().add(new Label("VG poäng: :" + readStudentAnswer.getStudPointsVG() + " / " + readStudentAnswer.getMaxPointsVG()));
        vBox.getChildren().add(new Label("Betyg: :" + grade));
        vBox.getChildren().add(new Label("Poäng: " + (readStudentAnswer.getStudPointsG() + readStudentAnswer.getStudPointsVG()) + " / "
                + (readStudentAnswer.getMaxPointsG() + readStudentAnswer.getMaxPointsVG())));
        getStudentResultHbox().getChildren().add(vBox);
        getStudentTestToggleGroup().selectedToggleProperty().addListener(event -> getStudentResultHbox().getChildren().clear());
    }

    private HBox getStudentResultHbox() {
        return studentResultHbox;
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

    private GridPane getAlternativePane() {
        return alternativePane;
    }
}