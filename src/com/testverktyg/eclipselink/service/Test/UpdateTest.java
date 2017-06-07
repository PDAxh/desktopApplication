package com.testverktyg.eclipselink.service.Test;

import com.testverktyg.eclipselink.entity.Alternative;
import com.testverktyg.eclipselink.entity.Question;
import com.testverktyg.eclipselink.entity.Test;

import javax.persistence.*;

/* Created by jennifergisslow on 2017-05-15. */

public class UpdateTest {

    //To be able to use several methods after each other without calling the class again
    // EntityManagerFactory and EntityManager needs to be in every method.

    // updates the information for the chosen test
    public void updateTestInformation(int testId, String newTestName, String newTestDescription, int newTestTime, String newLastDate, boolean newSelfCorrecting, boolean newSeeResult){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
        EntityManager entitymanager = emfactory.createEntityManager( );

        entitymanager.getTransaction( ).begin( );
        Test test = entitymanager.find(Test.class, testId);

        test.setTestName(newTestName);
        test.setTestDescription(newTestDescription);
        test.setTimeForTestMinutes(newTestTime);
        test.setLastDate(newLastDate);
        test.setSeeResultAfter(newSeeResult);
        test.setSelfCorrecting(newSelfCorrecting);

        entitymanager.getTransaction( ).commit( );
        entitymanager.close();
        emfactory.close();

    }

    // updatest the information for the chosen question
    public void updateQuestionInformation(int questionId, String newQuestionText, String newQuestionType, boolean newGradeG, boolean newGradeVG, int newPoints){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
        EntityManager entitymanager = emfactory.createEntityManager( );

        entitymanager.getTransaction( ).begin( );
        Question question = entitymanager.find(Question.class, questionId);

        question.setQuestionText(newQuestionText);
        question.setTypeOfQuestion(newQuestionType);
        question.setGradeG(newGradeG);
        question.setGradeVG(newGradeVG);
        question.setPoints(newPoints);

        entitymanager.getTransaction( ).commit( );
        entitymanager.close();
        emfactory.close();
    }

    //updatest the information for the chosen alternative
    public void updateAlternativeInformation(int alternativeId, String newAlternativeText, boolean newAlternativeStatus){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
        EntityManager entitymanager = emfactory.createEntityManager( );

        entitymanager.getTransaction( ).begin( );
        Alternative alternative = entitymanager.find(Alternative.class, alternativeId);

        alternative.setAlternativeText(newAlternativeText);
        alternative.setAlternativeStatus(newAlternativeStatus);

        entitymanager.getTransaction( ).commit( );
        entitymanager.close();
        emfactory.close();
    }

    public void deleteAnAlternative(int alternativeId){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
        EntityManager entitymanager = emfactory.createEntityManager( );

        entitymanager.getTransaction( ).begin( );
        Alternative alternative = entitymanager.find(Alternative.class, alternativeId);
        entitymanager.remove(alternative);

        entitymanager.getTransaction( ).commit( );
        entitymanager.close();
        emfactory.close();
    }
}
