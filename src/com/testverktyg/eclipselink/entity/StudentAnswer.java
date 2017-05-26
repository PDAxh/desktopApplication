package com.testverktyg.eclipselink.entity;

import javax.persistence.*;

/**
 * Created by Andreas on 2017-05-22.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="getStudentAnswer", query = "select s from StudentAnswer s where s.studentId = :sId and s.testId = :tId"),
        @NamedQuery(name="getAllStudentsAnswers", query = "select s from StudentAnswer s where s.testId = :tId")
})
public class StudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int studentAnswerId;
    private int testId;
    private int questionId;
    private int alternativeId;
    private int studentId;

    public int getStudentAnswerId() {
        return studentAnswerId;
    }

    public void setStudentAnswerId(int studentAnswerId) {
        this.studentAnswerId = studentAnswerId;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAlternativeId() {
        return alternativeId;
    }

    public void setAlternativeId(int alternativId) {
        this.alternativeId = alternativId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
