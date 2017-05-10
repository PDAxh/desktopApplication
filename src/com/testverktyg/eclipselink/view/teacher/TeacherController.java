package com.testverktyg.eclipselink.view.teacher;

import com.testverktyg.eclipselink.service.Test.CreateTest;
import com.testverktyg.eclipselink.view.teacher.createTest.NewAlternativ;
import com.testverktyg.eclipselink.view.teacher.createTest.NewQuestion;
import com.testverktyg.eclipselink.view.teacher.createTest.NewTest;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import java.io.IOException;

/**
 * Created by Jonas Johansson, Java2 on 2017-05-01.
 *
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
    @FXML private TextField questionName;
    @FXML private TextField testName;
    @FXML private TextArea descriptionInput;

    private int indexQuestion;
    private CheckBox rightAnswerCheckbox[];
    private TextField alternativField[];
    private RadioButton rightAnswerRadioButton[];
    private NewTest newTest = new NewTest();
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
        return typOfQuestion.getValue();
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

    private CreateTest getCreateTest() {
        return createTest;
    }

    @FXML
    private void saveTestToDataBase(){
        getCreateTest().createTest(getTestName(), getDescriptionInput(), getSelfCorrect().isSelected(), getShowResultsToStudent().isSelected(), getDatePicker(), getTimeInput());

            for(int i = 0; i < getNewTest().getQuestionObservableList().size(); i++){
                getCreateTest().createQuestion(getNewTest().getQuestionObservableList().get(i).getTypeOfQuestion(),
                        getNewTest().getQuestionObservableList().get(i).getQuestionName(),
                        getNewTest().getQuestionObservableList().get(i).isGradeVG(),
                        getNewTest().getQuestionObservableList().get(i).isGradeG());

                        getNewTest().getQuestionObservableList().get(i).getAlternativObservableList().forEach(newAlternativ ->
                            getCreateTest().createAlternative(newAlternativ.getAlternative(), newAlternativ.getRightAnswer())
                        );
            }

        getCreateTest().commitTest();
    }

    private NewTest getNewTest() {
        return newTest;
    }

    private String getTestName(){
        return testName.getText();
    }

    @FXML
    private void addQuestion() throws IOException{
        NewQuestion newQuestion = new NewQuestion();
        newQuestion.setTypeOfQuestion(getTypOfQuestion());
        newQuestion.setQuestionName(getQuestionName());
        newQuestion.setGradeG(getGradeGButton().isSelected());
        newQuestion.setGradeVG(getGradeVGButton().isSelected());
        newQuestion.setNumberOfAlternatives(Integer.parseInt(this.numberOfAlternatives.getValue().toString()));

            for(int i = 0; i < alternativField.length; i++){
                if(!alternativField[i].getText().isEmpty()){
                    NewAlternativ newAlternativ = new NewAlternativ();
                    newAlternativ.setAlternative(alternativField[i].getText());

                        if(getTypOfQuestion().equals("Flervals")){
                            newAlternativ.setRightAnswer(rightAnswerCheckbox[i].isSelected());
                        }
                        else if(getTypOfQuestion().equals("Alternativ")){
                            newAlternativ.setRightAnswer(rightAnswerRadioButton[i].isSelected());
                        }

                    newQuestion.getAlternativObservableList().add(newAlternativ);
                }
            }

        getNewTest().getQuestionObservableList().add(newQuestion);

        allQuestionList.setItems(getNewTest().getQuestionObservableList());
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

        setResetNewQuestion();
    }

    private String getDescriptionInput() {
        return descriptionInput.getText();
    }

    public void setDescriptionInput(String descriptionInput) {
        this.descriptionInput.setText(descriptionInput);
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

    private RadioButton getGradeGButton() {
        return gradeGButton;
    }

    private RadioButton getGradeVGButton() {
        return gradeVGButton;
    }

    @FXML
    private void setUpdateQuestion(){

        if(!getNewTest().getQuestionObservableList().get(getIndexQuestion()).getQuestionName().equals(getQuestionName())){
            getNewTest().getQuestionObservableList().get(getIndexQuestion()).setQuestionName(getQuestionName());
        }

        setResetNewQuestion();
    }

    @FXML
    private void setDeleteQuestion(){
        getNewTest().getQuestionObservableList().remove(getIndexQuestion());
        setResetNewQuestion();
    }

    private void setResetNewQuestion(){
        setQuestionName("");
        getGradeGButton().setSelected(false);
        getGradeVGButton().setSelected(false);
        setBorderPaneAlternatives(null);

        allQuestionList.setValue(null);
        typOfQuestion.setValue("Välj");
        numberOfAlternatives.setDisable(true);
        numberOfAlternatives.setValue(null);

        getAddQuestionButton().setDisable(false);
        getDeleteQuestionButton().setDisable(true);
        getUpdateQuestionButton().setDisable(true);
        getAddQuestionButton().requestFocus();
    }

    @FXML
    private void getSelectedQuestionFromComboBox() {
        setIndexQuestion(allQuestionList.getSelectionModel().getSelectedIndex());

        if(allQuestionList.getSelectionModel().getSelectedItem() != null){

            setQuestionName(allQuestionList.getSelectionModel().getSelectedItem().getQuestionName());
            getGradeGButton().setSelected(allQuestionList.getSelectionModel().getSelectedItem().isGradeG());
            getGradeVGButton().setSelected(allQuestionList.getSelectionModel().getSelectedItem().isGradeVG());
            typOfQuestion.setValue(allQuestionList.getSelectionModel().getSelectedItem().getTypeOfQuestion());
            this.numberOfAlternatives.setValue(allQuestionList.getSelectionModel().getSelectedItem().getNumberOfAlternatives());

                for(int i = 0; i < allQuestionList.getSelectionModel().getSelectedItem().getAlternativObservableList().size(); i++){
                    alternativField[i].setText(allQuestionList.getSelectionModel().getSelectedItem().getAlternativObservableList().get(i).getAlternative());

                        if(getTypOfQuestion().equals("Flervals")){
                            rightAnswerCheckbox[i].setSelected(allQuestionList.getSelectionModel().getSelectedItem().getAlternativObservableList().get(i).getRightAnswer());
                        }
                        else if(getTypOfQuestion().equals("Alternativ")){
                            rightAnswerRadioButton[i].setSelected(allQuestionList.getSelectionModel().getSelectedItem().getAlternativObservableList().get(i).getRightAnswer());
                        }
                }

            getAddQuestionButton().setDisable(true);
            getDeleteQuestionButton().setDisable(false);
            getUpdateQuestionButton().setDisable(false);
        }
    }

    public void setNumberOfAlternatives(ComboBox<Integer> numberOfAlternatives) {
        this.numberOfAlternatives = numberOfAlternatives;
    }

    private int getIndexQuestion() {
        return indexQuestion;
    }

    private void setIndexQuestion(int indexQuestion) {
        this.indexQuestion = indexQuestion;
    }
}