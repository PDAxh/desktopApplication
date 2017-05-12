package com.testverktyg.eclipselink.service.user;

import com.testverktyg.eclipselink.entity.Klass;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Daniel on 2017-05-09.
 */


public class CreateGroup {

    private String firstname;
    private String lastname;
    private String Klass;

    public CreateGroup(String firstname, String lastname, String Klass){

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
        EntityManager entityManager = emfactory.createEntityManager( );
        entityManager.getTransaction( ).begin( );

        //User user = new User();
        //Klass klass = new Klass();

        List<Klass> klassList = entityManager.createNamedQuery("findByKlass",Klass.class)
                .setParameter("firstname",firstname)
                .setParameter("lastname",lastname)
                .setParameter("klass",Klass).getResultList();

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getKlass() {
        return Klass;
    }

    public void setKlass(String klass) {
        Klass = klass;
    }
}



