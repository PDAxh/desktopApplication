package com.testverktyg.eclipselink.service.Test;

import com.testverktyg.eclipselink.entity.Alternative;
import com.testverktyg.eclipselink.entity.Question;
import com.testverktyg.eclipselink.entity.Test;

import javax.persistence.*;
import java.util.List;

/* Created by jennifergisslow on 2017-05-09. */

public class ReadTest {

    //Use this variable from GUI to see if next question is the last question.
    int amountOfQuestions = 0;
    //Use this variable from GUI to see if the question id a G or VG question.
    String gradeOnActiveQuestion = "";

    //Temporary variable for storing the information
    String testName = "";
    String testDescription = "";
    String testLastDate = "";
    int testTimeInMinutes = 0;

    private int questionCount = 0;
    private int tempTestId;

    private EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
    private EntityManager entitymanager = emfactory.createEntityManager();

    private List<Question> activeQuestionText;
    private List<Question> activeQuestionId;
    private List<Alternative> activeAlternativeText;
    private List<Alternative> activeAlternativeId;
    private List<Question> activeQuestionGradeG;

    public ReadTest(int testId){
        this.tempTestId = testId;
    }

    //Will be used to read the chosen test with the right testId
    public void getActiveTest(){

        //Get test information
        Test activeTest = entitymanager.find(Test.class, tempTestId);

        testName = activeTest.getTestName();
        testDescription = activeTest.getTestDescription();
        testLastDate = activeTest.getLastDate();
        testTimeInMinutes = activeTest.getTimeForTestMinutes();

        //Get the first question information
        activeQuestionText = entitymanager.createNamedQuery("FindQuestionText", Question.class).setParameter("tId", tempTestId).getResultList();
        activeQuestionId = entitymanager.createNamedQuery("FindQuestionId", Question.class).setParameter("tId", tempTestId).getResultList();
        activeQuestionGradeG = entitymanager.createNamedQuery("FindQuestionGradeG", Question.class).setParameter("qId", activeQuestionId.get(questionCount)).getResultList();

        if (activeQuestionGradeG.size() == 0){
            gradeOnActiveQuestion = "G";
        }
        else{
            gradeOnActiveQuestion = "VG";
        }

        //Gets the amount of questions in this test for the next button
        amountOfQuestions = activeQuestionId.size();

        activeAlternativeText = entitymanager.createNamedQuery("FindAlternativeText", Alternative.class).setParameter("qId", activeQuestionId.get(questionCount)).getResultList();
        activeAlternativeId = entitymanager.createNamedQuery("FindAlternativeId", Alternative.class).setParameter("qId", activeQuestionId.get(questionCount)).getResultList();
    }

    //Will be used for a next button to print out the next question with the results
    public void getNextActiveQuestion(){

        questionCount++;

        activeAlternativeText = entitymanager.createNamedQuery("FindAlternativeText", Alternative.class).setParameter("qId", activeQuestionId.get(questionCount)).getResultList();
        activeAlternativeId = entitymanager.createNamedQuery("FindAlternativeId", Alternative.class).setParameter("qId", activeQuestionId.get(questionCount)).getResultList();
    }

}
