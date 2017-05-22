package com.testverktyg.eclipselink.service.userTests;

import com.testverktyg.eclipselink.entity.UserTests;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Jonas Johansson on 2017-05-21.
 *
 */
public class DeleteUserTests {

    public DeleteUserTests(){}

    public void deleteUserFromUserTest(int testId, int userId){

            EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
            EntityManager entitymanager = emfactory.createEntityManager( );

            entitymanager.getTransaction( ).begin( );
            entitymanager.createNamedQuery("deleteUserTestFromUserTests", UserTests.class)
                    .setParameter("testId", testId)
                    .setParameter("userId", userId).executeUpdate();

            entitymanager.getTransaction( ).commit( );
            entitymanager.close();
            emfactory.close();
    }
}
