package com.testverktyg.eclipselink.service.user;

import com.testverktyg.eclipselink.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Grodfan on 2017-04-28.
 */
public class loginUser {

    private String email;
    private String password;
    private String group;

    public loginUser(String email, String password, String group){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
       // EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        User user = new User();
        user.setTypeOfUser(group);
        user.setPassword(password);
        user.setEmail(email);

        //user.setGroup(getGroup());

        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setGroup(String group) {
//        this.group = group;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public String getGroup() {
//        return group;
//    }
}
