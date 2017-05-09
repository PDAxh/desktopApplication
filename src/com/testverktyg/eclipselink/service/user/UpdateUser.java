package com.testverktyg.eclipselink.service.user;

/**
 * Created by Daniel on 2017-05-08.
 */
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.testverktyg.eclipselink.entity.User;

public class UpdateUser {

    public static void main( String[ ] args ) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );

        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );

        // TODO make function that connects id to entitymanager.find. on row below.

        User user = entitymanager.find( User.class,1 );

        System.out.println( user );
        user.setTypeOfUser("student"); // changs the existing tyope to user.
        entitymanager.getTransaction( ).commit( );


        System.out.println( user );
        entitymanager.close();
        emfactory.close();
    }

}