package com.testverktyg.eclipselink.service.user;

import com.testverktyg.eclipselink.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/* Created by Daniel on 2017-05-05. */
public class CreateUser {

    public CreateUser(String fname, String lname, String password, String email, String Klass, String userType){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin();

        User user = new User( );
        user.setFirstname( fname );
        user.setLastname( lname );
        user.setPassword(password);
        user.setEmail( email );
        user.setKlass( Klass);
        user.setTypeOfUser(userType);

        entitymanager.persist( user );
        entitymanager.getTransaction( ).commit( );
        System.out.println("User "+user.getFirstname()+" saved");
        entitymanager.close( );
        emfactory.close( );
    }
}

