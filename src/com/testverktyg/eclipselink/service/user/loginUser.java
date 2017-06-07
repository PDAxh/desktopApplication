package com.testverktyg.eclipselink.service.user;

import com.testverktyg.eclipselink.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/* Created by Jonas Johansson on 2017-04-28. */
public class loginUser {

    public loginUser(String email, String password, String group){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        User user = new User();
        user.setTypeOfUser(group);
        user.setPassword(password);
        user.setEmail(email);

        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
