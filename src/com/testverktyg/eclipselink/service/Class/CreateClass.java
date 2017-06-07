package com.testverktyg.eclipselink.service.Class;

/* Created by Daniel on 2017-05-16. */
import com.testverktyg.eclipselink.entity.Class;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CreateClass {

    //Creates a new class to the database
    public void CreateClass(String klassName){

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin();

        Class klass = new Class();

        klass.setClassName(klassName);

        entitymanager.persist(klass);
        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();

    }

}
