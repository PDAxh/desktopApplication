package com.testverktyg.eclipselink.entity;

import javax.persistence.*;
import java.util.List;

/*Created by jennifergisslow on 2017-05-08.*/

@Entity
public class Test {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "seq" )
    private int testId;
    private String testName;
    private String testDescription;
    private boolean selfCorrecting;
    private boolean SeeResultAfter;
    private String lastDate;
    private int timeForTestMinutes;

    @OneToMany( targetEntity = Question.class)
    private List questionList;

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public boolean isSelfCorrecting() {
        return selfCorrecting;
    }

    public void setSelfCorrecting(boolean selfCorrecting) {
        this.selfCorrecting = selfCorrecting;
    }

    public boolean isSeeResultAfter() {
        return SeeResultAfter;
    }

    public void setSeeResultAfter(boolean seeResultAfter) {
        SeeResultAfter = seeResultAfter;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public int getTimeForTestMinutes() {
        return timeForTestMinutes;
    }

    public void setTimeForTestMinutes(int timeForTestMinutes) {
        this.timeForTestMinutes = timeForTestMinutes;
    }

    public List getQuestionList(){
        return questionList;
    }

    public void setQuestionList(List questionList){
        this.questionList = questionList;
    }
}
