package com.testverktyg.eclipselink.view.teacher;

import com.testverktyg.eclipselink.service.Test.CreateTest;
import com.testverktyg.eclipselink.view.teacher.createTest.NewAlternativ;
import com.testverktyg.eclipselink.view.teacher.createTest.NewQuestion;
import com.testverktyg.eclipselink.view.teacher.createTest.NewTest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import java.io.IOException;

/**
 * Created by Grodfan on 2017-05-01.
 *
 */
public class TeacherController{

    //@FXML private BorderPane createNewTestRootBorderPane;
    @FXML private BorderPane borderPaneAlternatives;
    @FXML private Button deleteQuestionButton;
    @FXML private Button updateQuestionButton;
    @FXML private Button addQuestionButton;
    @FXML private CheckBox selfCorrect;
    @FXML private CheckBox showResultsToStudent;
    @FXML private ComboBox numberOfAlternatives;
    @FXML private ComboBox typOfQuestion;
    @FXML private ComboBox allQuestionList;
    @FXML private DatePicker datePicker;
    @FXML private Label showResultsToStudentLabel;
    @FXML private RadioButton gradeGButton;
    @FXML private RadioButton gradeVGButton;
    @FXML private Spinner timeInput;
    @FXML private TextField questionName;
    @FXML private TextField testName;
    @FXML private TextArea descriptionInput;

    private CheckBox rightAnswerCheckbox[];
    private TextField alternativField[];
    private RadioButton rightAnswerRadioButton[];
    private NewTest newTest = new NewTest();
    private int counter = 0;
    private ObservableList<NewQuestion> questionObservableList;

    private CreateTest createTest = new CreateTest();

    @FXML
    private void setShowResultToStudent(){
        if(getSelfCorrect().isSelected()){
            getShowResultsToStudent().setDisable(false);
            showResultsToStudentLabel.setTextFill(Color.web("#000000"));
        }
        else{
            getShowResultsToStudent().setSelected(false);
            getShowResultsToStudent().setDisable(true);
            showResultsToStudentLabel.setTextFill(Color.web("#d3d3d3"));
        }
    }

    private CheckBox getSelfCorrect() {
        return selfCorrect;
    }

    private CheckBox getShowResultsToStudent() {
        return showResultsToStudent;
    }


    private String getTypOfQuestion(){
        return typOfQuestion.getValue().toString();
    }

    @FXML
    private void getNumberOfAlternatives(){
        int numberOfAlternatives = 0;
        if(this.numberOfAlternatives.getValue() != null){
            numberOfAlternatives = Integer.parseInt(this.numberOfAlternatives.getValue().toString());
        }

        if(getTypOfQuestion().equals("Alternativ")){
            setAlternativeTextFields(numberOfAlternatives);
        }
        else if(getTypOfQuestion().equals("Flervals")){
            setMultipleTextFields(numberOfAlternatives);
        }
    }

    private void setAlternativeTextFields(int numberOfAlternatives){
        GridPane gridPane = new GridPane();
        alternativField = new TextField[numberOfAlternatives];
        rightAnswerRadioButton = new RadioButton[numberOfAlternatives];
        ToggleGroup toggleGroup = new ToggleGroup();
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        for(int i = 0; i < numberOfAlternatives; i++ ){
            Label alternativLabel = new Label("Alternativ " + (i+1) + ":");
            Label rightAnswerLabel = new Label("Rätt svar: ");
            alternativField[i] = new TextField();
            rightAnswerRadioButton[i] = new RadioButton();
            rightAnswerRadioButton[i].setToggleGroup(toggleGroup);
            GridPane.setConstraints(alternativLabel, 0,i);
            GridPane.setConstraints(alternativField[i], 1,i);
            GridPane.setConstraints(rightAnswerLabel, 2,i);
            GridPane.setConstraints(rightAnswerRadioButton[i], 3,i);
            gridPane.getChildren().addAll(alternativLabel, alternativField[i], rightAnswerLabel, rightAnswerRadioButton[i]);
        }

        setBorderPaneAlternatives(gridPane);
    }

    private void setMultipleTextFields(int numberOfAlternatives){
        GridPane gridPane = new GridPane();
        alternativField = new TextField[numberOfAlternatives];
        rightAnswerCheckbox = new CheckBox[numberOfAlternatives];
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        for(int i = 0; i < numberOfAlternatives; i++ ){
            Label alternativLabel = new Label("Alternativ " + (i+1) + ":");
            Label rightAnswerLabel = new Label("Rätt svar: ");
            alternativField[i] = new TextField();
            rightAnswerCheckbox[i] = new CheckBox();
            GridPane.setConstraints(alternativLabel, 0,i);
            GridPane.setConstraints(alternativField[i], 1,i);
            GridPane.setConstraints(rightAnswerLabel, 2,i);
            GridPane.setConstraints(rightAnswerCheckbox[i], 3,i);
            gridPane.getChildren().addAll(alternativLabel, alternativField[i], rightAnswerLabel, rightAnswerCheckbox[i]);
        }

        setBorderPaneAlternatives(gridPane);

    }

    private void setBorderPaneAlternatives(GridPane gridPane){
        this.borderPaneAlternatives.setCenter(gridPane);
    }

    @FXML
    private void setNumberOfAlternativesToTrue(){
        this.numberOfAlternatives.setDisable(false);
        getNumberOfAlternatives();
    }

