package com.testverktyg.eclipselink.view.teacher;

import com.testverktyg.eclipselink.entity.Test;
import com.testverktyg.eclipselink.entity.UserTests;
import com.testverktyg.eclipselink.service.Test.CreateTest;
import com.testverktyg.eclipselink.service.Test.DeleteTest;
import com.testverktyg.eclipselink.service.Test.ReadTest;
import com.testverktyg.eclipselink.service.Test.UpdateTest;
import com.testverktyg.eclipselink.service.userTests.CreateUserTests;
import com.testverktyg.eclipselink.service.userTests.DeleteUserTests;
import com.testverktyg.eclipselink.service.userTests.ReadUserTests;
import com.testverktyg.eclipselink.view.teacher.createTest.NewAlternativ;
import com.testverktyg.eclipselink.view.teacher.createTest.NewQuestion;
import com.testverktyg.eclipselink.view.teacher.createTest.NewTest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import java.io.IOException;

/**
 * Created by Jonas Johansson, Java2 on 2017-05-01.
 * Gui för att skapa ett test.
 */
public class TeacherController {

    @FXML private BorderPane borderPaneAlternatives;
    @FXML private Button deleteQuestionButton;
    @FXML private Button updateQuestionButton;
    @FXML private Button addQuestionButton;
    @FXML private CheckBox selfCorrect;
    @FXML private CheckBox showResultsToStudent;
    @FXML private ComboBox<Integer> numberOfAlternatives;
    @FXML private ComboBox<String> typOfQuestion;
    @FXML private ComboBox<NewQuestion> allQuestionList;
    @FXML private DatePicker datePicker;
    @FXML private Label showResultsToStudentLabel;
    @FXML private RadioButton gradeGButton;
    @FXML private RadioButton gradeVGButton;
    @FXML private Spinner timeInput;
    @FXML private Spinner pointSpinner;
    @FXML private TextField questionName;
    @FXML private TextField testName;
    @FXML private TextArea descriptionInput;

    private int indexQuestion;
    private CreateTest createTest = new CreateTest();
    private CheckBox rightAnswerCheckbox[];
    private TextField alternativField[];
    private RadioButton rightAnswerRadioButton[];
    private NewTest newTest = new NewTest();

    //test
    private ReadTest readTest;
    @FXML private VBox showTeacherTestVbox;
    @FXML private BorderPane showTeacherTestBorderPane;
    private RadioButton selectTestToPublishOrEdit[];
    private RadioButton selectToEdit[];
    private int userId;
    private TextField updateQuestionNameTextField;
    private RadioButton updateGradeG;
    private RadioButton updateGradeVG;
    private Spinner updatePoints;
    private ComboBox<String> updateTypeOfQuestion;
    private ObservableList<String> typeOfQuestionList = FXCollections.observableArrayList("Flervals", "Alternativ");
    private ComboBox<Integer> updateNumberOfAlternatives;
    private ObservableList<Integer> numberOfAlternatviesList = FXCollections.observableArrayList(2,3,4,5);
    private BorderPane updateQuestionAlternativeBorderPane;
    private TextField updateQuestionAlternativeTextField[];
    private RadioButton updateQuestionAlternativeRadioButton[];
    private CheckBox updateQuestionAlternativeCheckbox[];

    //test

    @FXML
    private void setShowResultToStudent(){
        if(getSelfCorrect().isSelected()){
            getShowResultsToStudent().setDisable(false);
            getShowResultsToStudentLabel().setTextFill(Color.web("#000000"));
        }
        else{
            getShowResultsToStudent().setSelected(false);
            getShowResultsToStudent().setDisable(true);
            getShowResultsToStudentLabel().setTextFill(Color.web("#d3d3d3"));
        }
    }

    @FXML
    private void getCreateTypeOfQuestion(){
        if(getTypOfQuestion().getValue().equals("Alternativ")){
                setAlternativeTextFields(getSelectedNumberOfAlternatives());
        }
        else if(getTypOfQuestion().getValue().equals("Flervals")){
            setMultipleTextFields(getSelectedNumberOfAlternatives());
        }
    }

    @FXML
    private void getSelectedQuestionFromComboBox() {
        setIndexQuestion(getAllQuestionList().getSelectionModel().getSelectedIndex());

        if(getAllQuestionList().getSelectionModel().getSelectedItem() != null){

            setQuestionName(getAllQuestionList().getSelectionModel().getSelectedItem().getQuestionName());
            getGradeGButton().setSelected(getAllQuestionList().getSelectionModel().getSelectedItem().isGradeG());
            getGradeVGButton().setSelected(getAllQuestionList().getSelectionModel().getSelectedItem().isGradeVG());
            setPointSpinner(getAllQuestionList().getSelectionModel().getSelectedItem().getPoints());
            getTypOfQuestion().setValue(getAllQuestionList().getSelectionModel().getSelectedItem().getTypeOfQuestion());
            getNumberOfAlternatives().setValue(getAllQuestionList().getSelectionModel().getSelectedItem().getNumberOfAlternatives());

            for(int i = 0; i < getAllQuestionList().getSelectionModel().getSelectedItem().getAlternativObservableList().size(); i++){
                getAlternativField()[i].setText(getAllQuestionList().getSelectionModel().getSelectedItem().getAlternativObservableList().get(i).getAlternative());
                if(getTypOfQuestion().getValue().equals("Flervals")){
                    getRightAnswerCheckbox()[i].setSelected(getAllQuestionList().getSelectionModel().getSelectedItem().getAlternativObservableList().get(i).getRightAnswer());
                }
                else if(getTypOfQuestion().getValue().equals("Alternativ")){
                    getRightAnswerRadioButton()[i].setSelected(getAllQuestionList().getSelectionModel().getSelectedItem().getAlternativObservableList().get(i).getRightAnswer());
                }
            }

            getAddQuestionButton().setDisable(true);
            getDeleteQuestionButton().setDisable(false);
            getUpdateQuestionButton().setDisable(false);
        }
    }

    @FXML
    private void setNumberOfAlternativesToTrue(){
        getNumberOfAlternatives().setDisable(false);
        getCreateTypeOfQuestion();
    }

