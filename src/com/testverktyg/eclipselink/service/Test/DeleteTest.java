package com.testverktyg.eclipselink.service.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.testverktyg.eclipselink.entity.Test;

/* Created by jennifergisslow on 2017-05-12. */

public class DeleteTest {

    public void deleteTest(int testId){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
        EntityManager entitymanager = emfactory.createEntityManager( );

        entitymanager.getTransaction( ).begin( );
        Test test = entitymanager.find(Test.class, testId);
        entitymanager.remove(test);

        entitymanager.getTransaction( ).commit( );
        entitymanager.close();
        emfactory.close();
    }
}
