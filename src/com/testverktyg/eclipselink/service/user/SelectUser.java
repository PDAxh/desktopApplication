//package com.testverktyg.eclipselink.service.user;
//
//import com.testverktyg.eclipselink.entity.User;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import java.util.List;
//
///**
// * Created by Daniel on 2017-05-09.
// */
//public class SelectGroup {
//
//    public static void main(String args[]){
//
//        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
//        EntityManager entityManager = emfactory.createEntityManager( );
//        entityManager.getTransaction( ).begin( );
//
//        User user = new User();
//
//        List<User> userList = entityManager.createNamedQuery("findByKlass", User.class).setParameter("email", klass).getResultList();
//        for(User user : userList){
//            setEmail(user.getEmail());
//            setPassword(user.getPassword());
//            setGroup(user.getTypeOfUser());
//        }
//
//
//
//    }
//}
//
//
