package com.testverktyg.eclipselink.service.userTests;

import com.testverktyg.eclipselink.entity.UserTests;
import com.testverktyg.eclipselink.service.user.ReadUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Grodfan on 2017-05-15.
 *
 */
public class ReadUserTests {

    private List<UserTests> userTestsList;
    private List<UserTests> userTestListByTestId;

    public ReadUserTests(){}

    public ReadUserTests(int userId){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        setUserTestsList(entityManager.createNamedQuery("findAllTestForUser", UserTests.class)
                .setParameter("userId", userId)
                .getResultList());

        entityManager.close();
        entityManagerFactory.close();
    }

    public List<UserTests> getUserTestsList() {
        return userTestsList;
    }

    private void setUserTestsList(List<UserTests> userTestsList) {
        this.userTestsList = userTestsList;
    }

    public void findAllUsersForOneTest(int testId){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        setUserTestListByTestId(entityManager.createNamedQuery("findAllUsersForOneTest", UserTests.class)
                .setParameter("testId", testId)
                .getResultList());

        entityManager.close();
        entityManagerFactory.close();
    }

    public List<UserTests> getUserTestListByTestId() {
        return userTestListByTestId;
    }

    private void setUserTestListByTestId(List<UserTests> userTestListByTestId) {
        this.userTestListByTestId = userTestListByTestId;
    }
}
