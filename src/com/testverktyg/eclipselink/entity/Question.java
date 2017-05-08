package com.testverktyg.eclipselink.entity;

import javax.persistence.*;
import java.util.List;

/*Created by jennifergisslow on 2017-05-08.*/

@Entity
public class Question {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "seq")
    private int questionId;
    private int testId;
    private String typeOfQuestion;
    private String questionText;
    private boolean gradeG;
    private boolean gradeVG;

    @OneToMany( targetEntity = Alternative.class)
    private List alternativeList;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        questionId = questionId;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(String typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public boolean isGradeG() {
        return gradeG;
    }

    public void setGradeG(boolean gradeG) {
        this.gradeG = gradeG;
    }

    public boolean isGradeVG() {
        return gradeVG;
    }

    public void setGradeVG(boolean gradeVG) {
        this.gradeVG = gradeVG;
    }

    public List getAlternativeList(){
        return alternativeList;
    }

    public void setAlternativeList(List alternativeList){
        this.alternativeList = alternativeList;
    }
}
