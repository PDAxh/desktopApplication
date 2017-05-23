package com.testverktyg.eclipselink.service.Test;

import com.testverktyg.eclipselink.entity.Alternative;
import com.testverktyg.eclipselink.entity.Question;
import com.testverktyg.eclipselink.entity.Test;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/* Created by jennifergisslow on 2017-05-09. */

public class ReadTest {

    //Use this variable from GUI to see if next question is the last question.
    private int amountOfQuestions = 0;
    //Use this variable from GUI to see if the question id a G or VG question.
    private String gradeOnActiveQuestion = "";
    private String nonGradeOnActiveQuestion = "";

    //Temporary variable for storing the information
    private String testName = "";
    private String testDescription = "";
    private String testLastDate = "";
    private int testTimeInMinutes = 0;
    private boolean seeResult;
    private boolean selfCorrecting;
    private ArrayList<String> activeAlternativeStatus = new ArrayList<>();

    private int questionCount = 0;
    private int tempTestId;

    private EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
    private EntityManager entitymanager = emfactory.createEntityManager();

    private List<Question> activeQuestionText;
    private List<Question> activeQuestionId;
    private List<Alternative> activeAlternativeText;
    private List<Alternative> activeAlternativeId;
    private List<Alternative> alternativeStatus;
    private List<Question> activeQuestionGradeG;
    private List<Question> activeQuestionPoints;
    private List<Question> activeQuestionType;

    //---testkod---

    private List<Test> testList;

    public ReadTest(){}

    //---testkod--

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
        seeResult = activeTest.isSeeResultAfter();
        selfCorrecting = activeTest.isSelfCorrecting();


        //Get the first question information
        activeQuestionText = entitymanager.createNamedQuery("FindQuestionText", Question.class).setParameter("tId", tempTestId).getResultList();
        activeQuestionId = entitymanager.createNamedQuery("FindQuestionId", Question.class).setParameter("tId", tempTestId).getResultList();
        //activeQuestionGradeG = entitymanager.createNamedQuery("FindQuestionGradeG", Question.class).setParameter("qId", activeQuestionId.get(questionCount)).getResultList();
        activeQuestionType = entitymanager.createNamedQuery("FindQuestionType", Question.class).setParameter("qId", activeQuestionId.get(questionCount)).getResultList();
        activeQuestionPoints = entitymanager.createNamedQuery("FindAllPoints", Question.class).setParameter("qId", activeQuestionId.get(questionCount)).getResultList();
        getGrade(questionCount);

        //Gets the amount of questions in this test for the next button
        amountOfQuestions = activeQuestionId.size();

        activeAlternativeText = entitymanager.createNamedQuery("FindAlternativeText", Alternative.class).setParameter("qId", activeQuestionId.get(questionCount)).getResultList();
        activeAlternativeId = entitymanager.createNamedQuery("FindAlternativeId", Alternative.class).setParameter("qId", activeQuestionId.get(questionCount)).getResultList();

