package com.testverktyg.eclipselink.entity;

import javax.persistence.*;

/**
 * Created by Jonas Johansson on 2017-05-02.
 *
 */

 @Entity
 @NamedQueries({
         @NamedQuery(query = "SELECT e FROM User e WHERE e.userName = :userName", name = "findByUserName"),
         @NamedQuery(query = "SELECT e FROM User e WHERE e.userName = :userName AND e.password = :password", name="findByUsernameAndPassword")
 })
 public class User {

    @Id
    @SequenceGenerator(name="seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int userId;

    private String password;
    private String userName;
    private String typeOfUser;

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
