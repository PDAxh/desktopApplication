package com.testverktyg.eclipselink.view.teacher;

import com.testverktyg.eclipselink.entity.Alternative;
import com.testverktyg.eclipselink.entity.Question;
import com.testverktyg.eclipselink.entity.Test;
import com.testverktyg.eclipselink.entity.UserTests;
import com.testverktyg.eclipselink.service.Test.CreateTest;
import com.testverktyg.eclipselink.service.Test.ReadTest;
import com.testverktyg.eclipselink.service.userTests.CreateUserTests;
import com.testverktyg.eclipselink.service.userTests.ReadUserTests;
import com.testverktyg.eclipselink.view.teacher.createTest.NewAlternativ;
import com.testverktyg.eclipselink.view.teacher.createTest.NewQuestion;
import com.testverktyg.eclipselink.view.teacher.createTest.NewTest;
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
    @FXML private VBox showTeacherTestVbox;
    @FXML private BorderPane showTeacherTestBorderPane;
    private RadioButton selectTestToPublishOrEdit[];
    private int userId;
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
                        getNewTest().getQuestionObservableList().get(i).isGradeG());

                        getNewTest().getQuestionObservableList().get(i).getAlternativObservableList().forEach(newAlternativ ->
                            getCreateTest().createAlternative(newAlternativ.getAlternative(), newAlternativ.getRightAnswer())
                        );
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

    public void setCreateTest(CreateTest createTest) {
        this.createTest = createTest;
    }

    private NewTest getNewTest() {
        return newTest;
    }

    public void setNewTest(NewTest newTest) {
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

    @FXML
    private void getTeacherTest(){
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
        FlowPane flowPane = new FlowPane();
        ReadTest readTest = new ReadTest();
        flowPane.setVgap(10);
        flowPane.setHgap(10);

        for(int i = 0; i <  getSelectTestToPublishOrEdit().length; i++){
            if(getSelectTestToPublishOrEdit()[i].isSelected()){
                int id = Integer.parseInt(getSelectTestToPublishOrEdit()[i].getId());
                readTest.getTest(id);

                /*for(Test test : readTest.getTestList()){
                    int counter = 0;
                    VBox vBox = new VBox();
                    vBox.getChildren().add(new Label(test.getTestName()));
                    vBox.getChildren().add(new Label(test.getTestDescription()));
                    vBox.getChildren().add(new Label(test.getLastDate()));
                    vBox.getChildren().add(new Label(String.valueOf(test.getTimeForTestMinutes())));
                    flowPane.getChildren().add(vBox);

                    for(Question question: readTest.getTestList().get(0).getQuestionList()){
                        VBox vBox1 = new VBox();

                        String grade = "";

                        if( readTest.getTestList().get(0).getQuestionList().get(counter).isGradeG()){
                            grade = "G";
                        }
                        else if(readTest.getTestList().get(0).getQuestionList().get(counter).isGradeVG()){
                            grade="VG";
                        }

                        vBox1.getChildren().add(new Label(question.getQuestionText()));
                        vBox1.getChildren().add(new Label(question.getTypeOfQuestion()));
                        vBox1.getChildren().add(new Label("Betyg: " + grade));

                        for(Alternative alternative : readTest.getTestList().get(0).getQuestionList().get(2).getAlternativeList()){
                            vBox1.getChildren().add(new Label(alternative.getAlternativeText() + " Rätt svar: " + alternative.isAlternativeStatus()));
                            System.out.println(alternative.getAlternativeText() + " Rätt svar: " + alternative.isAlternativeStatus());
                        }




                        flowPane.getChildren().add(vBox1);
                        System.out.println(counter);
                        counter++;
                    }

                }*/

                for(int j = 0; j < readTest.getTestList().size(); j++){
                    VBox vBox = new VBox();
                    vBox.setStyle("-fx-border-color: black;");
                    vBox.setPadding(new Insets(10));
                    vBox.setSpacing(5);
                    vBox.getChildren().add(new Label("Test: " + readTest.getTestList().get(j).getTestName()));
                    vBox.getChildren().add(new Label("Beskrivning: " + readTest.getTestList().get(j).getTestDescription()));
                    vBox.getChildren().add(new Label("Datum: " + readTest.getTestList().get(j).getLastDate()));
                    vBox.getChildren().add(new Label("Tid:" + String.valueOf(readTest.getTestList().get(j).getTimeForTestMinutes())));
                    flowPane.getChildren().add(vBox);

                    for(int k = 0; k < readTest.getTestList().get(j).getQuestionList().size(); k++){
                        VBox vBox1 = new VBox();
                        vBox1.setStyle("-fx-border-color: black;");
                        vBox1.setPadding(new Insets(10));
                        vBox1.setSpacing(5);

                        String grade = "";

                        if( readTest.getTestList().get(j).getQuestionList().get(k).isGradeG()){
                            grade = "G";
                        }
                        else if(readTest.getTestList().get(j).getQuestionList().get(k).isGradeVG()){
                            grade="VG";
                        }

                        vBox1.getChildren().add(new Label("Fråga " + (k+1) ));
                        vBox1.getChildren().add(new Label("Namn: " + readTest.getTestList().get(j).getQuestionList().get(k).getQuestionText()));
                        vBox1.getChildren().add(new Label("Typ av fråga: " + readTest.getTestList().get(j).getQuestionList().get(k).getTypeOfQuestion()));
                        vBox1.getChildren().add(new Label("Betyg: " + grade));
                        System.out.println(k);

                            for(int m = 0; m < readTest.getTestList().get(j).getQuestionList().get(k).getAlternativeList().size(); m++){
                                vBox1.getChildren().add(new Label("Alternativ " + (m+1) + " : " + readTest.getTestList().get(j).getQuestionList().get(k).getAlternativeList().get(m).getAlternativeText() +
                                        " Rätt svar: " + readTest.getTestList().get(j).getQuestionList().get(k).getAlternativeList().get(m).isAlternativeStatus()));
                                System.out.println(m);
                            }

                        flowPane.getChildren().add(vBox1);
                    }
                }

            }
        }

        getShowTeacherTestBorderPane().setCenter(flowPane);
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

    public BorderPane getShowTeacherTestBorderPane() {
        return showTeacherTestBorderPane;
    }
}