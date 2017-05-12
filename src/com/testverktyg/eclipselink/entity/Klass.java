package com.testverktyg.eclipselink.entity;

import javax.persistence.*;

/**
 * Created by Daniel on 2017-05-11.
 */

@Entity
@NamedQueries({

        @NamedQuery(name = "findByKlass", query = "select u.klassNamn from Klass u where u.klassId = :qId")

})

public class Klass {

    @Id
    @SequenceGenerator(name="seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int klassId;

    private String klassNamn;

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
}
