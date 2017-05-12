package com.testverktyg.eclipselink.service.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

import com.testverktyg.eclipselink.entity.Alternative;
import com.testverktyg.eclipselink.entity.Question;
import com.testverktyg.eclipselink.entity.Test;

/* Created by jennifergisslow on 2017-05-12. */

public class DeleteTest {

    private EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
    private EntityManager entitymanager = emfactory.createEntityManager();

    private ArrayList<Integer> altId = new ArrayList<>();

    //Will delete a test with all the questions and alternatives connected to the test
    public void deleteTheTest(int testId){
        entitymanager.getTransaction( ).begin( );
        List<Question> questionId = entitymanager.createNamedQuery("FindQuestionId", Question.class).setParameter("tId", testId).getResultList();
        Test test = entitymanager.find(Test.class, testId);

        //collects all the alternativeId in a arraylist that is connected to the test
        for (int i = 0; i < questionId.size(); i++){
            List<Alternative> alternativeId = entitymanager.createNamedQuery("FindAlternativeId", Alternative.class).setParameter("qId", questionId.get(i)).getResultList();
            for (int j = 0; j < alternativeId.size(); j++){
                altId.add(Integer.valueOf(String.valueOf(alternativeId.get(j))));
            }
        }

        //deletes all the alternatives to the test
        for (int i = 0; i < altId.size(); i++){
            Alternative alternative = entitymanager.find(Alternative.class, altId.get(i));
            entitymanager.remove(alternative);
        }

        //deletes all the questions to the test
        for (int i = 0; i < questionId.size(); i++){
            Question question = entitymanager.find(Question.class, questionId.get(i));
            entitymanager.remove(question);
        }

        //deletes the test
        entitymanager.remove(test);
        System.out.println("TestId " + test.getTestId() + ", testnamn: '" + test.getTestName() + "' " + "borttaget!!!");

        entitymanager.getTransaction( ).commit( );
        entitymanager.close( );
        emfactory.close( );
    }
}
