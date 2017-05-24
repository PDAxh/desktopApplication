package com.testverktyg.eclipselink.service.Class;

import com.testverktyg.eclipselink.entity.Class;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/* Created by jennifergisslow on 2017-05-16. */

public class ReadClass {

    private List<Class> classNameList;

    //reads all the classes from the database
    public void readAllClasses(){

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

       setClassNameList(entitymanager.createNamedQuery("FindAllClassNames", Class.class).getResultList());

        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }

    public List<Class> getClassNameList() {
        return classNameList;
    }

    private void setClassNameList(List<Class> classNameList) {
        this.classNameList = classNameList;
    }


    // public void readOneClass(){}
    /*    public static void main(String[] args) {
        ReadClass readClass = new ReadClass();

        readClass.readAllClasses();

        for (int i = 0; i < readClass.classNameList.size(); i++){
            System.out.println(readClass.classNameList.get(i));
        }
    }*/
}
