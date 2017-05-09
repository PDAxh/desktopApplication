package com.testverktyg.eclipselink.service.user;

/**
 * Created by Daniel on 2017-05-08.
 */
import com.testverktyg.eclipselink.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DeleteUser {
    public static void main( String[ ] args ) {

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );

        // TODO make function that connects id to entitymanager.find. on row below.
        // TODO function  that will select person via id?

        User user = entitymanager.find( User.class, 1 );
        entitymanager.remove(user);
        // TESYAR D`FÖR SYNK
        entitymanager.getTransaction( ).commit( );
        entitymanager.close( );
        emfactory.close( );
    }

}