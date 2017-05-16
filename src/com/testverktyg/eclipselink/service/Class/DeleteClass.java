package com.testverktyg.eclipselink.service.Class;

import com.testverktyg.eclipselink.entity.Class;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/* Created by Daniel on 2017-05-16. */
public class DeleteClass {

    //deletes a class from the database with a specific className
    public void deleteClass(String className) {

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        List<Class> classIdList = entitymanager.createNamedQuery("FindClassId", Class.class).setParameter("className", className).getResultList();
        Class klass = entitymanager.find(Class.class, classIdList.get(0).getClassId());

        entitymanager.remove(klass);

        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();

    }

    public static void main(String[] args) {
        DeleteClass deleteClass =  new DeleteClass();
        deleteClass.deleteClass("Java2");
    }
}