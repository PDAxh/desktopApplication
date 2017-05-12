package com.testverktyg.eclipselink.entity;

import javax.persistence.*;

/**
 * Created by Daniel on 2017-05-11.
 */

@Entity
@NamedQueries({

        @NamedQuery(query = "SELECT e FROM User e WHERE e.firstname = :firstname AND e.lastname = :lastname  AND e.Klass = :klass", name= "findByKlass")
})

public class Klass {

    @Id
    @SequenceGenerator(name="seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int klassId;

    private String klassNamn;
    private String firstname;
    private String lastname;
    private String Klass;

    public int getKlassId() {
        return klassId;
    }

    public void setKlassId(int klassId) {
        this.klassId = klassId;
    }

    public String getKlass() {
        return klassNamn;
    }

    public void setKlass(String klass) {
        this.klassNamn = klassNamn;
    }

    public String getKlassNamn() {
        return klassNamn;
    }

    public void setKlassNamn(String klassNamn) {
        this.klassNamn = klassNamn;
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
}
