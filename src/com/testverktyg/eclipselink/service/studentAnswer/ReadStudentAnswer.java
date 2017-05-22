package com.testverktyg.eclipselink.service.studentAnswer;

import com.testverktyg.eclipselink.entity.Alternative;
import com.testverktyg.eclipselink.entity.Question;
import com.testverktyg.eclipselink.entity.StudentAnswer;
import com.testverktyg.eclipselink.service.Test.ReadTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Andreas on 2017-05-22.
 */
public class ReadStudentAnswer {

    List<Alternative> correctAnswersList;
    List<StudentAnswer> studentAnswersList;

    int questionCount;

    public void getCorrectAnswers(int testId){
        ReadTest rt = new ReadTest(testId);
        rt.getActiveTest();
        rt.getTest(testId);
        ReadStudentAnswer rs = new ReadStudentAnswer();
        questionCount = rt.getAmountOfQuestions();

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        int maxPointsG=0;
        int maxPointsVG=0;
        int studPointsG=0;
        int studPointsVG=0;
        for (int i=0;i<rt.getTestList().get(0).getQuestionList().size();i++) {
            int correctAnswerCounter=0;
            int studentAnswerCounter=0;
            for (int j=0;j<rt.getTestList().get(0).getQuestionList().get(i).getAlternativeList().size();j++) {
                if(rt.getTestList().get(0).getQuestionList().get(i).getAlternativeList().get(j).isAlternativeStatus()){
                    correctAnswerCounter++;
                    for(int k=0;k<getStudentAnswersList().size();k++){
                        if(getStudentAnswersList().get(k).getAlternativeId()==rt.getTestList().get(0).getQuestionList().get(i).getAlternativeList().get(j).getAlternativeId()){
                            studentAnswerCounter++;
                        }
                    }
                }
            }
            boolean isG = rt.getTestList().get(0).getQuestionList().get(i).isGradeG();
            if(correctAnswerCounter==studentAnswerCounter){
                if(isG==true){
                    studPointsG = studPointsG + rt.getTestList().get(0).getQuestionList().get(i).getPoints();
                }else{
                    studPointsVG = studPointsVG + rt.getTestList().get(0).getQuestionList().get(i).getPoints();
                }
            }
            if(isG==true){
                maxPointsG = maxPointsG + rt.getTestList().get(0).getQuestionList().get(i).getPoints();
            }else{
                maxPointsVG = maxPointsVG + rt.getTestList().get(0).getQuestionList().get(i).getPoints();
            }
        }
        System.out.println("Maxpoäng G: " + maxPointsG);
        System.out.println("Maxpoäng CG: " + maxPointsVG);
        System.out.println("Student G: " + studPointsG);
        System.out.println("Student VG: " + studPointsVG);

    }

    public void getStudentAnswer(int sId, int tId) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        studentAnswersList = entitymanager.createNamedQuery("getStudentAnswer", StudentAnswer.class).setParameter("sId", sId).setParameter("tId", tId).getResultList();
    }

    public List<StudentAnswer> getStudentAnswersList() {
        return studentAnswersList;
    }

    public static void main(String[] args) {
        ReadStudentAnswer rsa = new ReadStudentAnswer();
        rsa.getStudentAnswer(2,1);
        rsa.getCorrectAnswers(1);

    }
}
