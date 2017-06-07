package com.testverktyg.eclipselink.service.studentAnswer;

import com.testverktyg.eclipselink.entity.StudentAnswer;

import javax.persistence.*;

/* Created by Andreas on 2017-05-22. */
public class CreateStudentAnswer {

    public void createNewStudentAnswer(int testId, int questionId, int alternativeId, int studentId){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        StudentAnswer sa = new StudentAnswer();
        sa.setTestId(testId);
        sa.setQuestionId(questionId);
        sa.setAlternativeId(alternativeId);
        sa.setStudentId(studentId);

        entitymanager.persist(sa);
        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }

    public static void main(String[] args) {
        CreateStudentAnswer csa = new CreateStudentAnswer();
        csa.createNewStudentAnswer(1,1,1,2);
        csa.createNewStudentAnswer(1,1,3,2);
        csa.createNewStudentAnswer(1,2,2,2);
    }
}

