package com.testverktyg.eclipselink.entity;

import javax.persistence.*;

/**
 * Created by Jonas Johansson on 2017-05-02.
 *
 */

 @Entity
 @NamedQueries({
         @NamedQuery(query = "SELECT e FROM User e WHERE e.email = :email", name = "findByEmail"),
         @NamedQuery(query = "SELECT e FROM User e WHERE e.email = :email AND e.password = :password", name= "findByEmailAndPassword")
 })
 public class User {

    @Id
    @SequenceGenerator(name="seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int userId;

    private String password;
    private String email;
    private String typeOfUser;
    private String firstname;
    private String lastname;
    private String Klass;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getKlass() {
        return Klass;
    }

    public void setKlass(String klass) {
        Klass = klass;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
