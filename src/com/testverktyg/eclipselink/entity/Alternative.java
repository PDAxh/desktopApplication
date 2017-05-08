package com.testverktyg.eclipselink.entity;

import javax.persistence.*;

/*Created by jennifergisslow on 2017-05-08.*/

@Entity
public class Alternative {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "seq" )
    private int alternativeId;
    private int questionId;
    private boolean alternativeStatus;
    private String alternativeText;

    public int getAlternativeId() {
        return alternativeId;
    }

    public void setAlternativeId(int alternativeId) {
        this.alternativeId = alternativeId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public boolean isAlternativeStatus() {
        return alternativeStatus;
    }

    public void setAlternativeStatus(boolean alternativeStatus) {
        this.alternativeStatus = alternativeStatus;
    }

    public String getAlternativeText() {
        return alternativeText;
    }

    public void setAlternativeText(String alternativeText) {
        this.alternativeText = alternativeText;
    }
}
