package com.testverktyg.eclipselink.service.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

import com.testverktyg.eclipselink.entity.Alternative;
import com.testverktyg.eclipselink.entity.Test;
import com.testverktyg.eclipselink.entity.Question;

/*Created by jennifergisslow on 2017-05-08.*/

public class CreateTest {

    //Variable
    private List<Question> questionList = new ArrayList<>();
    private List<Alternative> alternativeList;

    private Test test;
    private Question question;

    private EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA");
    private EntityManager entitymanager = emfactory.createEntityManager( );

    public CreateTest() {
        entitymanager.getTransaction().begin();
    }

    public void createTest(String testName, String descriptTest, Boolean selfCorrecting, Boolean seeResult, String lastDateTest, int minutesForTest){

        //create a new test
        test = new Test();
        test.setTestName(testName);
        test.setTestDescription(descriptTest);
        test.setSelfCorrecting(selfCorrecting);
        test.setSeeResultAfter(seeResult);
        test.setLastDate(lastDateTest);
        test.setTimeForTestMinutes(minutesForTest);

        entitymanager.persist(test);
        entitymanager.flush();

    }

    public void createQuestion(String typeQuestion, String textQuestion, boolean gradeVg, boolean gradeG, int points){

        //Add a new question to the active test
        question = new Question();
        question.setTestId(test.getTestId());
        question.setTypeOfQuestion(typeQuestion);
        question.setQuestionText(textQuestion);
        question.setGradeVG(gradeVg);
        question.setGradeG(gradeG);
        question.setPoints(points);
        entitymanager.persist(question);
        entitymanager.flush();
        questionList.add(question);
    }

    public void createAlternative(String altText, boolean alternativeStatus){

        //Add a new alternative to the active question
        Alternative alternative = new Alternative();
        alternative.setQuestionId(question.getQuestionId());
        alternative.setAlternativeText(altText);
        alternative.setAlternativeStatus(alternativeStatus);

        entitymanager.persist(alternative);
        alternativeList.add(alternative);
    }

    public void addAlternativListToQuestion(){
        question.setAlternativeList(alternativeList);
    }

    public void createNewAlternativeList(){
        alternativeList  = new ArrayList<>();
    }

    public int commitTest(){
        test.setQuestionList(questionList);

        //Commit to database and close the connection
        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
        return test.getTestId();
    }
}
