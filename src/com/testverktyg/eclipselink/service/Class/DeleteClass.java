package com.testverktyg.eclipselink.service.Class;

import com.testverktyg.eclipselink.entity.Class;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/* Created by Daniel on 2017-05-16. */
public class DeleteClass {

    public void deleteClass(int classId) {


        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Class klass = entitymanager.find(Class.class, classId);

        entitymanager.remove(klass);

        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();

    }

    public static void main(String[] args) {
        DeleteClass deleteClass =  new DeleteClass();
        deleteClass.deleteClass(3);
    }
}