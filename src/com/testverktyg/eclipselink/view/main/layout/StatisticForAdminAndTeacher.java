package com.testverktyg.eclipselink.view.main.layout;

import com.testverktyg.eclipselink.entity.Question;
import com.testverktyg.eclipselink.entity.Test;
import com.testverktyg.eclipselink.service.Test.ReadTest;
import com.testverktyg.eclipselink.service.studentAnswer.ReadStudentAnswer;
import com.testverktyg.eclipselink.service.user.ReadUser;
import com.testverktyg.eclipselink.service.userTests.ReadUserTests;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jennifergisslow on 2017-05-26.
 *
 */
public class StatisticForAdminAndTeacher {

    private ArrayList<Integer> userIdList = new ArrayList<>();
    private ReadStudentAnswer readStudentAnswer = new ReadStudentAnswer();
    private int maxAntStudentDone = 0;
    private int testId;
    private int antVGStudents;
    private int antGStudents;
    private int antIGStudents;
    private int totalGradeGQuestions = 0;
    private int totalGradeVgQuestions = 0;
    private int totalTestPoints = 0;
    private int testTimeInMinutes;
    private int averagePoints;
    private String testName;

    public StatisticForAdminAndTeacher(){}

    //Gets the amount of students that have done the test
    private void maxAntStudentsDoneTest(){

        readStudentAnswer.getAllStudentAnswers(testId);

        for (int i = 0; i < readStudentAnswer.getAllStudentAnswersList().size(); i++){
            int userIdCounter = 0;
            if (userIdList.isEmpty()){
                userIdList.add(readStudentAnswer.getAllStudentAnswersList().get(i).getStudentId());
            }
            for (int j = 0; j < userIdList.size(); j++){
                if (userIdList.get(j) == readStudentAnswer.getAllStudentAnswersList().get(i).getStudentId()){
                    userIdCounter++;
                }
            }
            if (userIdCounter < 1){
                userIdList.add(readStudentAnswer.getAllStudentAnswersList().get(i).getStudentId());
            }
        }

        for (int k = 0; k < userIdList.size(); k++){
            maxAntStudentDone++;
            System.out.println(userIdList.get(k));
        }
        System.out.println(maxAntStudentDone);
    }

    //gets the amount of students with the grade VG, G and IG
    private void antStudentsOfEveryGrade(){

        int resultGprocent;
        int resultVGprocent;

        antVGStudents = 0;
        antGStudents = 0;
        antIGStudents = 0;

        for (int i = 0; i < userIdList.size(); i++){
            readStudentAnswer.getStudentAnswerFromSpecificStudent(userIdList.get(i), testId);
            readStudentAnswer.getCorrectAnswers(testId);
            resultGprocent = readStudentAnswer.getStudPointsG() / readStudentAnswer.getMaxPointsG() * 100;
            resultVGprocent = readStudentAnswer.getStudPointsVG() / readStudentAnswer.getMaxPointsVG() * 100;
            setAveragePoints(readStudentAnswer.getStudPointsG() + readStudentAnswer.getStudPointsVG());

            if (resultGprocent>= 60 && resultVGprocent >= 60){
                antVGStudents++;
            }else if (resultGprocent>= 60 && resultVGprocent <= 60){
                antGStudents++;
            }else if (resultGprocent<= 60 && resultVGprocent <= 60){
                antIGStudents++;
            }

            System.out.println("Elev: " + userIdList.get(i) + " fick följande g-poäng: " + resultGprocent + "% och följande vg-poäng: " + resultVGprocent + "%");
        }
        System.out.println("Antalet IG- elever: " + antIGStudents + " Antalet G-elever: " + antGStudents + " Antalet VG-elever: " + antVGStudents);
    }

