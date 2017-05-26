package com.testverktyg.eclipselink.view.main.layout;

import com.testverktyg.eclipselink.service.studentAnswer.ReadStudentAnswer;

import java.util.ArrayList;

/**
 * Created by jennifergisslow on 2017-05-26.
 */
public class StatisticForAdminAndTeacher {

    private ArrayList<Integer> userIdList = new ArrayList<>();
    private ReadStudentAnswer readStudentAnswer = new ReadStudentAnswer();

    private int maxAntStudentDone = 0;
    private int testId;

    private int antVGStudents;
    private int antGStudents;
    private int antIGStudents;

    private StatisticForAdminAndTeacher(int testId){
        this.testId = testId;
    }

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

    public int getMaxAntStudentDone() {
        return maxAntStudentDone;
    }

    public int getAntVGStudents() {
        return antVGStudents;
    }

    public int getAntGStudents() {
        return antGStudents;
    }

    public int getAntIGStudents() {
        return antIGStudents;
    }

    public static void main(String[] args) {
        StatisticForAdminAndTeacher newTestAdminController = new StatisticForAdminAndTeacher(1);

        newTestAdminController.maxAntStudentsDoneTest();
        newTestAdminController.antStudentsOfEveryGrade();
    }
}
