package com.testverktyg.eclipselink.service.userTests;

import com.testverktyg.eclipselink.entity.UserTests;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/* Created by Jonas Johansson on 2017-05-15. */
public class CreateUserTests {

    private int testId;
    private int userId;

    public CreateUserTests(){}

    public void commitTestToUser(){
        EntityManagerFactory entityManagerFactory  = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        UserTests userTests = new UserTests();
        userTests.setTestId(getTestId());
        userTests.setUserId(getUserId());

        entityManager.persist(userTests);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    private int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    private int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