        //This part will be used for printing all the alternatives during updates
        for (int i = 0; i< activeAlternativeId.size(); i++) {
            alternativeStatus = entitymanager.createNamedQuery("FindAlternativeStatus", Alternative.class).setParameter("aId", activeAlternativeId.get(i)).getResultList();
            activeAlternativeStatus.add(alternativeStatus.toString());
        }
    }

    private void getGrade(int questionCount){
        activeQuestionGradeG = entitymanager.createNamedQuery("FindQuestionGradeVG", Question.class).setParameter("qId", activeQuestionId.get(questionCount)).getResultList();

        if (activeQuestionGradeG.size() == 0){
            gradeOnActiveQuestion = "G";
            nonGradeOnActiveQuestion = "VG";
        } else{
            gradeOnActiveQuestion = "VG";
            nonGradeOnActiveQuestion = "G";
        }
    }

    //Will be used for a next button to print out the alternatives for the next question
    public void getNextActiveQuestion(){

        questionCount++;
        getGrade(questionCount);

        activeAlternativeStatus.clear();

        activeQuestionPoints = entitymanager.createNamedQuery("FindAllPoints", Question.class).setParameter("qId", activeQuestionId.get(questionCount)).getResultList();

        activeAlternativeText = entitymanager.createNamedQuery("FindAlternativeText", Alternative.class).setParameter("qId", activeQuestionId.get(questionCount)).getResultList();
        activeAlternativeId = entitymanager.createNamedQuery("FindAlternativeId", Alternative.class).setParameter("qId", activeQuestionId.get(questionCount)).getResultList();

        //This part will be used for printing all the alternatives during updates
        for (int i = 0; i< activeAlternativeId.size(); i++) {
            alternativeStatus = entitymanager.createNamedQuery("FindAlternativeStatus", Alternative.class).setParameter("aId", activeAlternativeId.get(i)).getResultList();
            activeAlternativeStatus.add(alternativeStatus.toString());
        }
    }

    public String getTestName() {
        return testName;
    }

    public int getAmountOfQuestions() {
        return amountOfQuestions;
    }

    public String getGradeOnActiveQuestion() {
        return gradeOnActiveQuestion;
    }

    public String getNonGradeOnActiveQuestion() {
        return nonGradeOnActiveQuestion;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public String getTestLastDate() {
        return testLastDate;
    }

    public int getTestTimeInMinutes() {
        return testTimeInMinutes;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public List<Question> getActiveQuestionText() {
        return activeQuestionText;
    }

    public List<Question> getActiveQuestionId() {
        return activeQuestionId;
    }

    public List<Alternative> getActiveAlternativeText() {
        return activeAlternativeText;
    }

    public List<Alternative> getActiveAlternativeId() {
        return activeAlternativeId;
    }

    public List<Question> getActiveQuestionGradeG() {
        return activeQuestionGradeG;
    }

    public List<Question> getActiveQuestionPoints() {
        return activeQuestionPoints;
    }

    public List<Question> getActiveQuestionType() {
        return activeQuestionType;
    }

    public boolean isSeeResult() {
        return seeResult;
    }

    public boolean isSelfCorrecting() {
        return selfCorrecting;
    }

    public List<Alternative> getAlternativeStatus() {
        return alternativeStatus;
    }

    //using this main for console testing\\
    public static void main(String[] args) {

        ReadTest readTest = new ReadTest();
        readTest.getActiveTest();

        System.out.println(readTest.testName);
        System.out.println(readTest.testDescription);
        System.out.println(readTest.testLastDate);
        System.out.println(readTest.testTimeInMinutes);
        System.out.println(readTest.seeResult);
        System.out.println(readTest.selfCorrecting);

        for (int i = 0; i < readTest.getAmountOfQuestions(); i++){
            System.out.println("Frågans ID: " + readTest.getActiveQuestionId().get(i));
            System.out.println("Fråga: " + readTest.getActiveQuestionText().get(i));
            System.out.println("Typ av fråga: " + readTest.getActiveQuestionType().get(0));
            System.out.println("Grade: " + readTest.getGradeOnActiveQuestion());
            System.out.println("NonGrade: " + readTest.getNonGradeOnActiveQuestion());
            for (int j = 0; j < readTest.getActiveAlternativeId().size(); j++){
                System.out.println("Alternativ till fråge ID: " + readTest.getActiveQuestionId().get(i));
                System.out.println("Alternativ: " + readTest.getActiveAlternativeText().get(j));
                System.out.println("Status på alternativ: " + readTest.activeAlternativeStatus.get(j));
            }
            if (i != (readTest.getAmountOfQuestions()-1)){
                readTest.getNextActiveQuestion();
            }
        }
    }

    //----TESTKOD-----
    public void getTest(int testId){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        setTestList(entityManager.createNamedQuery("getTest", Test.class).setParameter("testId", testId).getResultList());
        entityManager.close();
        entityManagerFactory.close();
    }

    public List<Test> getTestList() {
        return testList;
    }

    private void setTestList(List<Test> testList) {
        this.testList = testList;
    }

    //---TESTKOD-----
}