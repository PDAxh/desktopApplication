package com.testverktyg.eclipselink.service.Test;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.testverktyg.eclipselink.entity.Test;
import com.testverktyg.eclipselink.entity.User;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        new CreatePDF().createPDF(DEST);


    }

    private void createPDF(String dest) throws DocumentException, IOException{

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        User user = entitymanager.find(User.class, 4); // get user who has id 19.
        Test test = entitymanager.find(Test.class,userId);
        firstname = user.getFirstname();
        lastname = user.getLastname();


        System.out.println(firstname + " " + lastname);



        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, new FileOutputStream("testpdf.pdf"));
        document.open();
        System.out.println(firstname + " " + lastname);
        Font font = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Paragraph headline = new Paragraph("Student Test Score", font );
        headline.setAlignment(Element.ALIGN_CENTER);
        document.add(headline);
        Paragraph emptySpace = new Paragraph(" ");
        document.add(emptySpace);
        document.add(emptySpace);
        document.add(new Paragraph(firstname+ " " + lastname)); // whys dosent this works?
        System.out.println("pdf has been created!");

        document.close();


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