    @FXML
    private void confirmedTestMessage(){
        Dialog<ButtonType> message = new Dialog<>();
        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox();
        ButtonType saveTest = new ButtonType("Spara", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Avbryt", ButtonBar.ButtonData.CANCEL_CLOSE);
        String selfCorrect = "Nej";
        String showResultatsToStudent = "Nej";

        if(getSelfCorrect().isSelected()){
            selfCorrect = "Ja";
        }
        if(getShowResultsToStudent().isSelected()){
            showResultatsToStudent = "Ja";
        }

        vBox.getChildren().add(new Label("Provnamn: " + getTestName().getText()));
        vBox.getChildren().add(new Label("Beskrivning: " + getDescriptionInput().getText()));
        vBox.getChildren().add(new Label("Datum: " + getDatePicker().getValue().toString()));
        vBox.getChildren().add(new Label("Tid: " + Integer.parseInt(getTimeInput().getValue().toString())));
        vBox.getChildren().add(new Label("Självrättande: " + selfCorrect));
        vBox.getChildren().add(new Label("Visa resultat till student: " + showResultatsToStudent));

        for(int i = 0; i < getNewTest().getQuestionObservableList().size(); i++){
            String grade = "";

            if(getNewTest().getQuestionObservableList().get(i).isGradeG()){
                grade = "G";
            }
            else if(getNewTest().getQuestionObservableList().get(i).isGradeVG()){
                grade="VG";
            }

            vBox.getChildren().add(new Label("\nFråga " + (i+1) + ": " + getNewTest().getQuestionObservableList().get(i).getQuestionName()));
            vBox.getChildren().add(new Label("Betyg: " + grade));
            vBox.getChildren().add(new Label("Poäng: " + getNewTest().getQuestionObservableList().get(i).getPoints()));

            for(int j = 0; j < getNewTest().getQuestionObservableList().get(i).getAlternativObservableList().size(); j++){
                String rightAnswer = "Nej";

                if(getNewTest().getQuestionObservableList().get(i).getAlternativObservableList().get(j).getRightAnswer()){
                    rightAnswer = "Ja";
                }

                vBox.getChildren().add(new Label("Alternativ " + (j+1) + ": " +
                        getNewTest().getQuestionObservableList().get(i).getAlternativObservableList().get(j).getAlternative()
                        + " Rätt svar: " + rightAnswer));
            }
        }

        scrollPane.setContent(vBox);
        scrollPane.setPrefViewportHeight(500);
        scrollPane.isFitToWidth();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        message.setTitle("Sammanfattning");
        message.getDialogPane().setContent(scrollPane);
        message.getDialogPane().getButtonTypes().addAll(saveTest, cancel);
        message.showAndWait().ifPresent(response -> {
            if(response == saveTest){
                saveTestToDataBase();
            }
        });
    }

    @FXML
    private void addQuestion() throws IOException{
        if(!getQuestionName().isEmpty()){
            NewQuestion newQuestion = new NewQuestion();
            newQuestion.setTypeOfQuestion(getTypOfQuestion().getValue());
            newQuestion.setQuestionName(getQuestionName());
            newQuestion.setGradeG(getGradeGButton().isSelected());
            newQuestion.setGradeVG(getGradeVGButton().isSelected());
            newQuestion.setNumberOfAlternatives(Integer.parseInt(getNumberOfAlternatives().getValue().toString()));
            newQuestion.setPoints(Integer.parseInt(getPointSpinner().getValue().toString()));

            for(int i = 0; i < getAlternativField().length; i++){
                if(!getAlternativField()[i].getText().isEmpty()){
                    NewAlternativ newAlternativ = new NewAlternativ();
                    newAlternativ.setAlternative(getAlternativField()[i].getText());

                    if(getTypOfQuestion().getValue().equals("Flervals")){
                        newAlternativ.setRightAnswer(getRightAnswerCheckbox()[i].isSelected());
                    }
                    else if(getTypOfQuestion().getValue().equals("Alternativ")){
                        newAlternativ.setRightAnswer(getRightAnswerRadioButton()[i].isSelected());
                    }

                    newQuestion.getAlternativObservableList().add(newAlternativ);
                }
            }

            getNewTest().getQuestionObservableList().add(newQuestion);

            getAllQuestionList().setItems(getNewTest().getQuestionObservableList());
            StringConverter<NewQuestion> converter = new StringConverter<NewQuestion>() {
                @Override
                public String toString(NewQuestion object) {
                    return object.getQuestionName();
                }

                @Override
                public NewQuestion fromString(String string) {
                    return null;
                }
            };
            getAllQuestionList().setConverter(converter);
            setResetNewQuestion();
        }
    }

    @FXML
    private void setUpdateQuestion(){
        getNewTest().getQuestionObservableList().get(getIndexQuestion()).setGradeG(getGradeGButton().isSelected());
        getNewTest().getQuestionObservableList().get(getIndexQuestion()).setGradeVG(getGradeVGButton().isSelected());
        getNewTest().getQuestionObservableList().get(getIndexQuestion()).setQuestionName(getQuestionName());
        getNewTest().getQuestionObservableList().get(getIndexQuestion()).setTypeOfQuestion(getTypOfQuestion().getValue());
        getNewTest().getQuestionObservableList().get(getIndexQuestion()).setNumberOfAlternatives(getSelectedNumberOfAlternatives());
        getNewTest().getQuestionObservableList().get(getIndexQuestion()).setPoints(Integer.parseInt(getPointSpinner().getValue().toString()));
        getNewTest().getQuestionObservableList().get(getIndexQuestion()).getAlternativObservableList().clear();

        for(int i = 0; i < getNewTest().getQuestionObservableList().get(getIndexQuestion()).getNumberOfAlternatives(); i++){
            NewAlternativ newAlternativ = new NewAlternativ();
            newAlternativ.setAlternative(getAlternativField()[i].getText());

            if(getTypOfQuestion().getValue().equals("Flervals")){
                newAlternativ.setRightAnswer(getRightAnswerCheckbox()[i].isSelected());
            }
            else if(getTypOfQuestion().getValue().equals("Alternativ")){
                newAlternativ.setRightAnswer(getRightAnswerRadioButton()[i].isSelected());
            }

            getNewTest().getQuestionObservableList().get(getIndexQuestion()).getAlternativObservableList().add(newAlternativ);
        }
        setResetNewQuestion();
    }

    @FXML
    private void setDeleteQuestion(){
        getNewTest().getQuestionObservableList().remove(getIndexQuestion());
        setResetNewQuestion();
    }

    private int getSelectedNumberOfAlternatives() {
        int numberOfAlternatives = 0;
            if(getNumberOfAlternatives().getValue() != null){
                numberOfAlternatives = Integer.parseInt(getNumberOfAlternatives().getValue().toString());
            }
        return numberOfAlternatives;
    }

    private void setAlternativeTextFields(int numberOfAlternatives){
        GridPane gridPane = new GridPane();
        setAlternativField(new TextField[numberOfAlternatives]);
        setRightAnswerRadioButton(new RadioButton[numberOfAlternatives]);
        ToggleGroup toggleGroup = new ToggleGroup();
        gridPane.setVgap(5);
        gridPane.setHgap(5);

            for(int i = 0; i < numberOfAlternatives; i++ ){
                Label alternativLabel = new Label("Alternativ " + (i+1) + ":");
                Label rightAnswerLabel = new Label("Rätt svar: ");
                getAlternativField()[i] = new TextField();
                getRightAnswerRadioButton()[i] = new RadioButton();
                getRightAnswerRadioButton()[i].setToggleGroup(toggleGroup);
                GridPane.setConstraints(alternativLabel, 0,i);
                GridPane.setConstraints(getAlternativField()[i], 1,i);
                GridPane.setConstraints(rightAnswerLabel, 2,i);
                GridPane.setConstraints(getRightAnswerRadioButton()[i], 3,i);
                gridPane.getChildren().addAll(alternativLabel, getAlternativField()[i], rightAnswerLabel, getRightAnswerRadioButton()[i]);
            }

        setBorderPaneAlternatives(gridPane);
    }

    private void setMultipleTextFields(int numberOfAlternatives){
        GridPane gridPane = new GridPane();
        setAlternativField(new TextField[numberOfAlternatives]);
        setRightAnswerCheckbox(new CheckBox[numberOfAlternatives]);
        gridPane.setVgap(5);
        gridPane.setHgap(5);

            for(int i = 0; i < numberOfAlternatives; i++ ){
                Label alternativLabel = new Label("Alternativ " + (i+1) + ":");
                Label rightAnswerLabel = new Label("Rätt svar: ");
                getAlternativField()[i] = new TextField();
                getRightAnswerCheckbox()[i] = new CheckBox();
                GridPane.setConstraints(alternativLabel, 0,i);
                GridPane.setConstraints(getAlternativField()[i], 1,i);
                GridPane.setConstraints(rightAnswerLabel, 2,i);
                GridPane.setConstraints(getRightAnswerCheckbox()[i], 3,i);
                gridPane.getChildren().addAll(alternativLabel, getAlternativField()[i], rightAnswerLabel, getRightAnswerCheckbox()[i]);
            }

        setBorderPaneAlternatives(gridPane);
    }

    private void saveTestToDataBase(){
        getCreateTest().createTest(getTestName().getText(), getDescriptionInput().getText(), getSelfCorrect().isSelected(),
                getShowResultsToStudent().isSelected(), getDatePicker().getValue().toString(), Integer.parseInt(getTimeInput().getValue().toString()));

            for(int i = 0; i < getNewTest().getQuestionObservableList().size(); i++){
                getCreateTest().createQuestion(getNewTest().getQuestionObservableList().get(i).getTypeOfQuestion(),
                        getNewTest().getQuestionObservableList().get(i).getQuestionName(),
                        getNewTest().getQuestionObservableList().get(i).isGradeVG(),
                        getNewTest().getQuestionObservableList().get(i).isGradeG(),
                        getNewTest().getQuestionObservableList().get(i).getPoints());

                getCreateTest().createNewAlternativeList();

                for(int j = 0; j < getNewTest().getQuestionObservableList().get(i).getAlternativObservableList().size(); j++){
                    getCreateTest().createAlternative(getNewTest().getQuestionObservableList().get(i).getAlternativObservableList().get(j).getAlternative(),
                            getNewTest().getQuestionObservableList().get(i).getAlternativObservableList().get(j).getRightAnswer());
                }

                        /*getNewTest().getQuestionObservableList().get(i).getAlternativObservableList().forEach(newAlternativ ->
                            getCreateTest().createAlternative(newAlternativ.getAlternative(), newAlternativ.getRightAnswer())
                        );*/
                        getCreateTest().addAlternativListToQuestion();
            }

        CreateUserTests createUserTests = new CreateUserTests();
        createUserTests.setTestId(getCreateTest().commitTest());
        createUserTests.setUserId(getUserId());
        createUserTests.commitTestToUser();

        setResetTestInput();
        setCreateTest(new CreateTest());
        setNewTest(new NewTest());
    }

    private void setResetNewQuestion(){
        setQuestionName("");
        getGradeGButton().setSelected(false);
        getGradeVGButton().setSelected(false);
        setBorderPaneAlternatives(null);
        setPointSpinner(0);
        getAllQuestionList().setValue(null);
        getTypOfQuestion().setValue("Välj");
        getNumberOfAlternatives().setDisable(true);
        getNumberOfAlternatives().setValue(null);
        getNumberOfAlternatives().setDisable(true);
        getAddQuestionButton().setDisable(false);
        getDeleteQuestionButton().setDisable(true);
        getUpdateQuestionButton().setDisable(true);
        getAddQuestionButton().requestFocus();
    }

    private void setResetTestInput(){
        getTestName().setText("");
        getDescriptionInput().setText("");
        getTimeInput().getEditor().setText(String.valueOf(0));
        getDatePicker().getEditor().clear();
        getSelfCorrect().setSelected(false);
        getShowResultsToStudent().setDisable(true);
        getShowResultsToStudent().setSelected(false);
        getNewTest().getQuestionObservableList().clear();
    }

    private TextField[] getAlternativField() {
        return alternativField;
    }

    private void setAlternativField(TextField[] alternativField) {
        this.alternativField = alternativField;
    }

    private ComboBox<Integer> getNumberOfAlternatives() {
        return numberOfAlternatives;
    }

    private ComboBox<NewQuestion> getAllQuestionList() {
        return allQuestionList;
    }

    private int getIndexQuestion() {
        return indexQuestion;
    }

    private void setIndexQuestion(int indexQuestion) {
        this.indexQuestion = indexQuestion;
    }

    private Spinner getPointSpinner() {
        return pointSpinner;
    }

    private void setPointSpinner(int pointSpinner) {
        this.pointSpinner.getEditor().setText(String.valueOf(pointSpinner));
    }

    private TextField getTestName() {
        return testName;
    }

    private CheckBox[] getRightAnswerCheckbox() {
        return rightAnswerCheckbox;
    }

    private void setRightAnswerCheckbox(CheckBox[] rightAnswerCheckbox) {
        this.rightAnswerCheckbox = rightAnswerCheckbox;
    }

    private RadioButton[] getRightAnswerRadioButton() {
        return rightAnswerRadioButton;
    }

    private void setRightAnswerRadioButton(RadioButton[] rightAnswerRadioButton) {
        this.rightAnswerRadioButton = rightAnswerRadioButton;
    }
    private Label getShowResultsToStudentLabel() {
        return showResultsToStudentLabel;
    }

    private CheckBox getSelfCorrect() {
        return selfCorrect;
    }

    private CheckBox getShowResultsToStudent() {
        return showResultsToStudent;
    }

    private ComboBox<String> getTypOfQuestion() {
        return typOfQuestion;
    }

    private void setBorderPaneAlternatives(GridPane gridPane){
        this.borderPaneAlternatives.setCenter(gridPane);
    }

    private String getQuestionName(){
        return questionName.getText();
    }

    private void setQuestionName(String questionName){
        this.questionName.setText(questionName);
    }

    private CreateTest getCreateTest() {
        return createTest;
    }

    private void setCreateTest(CreateTest createTest) {
        this.createTest = createTest;
    }

    private NewTest getNewTest() {
        return newTest;
    }

    private void setNewTest(NewTest newTest) {
        this.newTest = newTest;
    }

    private TextArea getDescriptionInput() {
        return descriptionInput;
    }

    private Spinner getTimeInput() {
        return timeInput;
    }

    private DatePicker getDatePicker() {
        return datePicker;
    }

    private Button getDeleteQuestionButton() {
        return deleteQuestionButton;
    }

    private Button getUpdateQuestionButton() {
        return updateQuestionButton;
    }

    private Button getAddQuestionButton() {
        return addQuestionButton;
    }

    private RadioButton getGradeGButton() {
        return gradeGButton;
    }

    private RadioButton getGradeVGButton() {
        return gradeVGButton;
    }

    //----Show-Tests------------

    public void getTeacherTest(){
        getShowTeacherTestVbox().getChildren().clear();
        ReadTest readTest = new ReadTest();
        ReadUserTests readUserTests = new ReadUserTests(getUserId());
        ToggleGroup toggleGroup = new ToggleGroup();
        setSelectTestToPublishOrEdit(new RadioButton[readUserTests.getUserTestsList().size()]);
        int counter = 0;
        for(UserTests userTests: readUserTests.getUserTestsList()){

            readTest.getTest(userTests.getTestId());
            for(Test test : readTest.getTestList()){
                HBox hBoxLeft = new HBox();
                HBox hBoxRight = new HBox();
                BorderPane borderPane = new BorderPane();
                hBoxLeft.setSpacing(50.0);
                getSelectTestToPublishOrEdit()[counter] = new RadioButton();
                getSelectTestToPublishOrEdit()[counter].setToggleGroup(toggleGroup);
                getSelectTestToPublishOrEdit()[counter].setId(String.valueOf(test.getTestId()));
                hBoxLeft.getChildren().addAll(new Label("Prov: " + test.getTestName()), new Label(" Beskrivning: " + test.getTestDescription()),
                        new Label(" Datum: " + test.getLastDate()), new Label(" Tid: " + String.valueOf(test.getTimeForTestMinutes())));
                hBoxRight.getChildren().addAll( new Label("Välj:"), getSelectTestToPublishOrEdit()[counter]);
                borderPane.setStyle("-fx-border-color: black;");
                borderPane.setPadding(new Insets(10));
                borderPane.setLeft(hBoxLeft);
                borderPane.setRight(hBoxRight);
                getShowTeacherTestVbox().getChildren().add(borderPane);
                counter++;
            }
        }
    }

    @FXML
    private void getSelectedTestToEdit(){
        ScrollPane scrollPane = new ScrollPane();
        ToggleGroup toggleGroup = new ToggleGroup();
        FlowPane flowPane = new FlowPane();
        setReadTest(new ReadTest());
        flowPane.setVgap(10);
        flowPane.setHgap(10);
        scrollPane.setStyle("-fx-background-color:transparent;");

        for(int i = 0; i <  getSelectTestToPublishOrEdit().length; i++){
            if(getSelectTestToPublishOrEdit()[i].isSelected()){
                int id = Integer.parseInt(getSelectTestToPublishOrEdit()[i].getId());
                getReadTest().getTest(id);
                setSelectToEdit(new RadioButton[(getReadTest().getTestList().get(0).getQuestionList().size() + 1)]);

                for(int j = 0; j < getReadTest().getTestList().size(); j++){
                    getSelectToEdit()[j] = new RadioButton();
                    getSelectToEdit()[j].setToggleGroup(toggleGroup);
                    getSelectToEdit()[j].setId(String.valueOf(getReadTest().getTestList().get(j).getTestId()));
                    getSelectToEdit()[j].setText("Välj test ");
                    VBox vBox = new VBox();
                    vBox.setStyle("-fx-border-color: black;");
                    vBox.setPadding(new Insets(10));
                    vBox.setSpacing(5);
                    vBox.getChildren().add(new Label("Test: " + getReadTest().getTestList().get(j).getTestName()));
                    vBox.getChildren().add(new Label("Beskrivning: " + getReadTest().getTestList().get(j).getTestDescription()));
                    vBox.getChildren().add(new Label("Datum: " + getReadTest().getTestList().get(j).getLastDate()));
                    vBox.getChildren().add(new Label("Tid:" + String.valueOf(getReadTest().getTestList().get(j).getTimeForTestMinutes())));
                    vBox.getChildren().add(getSelectToEdit()[j]);
                    flowPane.getChildren().add(vBox);

                    for(int k = 0; k < getReadTest().getTestList().get(j).getQuestionList().size(); k++){
                        getSelectToEdit()[(k+1)] = new RadioButton();
                        getSelectToEdit()[(k+1)].setToggleGroup(toggleGroup);
                        getSelectToEdit()[(k+1)].setId(String.valueOf(getReadTest().getTestList().get(j).getQuestionList().get(k).getQuestionId()));
                        getSelectToEdit()[(k+1)].setText("Välj fråga ");
                        VBox vBox1 = new VBox();
                        vBox1.setStyle("-fx-border-color: black;");
                        vBox1.setPadding(new Insets(10));
                        vBox1.setSpacing(5);

                        String grade = "";

                        if( getReadTest().getTestList().get(j).getQuestionList().get(k).isGradeG()){
                            grade = "G";
                        }
                        else if(getReadTest().getTestList().get(j).getQuestionList().get(k).isGradeVG()){
                            grade="VG";
                        }

                        vBox1.getChildren().add(new Label("Fråga " + (k+1) ));
                        vBox1.getChildren().add(new Label("Namn: " + getReadTest().getTestList().get(j).getQuestionList().get(k).getQuestionText()));
                        vBox1.getChildren().add(new Label("Typ av fråga: " + getReadTest().getTestList().get(j).getQuestionList().get(k).getTypeOfQuestion()));
                        vBox1.getChildren().add(new Label("Betyg: " + grade));
                        vBox1.getChildren().add(new Label("Poäng: " + getReadTest().getTestList().get(j).getQuestionList().get(k).getPoints()));

                            for(int m = 0; m < getReadTest().getTestList().get(j).getQuestionList().get(k).getAlternativeList().size(); m++){
                                vBox1.getChildren().add(new Label("Alternativ " + (m+1) + " : "
                                        + getReadTest().getTestList().get(j).getQuestionList().get(k).getAlternativeList().get(m).getAlternativeText() +
                                        " Rätt svar: " + getReadTest().getTestList().get(j).getQuestionList().get(k).getAlternativeList().get(m).isAlternativeStatus()));
                            }

                        vBox1.getChildren().addAll( getSelectToEdit()[(k+1)]);
                        flowPane.getChildren().add(vBox1);
                    }
                }
           }
        }
        scrollPane.setContent(flowPane);
        scrollPane.setFitToWidth(true);
        getShowTeacherTestBorderPane().setCenter(scrollPane);
    }

    private VBox getShowTeacherTestVbox() {
        return showTeacherTestVbox;
    }

    private int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private RadioButton[] getSelectTestToPublishOrEdit() {
        return selectTestToPublishOrEdit;
    }

    private void setSelectTestToPublishOrEdit(RadioButton[] selectTestToPublishOrEdit) {
        this.selectTestToPublishOrEdit = selectTestToPublishOrEdit;
    }

    private BorderPane getShowTeacherTestBorderPane() {
        return showTeacherTestBorderPane;
    }

    private RadioButton[] getSelectToEdit() {
        return selectToEdit;
    }

    private void setSelectToEdit(RadioButton[] selectToEdit) {
        this.selectToEdit = selectToEdit;
    }

    private ReadTest getReadTest() {
        return readTest;
    }

    private void setReadTest(ReadTest readTest) {
        this.readTest = readTest;
    }

    private TextField getUpdateQuestionNameTextField() {
        return updateQuestionNameTextField;
    }

    private void setUpdateQuestionNameTextField(TextField updateQuestionNameTextField) {
        this.updateQuestionNameTextField = updateQuestionNameTextField;
    }

    private RadioButton getUpdateGradeG() {
        return updateGradeG;
    }

    private void setUpdateGradeG(RadioButton updateGradeG) {
        this.updateGradeG = updateGradeG;
    }

    private RadioButton getUpdateGradeVG() {
        return updateGradeVG;
    }

    private void setUpdateGradeVG(RadioButton updateGradeVG) {
        this.updateGradeVG = updateGradeVG;
    }

    private Spinner getUpdatePoints() {
        return updatePoints;
    }

    private void setUpdatePoints(Spinner updatePoints) {
        this.updatePoints = updatePoints;
    }

    private ComboBox<String> getUpdateTypeOfQuestion() {
        return updateTypeOfQuestion;
    }

    private void setUpdateTypeOfQuestion(ComboBox<String> updateTypeOfQuestion) {
        this.updateTypeOfQuestion = updateTypeOfQuestion;
    }

    private ObservableList<String> getTypeOfQuestionList() {
        return typeOfQuestionList;
    }

    private ComboBox<Integer> getUpdateNumberOfAlternatives() {
        return updateNumberOfAlternatives;
    }

    private void setUpdateNumberOfAlternatives(ComboBox<Integer> updateNumberOfAlternatives) {
        this.updateNumberOfAlternatives = updateNumberOfAlternatives;
    }

    private ObservableList<Integer> getNumberOfAlternatviesList() {
        return numberOfAlternatviesList;
    }

    private BorderPane getUpdateQuestionAlternativeBorderPane() {
        return updateQuestionAlternativeBorderPane;
    }

    private void setUpdateQuestionAlternativeBorderPane(BorderPane updateQuestionAlternativeBorderPane) {
        this.updateQuestionAlternativeBorderPane = updateQuestionAlternativeBorderPane;
    }

    private TextField[] getUpdateQuestionAlternativeTextField() {
        return updateQuestionAlternativeTextField;
    }

    private void setUpdateQuestionAlternativeTextField(TextField[] updateQuestionAlternativeTextField) {
        this.updateQuestionAlternativeTextField = updateQuestionAlternativeTextField;
    }

    private RadioButton[] getUpdateQuestionAlternativeRadioButton() {
        return updateQuestionAlternativeRadioButton;
    }

    private void setUpdateQuestionAlternativeRadioButton(RadioButton[] updateQuestionAlternativeRadioButton) {
        this.updateQuestionAlternativeRadioButton = updateQuestionAlternativeRadioButton;
    }

    private CheckBox[] getUpdateQuestionAlternativeCheckbox() {
        return updateQuestionAlternativeCheckbox;
    }

    private void setUpdateQuestionAlternativeCheckbox(CheckBox[] updateQuestionAlternativeCheckbox) {
        this.updateQuestionAlternativeCheckbox = updateQuestionAlternativeCheckbox;
    }

    private String holdQuestionTypeForUpdate;
    private int alternativeIdList[];
    private int hodlQuestionIdForNewAlternatives;
    private TextField updateTestnameTextField;
    private TextArea updateDescriptionTextArea;
    private Spinner updateTestTimeInMinutesSpinner;
    private DatePicker updateDateForTestDatePIcker;
    private CheckBox updateSelfCorrectCheckBox;
    private CheckBox updateShowResultToStudentCheckBox;

    private String getHoldQuestionTypeForUpdate() {
        return holdQuestionTypeForUpdate;
    }

    private void setHoldQuestionTypeForUpdate(String holdQuestionTypeForUpdate) {
        this.holdQuestionTypeForUpdate = holdQuestionTypeForUpdate;
    }

    private int[] getAlternativeIdList() {
        return alternativeIdList;
    }

    private void setAlternativeIdList(int[] alternativeIdList) {
        this.alternativeIdList = alternativeIdList;
    }

    private int getHodlQuestionIdForNewAlternatives() {
        return hodlQuestionIdForNewAlternatives;
    }

    private void setHodlQuestionIdForNewAlternatives(int hodlQuestionIdForNewAlternatives) {
        this.hodlQuestionIdForNewAlternatives = hodlQuestionIdForNewAlternatives;
    }

    private TextField getUpdateTestnameTextField() {
        return updateTestnameTextField;
    }

    private void setUpdateTestnameTextField(TextField updateTestnameTextField) {
        this.updateTestnameTextField = updateTestnameTextField;
    }

    private TextArea getUpdateDescriptionTextArea() {
        return updateDescriptionTextArea;
    }

    private void setUpdateDescriptionTextArea(TextArea updateDescriptionTextArea) {
        this.updateDescriptionTextArea = updateDescriptionTextArea;
    }

    private Spinner getUpdateTestTimeInMinutesSpinner() {
        return updateTestTimeInMinutesSpinner;
    }

    private void setUpdateTestTimeInMinutesSpinner(Spinner updateTestTimeInMinutesSpinner) {
        this.updateTestTimeInMinutesSpinner = updateTestTimeInMinutesSpinner;
    }

    private DatePicker getUpdateDateForTestDatePIcker() {
        return updateDateForTestDatePIcker;
    }

    private void setUpdateDateForTestDatePIcker(DatePicker updateDateForTestDatePIcker) {
        this.updateDateForTestDatePIcker = updateDateForTestDatePIcker;
    }

    private CheckBox getUpdateSelfCorrectCheckBox() {
        return updateSelfCorrectCheckBox;
    }

    private void setUpdateSelfCorrectCheckBox(CheckBox updateSelfCorrectCheckBox) {
        this.updateSelfCorrectCheckBox = updateSelfCorrectCheckBox;
    }

    private CheckBox getUpdateShowResultToStudentCheckBox() {
        return updateShowResultToStudentCheckBox;
    }

    private void setUpdateShowResultToStudentCheckBox(CheckBox updateShowResultToStudentCheckBox) {
        this.updateShowResultToStudentCheckBox = updateShowResultToStudentCheckBox;
    }

    @FXML
    private void getSelectToEditButton() throws IOException{
        for(int i = 0; i < getSelectToEdit().length; i++){
            if(getSelectToEdit()[i].isSelected()){

                int id = Integer.parseInt(getSelectToEdit()[i].getId());
                Dialog<ButtonType> message = new Dialog<>();
                ButtonType saveTest = new ButtonType("Spara", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancel = new ButtonType("Avbryt", ButtonBar.ButtonData.CANCEL_CLOSE);

                if(getSelectToEdit()[i].getText().equals("Välj fråga ")){
                    message.setWidth(400);
                    message.setTitle("Uppdatera fråga");
                    message.getDialogPane().setContent(getUpdateMessageQuestionLayout(id));
                    message.getDialogPane().getButtonTypes().addAll(saveTest, cancel);
                    message.getDialogPane().setPrefHeight(330);
                    message.getDialogPane().setPrefWidth(350);

                    getUpdateTypeOfQuestion().setOnAction(event -> {
                        if(getUpdateTypeOfQuestion().getValue().equals("Alternativ")){
                            getUpdateAlternativeLayout();
                        }
                        else if(getUpdateTypeOfQuestion().getValue().equals("Flervals")){
                            getUpdateMultiLayout();
                        }
                    });

                    getUpdateNumberOfAlternatives().setOnAction(event -> {
                        if(getUpdateTypeOfQuestion().getValue().equals("Alternativ")){
                            getUpdateAlternativeLayout();
                        }
                        else if(getUpdateTypeOfQuestion().getValue().equals("Flervals")){
                            getUpdateMultiLayout();
                        }
                    });

                    message.showAndWait().ifPresent(response -> {
                        if(response == saveTest){
                            UpdateTest updateTest = new UpdateTest();
                            updateTest.updateQuestionInformation(id, getUpdateQuestionNameTextField().getText(), getUpdateTypeOfQuestion().getValue(),
                                    getUpdateGradeG().isSelected(), getUpdateGradeVG().isSelected(), Integer.parseInt(getUpdatePoints().getEditor().getText()));

                            if(getUpdateTypeOfQuestion().getValue().equals(getHoldQuestionTypeForUpdate())){
                                for(int l = 0; l < getUpdateNumberOfAlternatives().getValue(); l++ ){
                                    if(getUpdateTypeOfQuestion().getValue().equals("Alternativ")){
                                        updateTest.updateAlternativeInformation(Integer.parseInt(getUpdateQuestionAlternativeRadioButton()[l].getId()),getUpdateQuestionAlternativeTextField()[l].getText(), getUpdateQuestionAlternativeRadioButton()[l].isSelected());
                                    }else if(getUpdateTypeOfQuestion().getValue().equals("Flervals")){
                                        updateTest.updateAlternativeInformation(Integer.parseInt(getUpdateQuestionAlternativeCheckbox()[l].getId()),getUpdateQuestionAlternativeTextField()[l].getText(), getUpdateQuestionAlternativeCheckbox()[l].isSelected());
                                    }
                                }
                            }else if(!getUpdateTypeOfQuestion().getValue().equals(getHoldQuestionTypeForUpdate())){
                                //updateTest.deleteAQuestion(id);
                                for(int m = 0; m < getAlternativeIdList().length; m++){
                                   updateTest.deleteAnAlternative(getAlternativeIdList()[m]);
                                    System.out.println(getAlternativeIdList()[m]);
                                }
    /*
                                for(int n = 0; n < getUpdateNumberOfAlternatives().getValue(); n++){

                                    if(getUpdateTypeOfQuestion().getValue().equals("Alternativ")){
                                        updateTest.addNewAlternative(getHodlQuestionIdForNewAlternatives(),getUpdateQuestionAlternativeTextField()[n].getText(), getUpdateQuestionAlternativeRadioButton()[n].isSelected());
                                    }else if(getUpdateTypeOfQuestion().getValue().equals("Flervals")){
                                        updateTest.addNewAlternative(getHodlQuestionIdForNewAlternatives(),getUpdateQuestionAlternativeTextField()[n].getText(), getUpdateQuestionAlternativeCheckbox()[n].isSelected());
                                    }
                                }*/
                            }

                            getSelectedTestToEdit();
                        }
                    });
                }else if(getSelectToEdit()[i].getText().equals("Välj test ")){
                    message.setTitle("Uppdatera test");
                    message.getDialogPane().setContent(getUpdateMessageTestInfoLayout());
                    message.getDialogPane().getButtonTypes().addAll(saveTest, cancel);

                    getUpdateSelfCorrectCheckBox().setOnAction(event -> {
                        if(getUpdateSelfCorrectCheckBox().isSelected()){
                            getUpdateShowResultToStudentCheckBox().setDisable(false);
                        }
                        else{
                            getUpdateShowResultToStudentCheckBox().setSelected(false);
                            getUpdateShowResultToStudentCheckBox().setDisable(true);
                        }
                    });

                    message.showAndWait().ifPresent(response -> {
                        if(response == saveTest){
                            UpdateTest updateTest = new UpdateTest();
                            updateTest.updateTestInformation(getReadTest().getTestList().get(0).getTestId(), getUpdateTestnameTextField().getText(), getUpdateDescriptionTextArea().getText(),
                                    Integer.parseInt(getUpdateTestTimeInMinutesSpinner().getEditor().getText()), getUpdateDateForTestDatePIcker().getEditor().getText(),
                                    getUpdateSelfCorrectCheckBox().isSelected(), getUpdateShowResultToStudentCheckBox().isSelected());
                            getSelectedTestToEdit();
                        }
                    });
                }
            }
        }
    }

    private void getUpdateAlternativeLayout(){
        GridPane alternativeGridpane = new GridPane();
        ToggleGroup alternativToggleGroup = new ToggleGroup();
        setUpdateQuestionAlternativeTextField(new TextField[getUpdateNumberOfAlternatives().getValue()]);
        setUpdateQuestionAlternativeRadioButton(new  RadioButton[getUpdateNumberOfAlternatives().getValue()]);
        alternativeGridpane.setHgap(5);
        alternativeGridpane.setVgap(5);

            for(int i = 0; i < getUpdateNumberOfAlternatives().getValue(); i++){
                alternativeGridpane.add(new Label("Alternativ " + (i+1) + " : "),0,i);
                alternativeGridpane.add(getUpdateQuestionAlternativeTextField()[i] = new TextField(),1,i);
                alternativeGridpane.add(new Label(" Rätt svar: "), 2,i);
                alternativeGridpane.add(getUpdateQuestionAlternativeRadioButton()[i] = new RadioButton(),3,i);
                getUpdateQuestionAlternativeRadioButton()[i].setToggleGroup(alternativToggleGroup);
            }

        getUpdateQuestionAlternativeBorderPane().setCenter(alternativeGridpane);
    }

    private void getUpdateMultiLayout(){
        GridPane alternativeGridpane = new GridPane();
        setUpdateQuestionAlternativeTextField(new TextField[getUpdateNumberOfAlternatives().getValue()]);
        setUpdateQuestionAlternativeCheckbox(new  CheckBox[getUpdateNumberOfAlternatives().getValue()]);
        alternativeGridpane.setHgap(5);
        alternativeGridpane.setVgap(5);

            for(int i = 0; i < getUpdateNumberOfAlternatives().getValue(); i++){
                alternativeGridpane.add(new Label("Alternativ " + (i+1) + " : "),0,i);
                alternativeGridpane.add(getUpdateQuestionAlternativeTextField()[i] = new TextField(),1,i);
                alternativeGridpane.add(new Label(" Rätt svar: "), 2,i);
                alternativeGridpane.add(getUpdateQuestionAlternativeCheckbox()[i] = new CheckBox(),3,i);
            }

        getUpdateQuestionAlternativeBorderPane().setCenter(alternativeGridpane);
    }

    private GridPane getUpdateMessageQuestionLayout(int id){
        GridPane gridPane = new GridPane();
        ToggleGroup toggleGroup = new ToggleGroup();
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        for(int j = 0; j < getReadTest().getTestList().get(0).getQuestionList().size(); j++) {
            if (getReadTest().getTestList().get(0).getQuestionList().get(j).getQuestionId() == id) {
                setAlternativeIdList(new int[getReadTest().getTestList().get(0).getQuestionList().get(j).getAlternativeList().size()]);
                setHodlQuestionIdForNewAlternatives(getReadTest().getTestList().get(0).getQuestionList().get(j).getQuestionId());

                setUpdateQuestionNameTextField(new TextField(getReadTest().getTestList().get(0).getQuestionList().get(j).getQuestionText()));
                setUpdateGradeG(new RadioButton());
                getUpdateGradeG().setSelected(getReadTest().getTestList().get(0).getQuestionList().get(j).isGradeG());
                getUpdateGradeG().setText("G");
                getUpdateGradeG().setToggleGroup(toggleGroup);
                setUpdateGradeVG(new RadioButton());
                getUpdateGradeVG().setText("VG");
                getUpdateGradeVG().setToggleGroup(toggleGroup);
                getUpdateGradeVG().setSelected(getReadTest().getTestList().get(0).getQuestionList().get(j).isGradeVG());
                setUpdatePoints(new Spinner(0, 100, 0, 1));
                getUpdatePoints().getEditor().setText(String.valueOf(getReadTest().getTestList().get(0).getQuestionList().get(j).getPoints()));
                setUpdateTypeOfQuestion(new ComboBox<>(getTypeOfQuestionList()));
                getUpdateTypeOfQuestion().setValue(getReadTest().getTestList().get(0).getQuestionList().get(j).getTypeOfQuestion());
                setHoldQuestionTypeForUpdate(getReadTest().getTestList().get(0).getQuestionList().get(j).getTypeOfQuestion());
                setUpdateNumberOfAlternatives(new ComboBox<>(getNumberOfAlternatviesList()));
                getUpdateNumberOfAlternatives().setValue(getReadTest().getTestList().get(0).getQuestionList().get(j).getAlternativeList().size());
                setUpdateQuestionAlternativeBorderPane(new BorderPane());

                gridPane.add(new Label("Namn: "), 0, 0);
                gridPane.add(getUpdateQuestionNameTextField(), 1, 0, 4, 1);
                gridPane.add(new Label("Betyg: "), 0, 1);
                gridPane.add(getUpdateGradeG(), 1, 1);
                gridPane.add(getUpdateGradeVG(), 2, 1);
                gridPane.add(new Label("Poäng: "), 0, 2);
                gridPane.add(getUpdatePoints(), 1, 2, 2, 1);
                gridPane.add(new Label("Typ av fråga: "), 0, 3);
                gridPane.add(getUpdateTypeOfQuestion(), 1, 3);
                gridPane.add(new Label("Antal alternativ: "), 2, 3);
                gridPane.add(getUpdateNumberOfAlternatives(), 3, 3);
                gridPane.add(getUpdateQuestionAlternativeBorderPane(), 0, 4, 4, 1);

                if (getUpdateTypeOfQuestion().getValue().equals("Alternativ")) {
                    getUpdateAlternativeLayout();

                    for (int k = 0; k < getReadTest().getTestList().get(0).getQuestionList().get(j).getAlternativeList().size(); k++) {
                        getUpdateQuestionAlternativeTextField()[k].setText(getReadTest().getTestList().get(0).getQuestionList().get(j).getAlternativeList().get(k).getAlternativeText());
                        getUpdateQuestionAlternativeRadioButton()[k].setSelected(getReadTest().getTestList().get(0).getQuestionList().get(j).getAlternativeList().get(k).isAlternativeStatus());
                        getUpdateQuestionAlternativeRadioButton()[k].setId(String.valueOf(getReadTest().getTestList().get(0).getQuestionList().get(j).getAlternativeList().get(k).getAlternativeId()));
                        getAlternativeIdList()[k] = getReadTest().getTestList().get(0).getQuestionList().get(j).getAlternativeList().get(k).getAlternativeId();
                    }

                } else if (getUpdateTypeOfQuestion().getValue().equals("Flervals")) {
                    getUpdateMultiLayout();

                    for (int k = 0; k < getReadTest().getTestList().get(0).getQuestionList().get(j).getAlternativeList().size(); k++) {
                        getUpdateQuestionAlternativeTextField()[k].setText(getReadTest().getTestList().get(0).getQuestionList().get(j).getAlternativeList().get(k).getAlternativeText());
                        getUpdateQuestionAlternativeCheckbox()[k].setSelected(getReadTest().getTestList().get(0).getQuestionList().get(j).getAlternativeList().get(k).isAlternativeStatus());
                        getUpdateQuestionAlternativeCheckbox()[k].setId(String.valueOf(getReadTest().getTestList().get(0).getQuestionList().get(j).getAlternativeList().get(k).getAlternativeId()));
                        getAlternativeIdList()[k] = getReadTest().getTestList().get(0).getQuestionList().get(j).getAlternativeList().get(k).getAlternativeId();
                    }
                }
            }
        }
        return gridPane;
    }

    private GridPane getUpdateMessageTestInfoLayout(){
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        setUpdateTestnameTextField(new TextField(getReadTest().getTestList().get(0).getTestName()));
        setUpdateDescriptionTextArea(new TextArea(getReadTest().getTestList().get(0).getTestDescription()));
        setUpdateDateForTestDatePIcker(new DatePicker());
        getUpdateDateForTestDatePIcker().getEditor().setText(getReadTest().getTestList().get(0).getLastDate());
        setUpdateTestTimeInMinutesSpinner(new Spinner(0,100,0,1));
        getUpdateTestTimeInMinutesSpinner().getEditor().setText(String.valueOf(getReadTest().getTestList().get(0).getTimeForTestMinutes()));
        setUpdateSelfCorrectCheckBox(new CheckBox());
        getUpdateSelfCorrectCheckBox().setSelected(getReadTest().getTestList().get(0).isSelfCorrecting());
        setUpdateShowResultToStudentCheckBox(new CheckBox());
        getUpdateShowResultToStudentCheckBox().setSelected(getReadTest().getTestList().get(0).isSeeResultAfter());

        if(!getReadTest().getTestList().get(0).isSelfCorrecting()){
            getUpdateShowResultToStudentCheckBox().setDisable(true);
        }

        gridPane.add(new Label("Namn: "), 0,0);
        gridPane.add(getUpdateTestnameTextField(),1,0);
        gridPane.add(new Label("Beskrivning: "), 0,1);
        gridPane.add(getUpdateDescriptionTextArea(),1,1);
        gridPane.add(new Label("Datum: "), 0,2);
        gridPane.add(getUpdateDateForTestDatePIcker(),1,2);
        gridPane.add(new Label("Tid: "), 0,3);
        gridPane.add(getUpdateTestTimeInMinutesSpinner(),1,3);
        gridPane.add(new Label("Självrättande: "), 0,4);
        gridPane.add(getUpdateSelfCorrectCheckBox(),1,4);
        gridPane.add(new Label("Visa test för student: "), 0,5);
        gridPane.add(getUpdateShowResultToStudentCheckBox(),1,5);

        return gridPane;
    }

    @FXML
    private void getSelectToDelete(){
        for(int i = 0; i <  getSelectTestToPublishOrEdit().length; i++) {
            if (getSelectTestToPublishOrEdit()[i].isSelected()) {
                DeleteTest deleteTest = new DeleteTest();
                DeleteUserTests deleteUserTests = new DeleteUserTests();
                deleteTest.deleteTest(Integer.parseInt(getSelectTestToPublishOrEdit()[i].getId()));
                deleteUserTests.deleteTestFromUserTest(Integer.parseInt(getSelectTestToPublishOrEdit()[i].getId()));
                getTeacherTest();
                getShowTeacherTestBorderPane().setCenter(null);
            }
        }
    }
}