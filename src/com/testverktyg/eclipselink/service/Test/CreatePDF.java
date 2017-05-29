package com.testverktyg.eclipselink.service.Test;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.testverktyg.eclipselink.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Daniel on 2017-05-24.
 */

public class CreatePDF {

    public static String DEST = "TestPDF";
    public static String getData = "getData";
    public int userId;
    public  String firstname;
    public  String lastname;


    public static void main(String[] args) throws DocumentException, IOException
    {
        new CreatePDF().createTeacherAndAdminPDF(DEST);
        new CreatePDF().createStudentPDF(DEST);

    }

    private void createStudentPDF(String dest)throws DocumentException {

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        User user = entitymanager.find(User.class, userId);

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("testStudentpdf.pdf"));
            System.out.println("testing 1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        System.out.println("testing 2");
        Font font = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Paragraph headline = new Paragraph("Student Test Score", font );
        headline.setAlignment(Element.ALIGN_CENTER);
        document.add(headline);
        Paragraph emptySpace = new Paragraph(" ");
        document.add(emptySpace);
        document.add(emptySpace);
        document.add(new Paragraph(firstname+ " " + lastname));
        Paragraph klass = new Paragraph("Klass: ");
        document.add(klass);
        Paragraph numbGquestion = new Paragraph("Antal Godkänd frågor:");
        document.add(numbGquestion);
        Paragraph numbVGquestion = new Paragraph("Antal Väl godkänd frågor:");
        document.add(numbVGquestion);


        document.close();
    }

    private void createTeacherAndAdminPDF(String dest) throws DocumentException, IOException{

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        User user = entitymanager.find(User.class, 4); // get user who has id 19.
        firstname = user.getFirstname();
        lastname = user.getLastname();


        System.out.println(firstname + " " + lastname);

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, new FileOutputStream("testpdf.pdf"));
        document.open();
        Font font = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Paragraph headline = new Paragraph("Student Test Score", font );
        headline.setAlignment(Element.ALIGN_CENTER);
        document.add(headline);
        Paragraph emptySpace = new Paragraph(" ");
        document.add(emptySpace);
        document.add(emptySpace);
        document.add(new Paragraph(firstname+ " " + lastname));
        Paragraph testName = new Paragraph("Test:");
        document.add(testName);
        Paragraph numbGquestion = new Paragraph("Antal Godkänd frågor:");
        document.add(numbGquestion);
        Paragraph numbVGquestion = new Paragraph("Antal Väl godkänd frågor:");
        document.add(numbVGquestion);
        Paragraph testTime = new Paragraph("Tid:");
        document.add(testTime);
        Paragraph averageScore = new Paragraph("Snittresultat:");
        document.add(averageScore);
        Paragraph maxScore = new Paragraph("maxpoäng:");
        document.add(maxScore);
        Paragraph numbOfStudentsTakenTest = new Paragraph("Antal studenter som gjort test:");
        document.add(numbOfStudentsTakenTest);
        Paragraph numbOfFail = new Paragraph("Antal IG:");
        document.add(numbOfFail);
        Paragraph numbOfPass = new Paragraph("Antal G:");
        document.add(numbOfPass);
        Paragraph numbOfHighPass = new Paragraph("Antal VG:");
        document.add(numbOfHighPass);




        document.close();

    }

    public void CreateStudentPDF(){



    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
