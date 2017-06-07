package com.testverktyg.eclipselink.service.user;

/* Created by Daniel on 2017-05-08. */
import com.testverktyg.eclipselink.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DeleteUser {

    private int userId;

    public void DeleteSelectedUser() {

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );

        User user = entitymanager.find( User.class,userId  );
        entitymanager.remove(user);
        entitymanager.getTransaction( ).commit( );
        entitymanager.close( );
        emfactory.close( );
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}