package com.testverktyg.eclipselink.entity;

import javax.persistence.*;

/* Created by Daniel on 2017-05-11. */

@Entity
@NamedQueries({
        @NamedQuery(query = "SELECT e FROM User e WHERE e.firstname = :firstname AND e.lastname = :lastname  AND e.Klass = :klass", name= "findByKlass"),
        @NamedQuery(query = "SELECT c FROM Class c WHERE c.className = :className", name= "FindClassId")
})

public class Class {

    @Id
    @SequenceGenerator(name="seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int classId;

    private String className;

    public int getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}