    private String getQuestionName(){
        return questionName.getText();
    }

    private void setQuestionName(String questionName){
        this.questionName.setText(questionName);
    }

    @FXML
    private void saveTestToDataBase(){
        createTest.createTest(getTestName(), getDescriptionInput(), getSelfCorrect().isSelected(), getShowResultsToStudent().isSelected(), getDatePicker(), getTimeInput());

        for(int i = 0; i < newTest.getQuestionArrayList().size(); i++) {
            createTest.createQuestion(newTest.getQuestionArrayList().get(i).getTypeOfQuestion(),
                    newTest.getQuestionArrayList().get(i).getQuestionName(),
                    newTest.getQuestionArrayList().get(i).isGradeVG(),
                    newTest.getQuestionArrayList().get(i).isGradeG());
            System.out.println(newTest.getQuestionArrayList().get(i).getQuestionName());
            for(int j = 0; j < newTest.getAlternativArrayList().size(); j++){
                if(newTest.getAlternativArrayList().get(j).getAlternativeId() == i) {
                    createTest.createAlternative(newTest.getAlternativArrayList().get(j).getAlternative(), newTest.getAlternativArrayList().get(j).getRightAnswer());
                    System.out.println(newTest.getAlternativArrayList().get(j).getAlternative() + " " + newTest.getAlternativArrayList().get(j).getRightAnswer());
                }
            }
        }

        createTest.commitTest();
    }

    private NewTest getNewTest() {
        return newTest;
    }

    private String getTestName(){
        return testName.getText();
    }

/*    @FXML
    private void newQuestion() throws IOException{
        GridPane gridPane = FXMLLoader.load(getClass().getResource("layout/createNewQuestion.fxml"));
        setCreateNewTestRootBorderPaneCenter(gridPane);
    }

    private void setCreateNewTestRootBorderPaneCenter(GridPane gridPane){
        createNewTestRootBorderPane.setCenter(gridPane);
    }*/

    @FXML
    private void addQuestion() throws IOException{
        NewQuestion newQuestion = new NewQuestion();
        newQuestion.setQuestionId(counter);
        newQuestion.setTypeOfQuestion(getTypOfQuestion());
        newQuestion.setQuestionName(getQuestionName());
        newQuestion.setGradeG(getGradeGButton().isSelected());
        newQuestion.setGradeVG(getGradeVGButton().isSelected());
        getNewTest().getQuestionArrayList().add(newQuestion);

        for(int i = 0; i < alternativField.length; i++){
            if(!alternativField[i].getText().isEmpty()){
                NewAlternativ newAlternativ = new NewAlternativ();
                newAlternativ.setAlternative(alternativField[i].getText());
                newAlternativ.setAlternativeId(counter);

                if(getTypOfQuestion().equals("Flervals")){
                    newAlternativ.setRightAnswer(rightAnswerCheckbox[i].isSelected());
                }
                else if(getTypOfQuestion().equals("Alternativ")){
                    newAlternativ.setRightAnswer(rightAnswerRadioButton[i].isSelected());
                }

                newTest.getAlternativArrayList().add(newAlternativ);
            }
        }

        questionObservableList = FXCollections.observableList(getNewTest().getQuestionArrayList());
        allQuestionList.setItems(questionObservableList);
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
        allQuestionList.setConverter(converter);
        counter++;
    }

    @FXML
    private void printQuestions() {
        for(int i = 0; i < newTest.getQuestionArrayList().size(); i++) {
            System.out.println(newTest.getQuestionArrayList().get(i).getQuestionName());
            for(int j = 0; j < newTest.getAlternativArrayList().size(); j++){
                if(newTest.getAlternativArrayList().get(j).getAlternativeId() == i) {
                    System.out.println(newTest.getAlternativArrayList().get(j).getAlternative() + " " + newTest.getAlternativArrayList().get(j).getRightAnswer());
                }
            }
        }

        getAddQuestionButton().setDisable(true);
        getDeleteQuestionButton().setDisable(false);
        getUpdateQuestionButton().setDisable(false);
    }

    private String getDescriptionInput() {
        return descriptionInput.getText();
    }

    public void setDescriptionInput(String descriptionInput) {
        this.descriptionInput.setText("");
    }

    private int getTimeInput() {
        return Integer.parseInt(timeInput.getValue().toString());
    }

    public void setTimeInput(Spinner timeInput) {
        this.timeInput = timeInput;
    }

    private String getDatePicker() {
        return datePicker.getValue().toString();
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
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

    @FXML
    private void setUpdateQuestion(){

    }

    @FXML
    private void setDeleteQuestion(){
        questionObservableList.remove(allQuestionList.getSelectionModel().getSelectedIndex());

      setResetNewQuestion();
    }

  /*  public BorderPane getCreateNewTestRootBorderPane() {
        return createNewTestRootBorderPane;
    }*/

    private RadioButton getGradeGButton() {
        return gradeGButton;
    }

    private RadioButton getGradeVGButton() {
        return gradeVGButton;
    }

    private void setResetNewQuestion(){
        setQuestionName("");
        getGradeGButton().setSelected(false);
        getGradeVGButton().setSelected(false);
        setBorderPaneAlternatives(null);

        allQuestionList.setPlaceholder(null);
        typOfQuestion.setPlaceholder(null);
        numberOfAlternatives.setDisable(true);
//        numberOfAlternatives.setValue(null);





    }


}
