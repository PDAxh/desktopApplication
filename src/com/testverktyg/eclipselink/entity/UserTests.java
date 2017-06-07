package com.testverktyg.eclipselink.entity;

import javax.persistence.*;

/* Created by Jonas Johansson on 2017-05-15.
* Håller koll på vilka test som varje användare kan komma åt.
*
* */

@Entity
@NamedQueries({
        @NamedQuery(query = "SELECT e FROM UserTests e WHERE e.userId = :userId", name = "findAllTestForUser"),
        @NamedQuery(query = "DELETE FROM UserTests e WHERE e.testId = :testId AND e.userId = :userId", name="deleteUserTestFromUserTests"),
        @NamedQuery(query = "DELETE FROM UserTests e WHERE e.testId = :testId", name="deleteTestFromUserTests"),
        @NamedQuery(query = "SELECT e FROM UserTests  e WHERE  e.testId = :testId", name="findAllUsersForOneTest")
})
public class UserTests {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int userTestsId;
    private int userId;
    private int testId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }
}
