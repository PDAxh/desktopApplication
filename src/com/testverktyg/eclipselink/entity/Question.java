package com.testverktyg.eclipselink.entity;

import javax.persistence.*;
import java.util.List;

/*Created by jennifergisslow on 2017-05-08.*/

@Entity
@NamedQueries({
        @NamedQuery(name = "FindQuestionText", query = "select q.questionText from Question q where q.testId = :tId"),
        @NamedQuery(name = "FindQuestionId", query = "select q.questionId from Question q where q.testId = :tId"),
        @NamedQuery(name = "FindQuestionGradeG", query = "select q.gradeG from Question q where q.questionId = :qId"),
        @NamedQuery(name = "FindQuestionGradeVG", query = "select q.gradeG from Question q where q.questionId = :qId"),
        @NamedQuery(name = "FindQuestionType", query = "select q.typeOfQuestion from Question q where q.questionId = :qId")
})

public class Question {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "seq")
    private int questionId;
    private int testId;
    private String typeOfQuestion;
    private String questionText;
    private boolean gradeG;
    private boolean gradeVG;
    private int points;

    @OneToMany( targetEntity = Alternative.class)
    private List<Alternative> alternativeList;

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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Alternative> getAlternativeList(){
        return alternativeList;
    }

    public void setAlternativeList(List alternativeList){
        this.alternativeList = alternativeList;
    }
}
