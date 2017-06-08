package com.testverktyg.eclipselink.service.Test;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 */

public class CreatePDF {

    private String firstname;
    private String lastname;
    private String testName;
    private int userId;
    private int totalGQuestions;
    private int totalVgQuestions;
    private int testTime;
    private int averagePoints;
    private int maxPoints;
    private int maxStudentsForTest;
    private int totalStudentDoneTest;
    private int totalIgStudents;
    private int totalGStudents;
    private int totalVgStudents;

    public void createStudentPDF(String StudentGPointsResultLabel, String StudentVGPointsResultLabel, String StudentTotalPointsResultLabel, String GradeResultLabelText)throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("testStudentpdf.pdf"));
        document.open();
        Font font = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Paragraph headline = new Paragraph("Provresultat", font );
        headline.setAlignment(Element.ALIGN_CENTER);
        document.add(headline);
        Paragraph emptySpace = new Paragraph(" ");
        document.add(emptySpace);
        document.add(emptySpace);
        document.add(new Paragraph("Antal G poäng: " + StudentGPointsResultLabel));
        document.add(new Paragraph("Antal VG poäng: " + StudentVGPointsResultLabel));
        document.add(new Paragraph("Totalt antal poäng: " + StudentTotalPointsResultLabel));
        document.add(new Paragraph("Betyg: " + GradeResultLabelText));

        document.close();
    }

    public void createTeacherAndAdminPDF() throws DocumentException, IOException{
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream("testpdf.pdf"));
        document.open();
        Font font = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Paragraph headline = new Paragraph("Statistik för prov", font );
        headline.setAlignment(Element.ALIGN_CENTER);
        document.add(headline);
        Paragraph emptySpace = new Paragraph(" ");
        document.add(emptySpace);
        document.add(emptySpace);
        document.add(new Paragraph("Test: " + getTestName()));
        document.add(new Paragraph("Antal Godkänd frågor: " + getTotalGQuestions()));
        document.add(new Paragraph("Antal Väl Godkänd frågor: " + getTotalVgQuestions()));
        document.add(new Paragraph("Tid:" + getTestTime()));
        document.add(new Paragraph("Snittresultat: " + getAveragePoints()));
        document.add(new Paragraph("Maxpoäng: " + getMaxPoints() ));
        document.add(new Paragraph("Antal studenter som gjort test: " + getTotalStudentDoneTest()));
        document.add(new Paragraph("Max antal studenter: " + getMaxStudentsForTest()));
        document.add(new Paragraph("Antal IG: " + getTotalIgStudents()));
        document.add(new Paragraph("Antal G: " + getTotalGStudents()));
        document.add(new Paragraph("Antal VG: " + getTotalVgStudents()));
        document.close();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    private String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    private int getTotalGQuestions() {
        return totalGQuestions;
    }

    public void setTotalGQuestions(int totalGQuestions) {
        this.totalGQuestions = totalGQuestions;
    }

    private int getTotalVgQuestions() {
        return totalVgQuestions;
    }

    public void setTotalVgQuestions(int totalVgQuestions) {
        this.totalVgQuestions = totalVgQuestions;
    }

    private int getTestTime() {
        return testTime;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    private int getAveragePoints() {
        return averagePoints;
    }

    public void setAveragePoints(int averagePoints) {
        this.averagePoints = averagePoints;
    }

    private int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    private int getTotalStudentDoneTest() {
        return totalStudentDoneTest;
    }

    public void setTotalStudentDoneTest(int totalStudentDoneTest) {
        this.totalStudentDoneTest = totalStudentDoneTest;
    }

    private int getTotalIgStudents() {
        return totalIgStudents;
    }

    public void setTotalIgStudents(int totalIgStudents) {
        this.totalIgStudents = totalIgStudents;
    }

    private int getTotalGStudents() {
        return totalGStudents;
    }

    public void setTotalGStudents(int totalGStudents) {
        this.totalGStudents = totalGStudents;
    }

    private int getTotalVgStudents() {
        return totalVgStudents;
    }

    public void setTotalVgStudents(int totalVgStudents) {
        this.totalVgStudents = totalVgStudents;
    }

    private String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    private int getMaxStudentsForTest() {
        return maxStudentsForTest;
    }

    public void setMaxStudentsForTest(int maxStudentsForTest) {
        this.maxStudentsForTest = maxStudentsForTest;
    }
}
