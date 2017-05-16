package com.testverktyg.eclipselink.service.Class;

import com.testverktyg.eclipselink.entity.Class;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/* Created by jennifergisslow on 2017-05-16. */

public class ReadClass {

    List<Class> classNameList;

    //reads all the classes from the database
    public void readAllClasses(){

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        classNameList = entitymanager.createNamedQuery("FindAllClassNames", Class.class).getResultList();

    }

    public void readOneClass(){}


    public List<Class> getClassNameList() {
        return classNameList;
    }

    public static void main(String[] args) {
        ReadClass readClass = new ReadClass();

        readClass.readAllClasses();

        for (int i = 0; i < readClass.classNameList.size(); i++){
            System.out.println(readClass.classNameList.get(i));
        }
    }
}
