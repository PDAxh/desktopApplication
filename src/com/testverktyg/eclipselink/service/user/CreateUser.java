package com.testverktyg.eclipselink.service.user;

import com.sun.xml.internal.bind.v2.schemagen.episode.Klass;
import com.testverktyg.eclipselink.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

/**
 * Created by Daniel on 2017-05-05.
 */
public class CreateUser {
    String fname = "";
    String lname = "";
    String password = "";
    String email = "";
    String Klass = "";
    String userType="";

    public CreateUser(String fname, String lname, String password, String email, String Klass, String userType){
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.email = email;
        this.Klass = Klass;
        this.userType = userType;

    EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
    EntityManager entitymanager = emfactory.createEntityManager( );
    entitymanager.getTransaction( ).begin();

    Scanner scanner = new Scanner(System.in);


////      run in console
//
//        System.out.println("Here we create new user");
//        System.out.println("firstname: ");
//        String fname = scanner.next();
//        System.out.println("lastname: ");
//        String lname = scanner.next();
//        System.out.println("password");
//        String password = scanner.next();
//        System.out.println("Email: ");
//        String email = scanner.next();
//        System.out.println("Class: ");
//        String Klass = scanner.next();
//        System.out.println("Typ: ");
//        String userType = scanner.next();

    // ONly this below  is needed for the GUI
        User user = new User( );
        user.setFirstname( fname );
        user.setLastname( lname );
        user.setPassword(password);
        user.setEmail( email );
        user.setKlass( Klass);
        user.setTypeOfUser(userType);

        entitymanager.persist( user );
        entitymanager.getTransaction( ).commit( );

        entitymanager.close( );
        emfactory.close( );
}
}

