package com.testverktyg.eclipselink.entity;

import javax.persistence.*;

/**
 * Created by Grodfan on 2017-05-15.
 *
 */

@Entity
@NamedQueries({
        @NamedQuery(query = "SELECT e FROM UserTests e WHERE e.userId = :userId", name = "findAllTestForUser"),
        @NamedQuery(query = "DELETE FROM UserTests e WHERE e.testId = :testId AND e.userId = :userId", name="deleteUserTestFromUserTests")
})
public class UserTests {

    @Id
    @SequenceGenerator(name="seq", initialValue = 1, allocationSize = 1)
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
