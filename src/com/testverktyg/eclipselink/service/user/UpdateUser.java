package com.testverktyg.eclipselink.service.user;

/**
 * Created by Daniel on 2017-05-15
 */
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.testverktyg.eclipselink.entity.User;

import java.awt.*;
import java.util.Scanner;

public class UpdateUser {

    private int userId;
    private String newfirstname;
    private String newLastname;
    private String newEmail;
    private String newPassword;
    private String newKlass;
    private String newTypeOfUser;


    public void UpdateUser() {

        Scanner scanner = new Scanner(System.in);
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        User user = entitymanager.find(User.class, userId);

        user.setFirstname(newfirstname);
        user.setLastname(newLastname);
        user.setEmail(newEmail);
        user.setPassword(newPassword);
        user.setKlass(newKlass);
        user.setTypeOfUser(newTypeOfUser);

        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }
    

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNewfirstname() {
        return newfirstname;
    }

    public void setNewfirstname(String newfirstname) {
        this.newfirstname = newfirstname;
    }

    public String getNewLastname() {
        return newLastname;
    }

    public void setNewLastname(String newLastname) {
        this.newLastname = newLastname;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewKlass() {
        return newKlass;
    }

    public void setNewKlass(String newKlass) {
        this.newKlass = newKlass;
    }

    public String getNewTypeOfUser() {
        return newTypeOfUser;
    }

    public void setNewTypeOfUser(String newTypeOfUser) {
        this.newTypeOfUser = newTypeOfUser;
    }
}
