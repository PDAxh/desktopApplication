package com.testverktyg.eclipselink.service.studentAnswer;

import com.testverktyg.eclipselink.entity.StudentAnswer;
import com.testverktyg.eclipselink.service.Test.ReadTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/* Created by Andreas on 2017-05-22. */
public class ReadStudentAnswer {

    private List<StudentAnswer> oneStudentAnswersList;
    private List<StudentAnswer> allStudentAnswersList;
    private int maxPointsG;
    private int maxPointsVG;
    private int studPointsG;
    private int studPointsVG;

    public void getCorrectAnswers(int testId){
        ReadTest rt = new ReadTest(testId);
        rt.getActiveTest();
        rt.getTest(testId);

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        maxPointsG=0;
        maxPointsVG=0;
        studPointsG=0;
        studPointsVG=0;
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
                if(isG){
                    studPointsG = studPointsG + rt.getTestList().get(0).getQuestionList().get(i).getPoints();
                }else{
                    studPointsVG = studPointsVG + rt.getTestList().get(0).getQuestionList().get(i).getPoints();
                }
            }
            if(isG){
                maxPointsG = maxPointsG + rt.getTestList().get(0).getQuestionList().get(i).getPoints();
            }else{
                maxPointsVG = maxPointsVG + rt.getTestList().get(0).getQuestionList().get(i).getPoints();
            }
        }

        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }

    public void getStudentAnswerFromSpecificStudent(int sId, int tId) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        oneStudentAnswersList = entitymanager.createNamedQuery("getStudentAnswer", StudentAnswer.class).setParameter("sId", sId).setParameter("tId", tId).getResultList();

        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }

    public void getAllStudentAnswers(int testId){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        allStudentAnswersList = entitymanager.createNamedQuery("getAllStudentsAnswers", StudentAnswer.class).setParameter("tId", testId).getResultList();

        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }

    public List<StudentAnswer> getStudentAnswersList() {
        return oneStudentAnswersList;
    }

    public List<StudentAnswer> getAllStudentAnswersList() {
        return allStudentAnswersList;
    }

    public int getStudPointsG() {
        return studPointsG;
    }

    public int getStudPointsVG() {
        return studPointsVG;
    }

    public int getMaxPointsG() {
        return maxPointsG;
    }

    public int getMaxPointsVG() {
        return maxPointsVG;
    }
}