    private int getMaxTotalStudentForTest(){
        int maxTotalStudentsForTest = 0;
        ReadUser readUser = new ReadUser();
        ReadUserTests readUserTests = new ReadUserTests();
        List<String> klassList = new ArrayList<>();
        readUserTests.findAllUsersForOneTest(getTestId());

        for(int i = 0; i < readUserTests.getUserTestListByTestId().size(); i++){
            readUser.getFindClassWithUserId(readUserTests.getUserTestListByTestId().get(i).getUserId());
            if(!klassList.contains(readUser.getFindClassWithUserIdList().get(0).getKlass())){
                klassList.add(readUser.getFindClassWithUserIdList().get(0).getKlass());
            }
        }

        for(String readKlass : klassList){
            readUser.getUserIdByClass(readKlass);
            maxTotalStudentsForTest += readUser.getUserIdByClassList().size();
        }

        return maxTotalStudentsForTest;
    }

    private void setTotalQuestionsAndTotalPointsForQuestion(List<Question> readTest){
        for(Question question : readTest){
            if(question.isGradeG()){
                setTotalGradeGQuestions();
            }
            else if(question.isGradeVG()){
                setTotalGradeVgQuestions();
            }

            setTotalTestPoints(question.getPoints());
        }
    }

    public VBox getTestResultLayout(){
        getTestnameTimeAndQuestionlist();
        maxAntStudentsDoneTest();
        antStudentsOfEveryGrade();

        int average = 0;

        if((getAveragePoints() != 0) && (getMaxAntStudentDone() != 0)){
           average = getAveragePoints() / getMaxAntStudentDone();
        }

        VBox vBox = new VBox();
       // vBox.setStyle("-fx-border-color: black;");
        vBox.getChildren().add(new Label("Prov: " + getTestName()));
        vBox.getChildren().add(new Label("Antal Godkänt frågor: " + getTotalGradeGQuestions()));
        vBox.getChildren().add(new Label("Antal Väl Godkänt frågor: " + getTotalGradeVgQuestions()));
        vBox.getChildren().add(new Label("Tid: " + getTestTimeInMinutes()));
        vBox.getChildren().add(new Label("Genomsnittpoäng: " + String.valueOf(average)));
        vBox.getChildren().add(new Label("Max poäng: " + getTotalTestPoints()));
        vBox.getChildren().add(new Label("Antal elever som gjort test: " + getMaxAntStudentDone()));
        vBox.getChildren().add(new Label("Max antal elever: " + getMaxTotalStudentForTest()));
        vBox.getChildren().add(new Label("Antalet IG- elever: " + getAntIGStudents()));
        vBox.getChildren().add(new Label("Antalet G-elever: " + getAntGStudents()));
        vBox.getChildren().add(new Label("Antalet VG-elever: " + getAntVGStudents()));

        return vBox;
    }

    private void getTestnameTimeAndQuestionlist(){
        ReadTest readTest = new ReadTest();
        readTest.getTest(getTestId());

        for(Test test : readTest.getTestList()){
            setTestName(test.getTestName());
            setTestTimeInMinutes(test.getTimeForTestMinutes());
            setTotalQuestionsAndTotalPointsForQuestion(test.getQuestionList());
        }
    }

    private int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    private int getTestTimeInMinutes() {
        return testTimeInMinutes;
    }

    private void setTestTimeInMinutes(int testTimeInMinutes) {
        this.testTimeInMinutes = testTimeInMinutes;
    }

    private String getTestName() {
        return testName;
    }

    private void setTestName(String testName) {
        this.testName = testName;
    }

    private int getTotalGradeGQuestions() {
        return totalGradeGQuestions;
    }

    private void setTotalGradeGQuestions() {
        this.totalGradeGQuestions++;
    }

    private int getTotalGradeVgQuestions() {
        return totalGradeVgQuestions;
    }

    private void setTotalGradeVgQuestions() {
        this.totalGradeVgQuestions ++;
    }

    private int getTotalTestPoints() {
        return totalTestPoints;
    }

    private void setTotalTestPoints(int totalTestPoints) {
        this.totalTestPoints += totalTestPoints;
    }

    private int getMaxAntStudentDone() {
        return maxAntStudentDone;
    }

    private int getAntVGStudents() {
        return antVGStudents;
    }

    private int getAntGStudents() {
        return antGStudents;
    }

    private int getAntIGStudents() {
        return antIGStudents;
    }

    private int getAveragePoints() {
        return averagePoints;
    }

    private void setAveragePoints(int averagePoints) {
        this.averagePoints += averagePoints;
    }
}
