package com.testverktyg.eclipselink.service.user;

import com.testverktyg.eclipselink.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jonas Johansson, Java2, on 2017-05-02.
 *
 */
public class ReadUser {

    private String email;
    private String password;
    private String group;
    private boolean loginStatus;
    List<User> studentListFromSpecificClass;
    List<User> studentList;
    List<User> teacherList;
    List<User> adminList;
    private int userId;

    public ReadUser(){}

    public ReadUser(String email, String password){
        setLoginStatus(false);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();


        List<User> userList = entityManager.createNamedQuery("findByEmailAndPassword", User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList();

        for(User user : userList){
            setEmail(user.getEmail());
            setPassword(user.getPassword());
            setGroup(user.getTypeOfUser());
            setUserId(user.getUserId());
        }

        if((getEmail() != null) && (getPassword() != null)){
            setLoginStatus(true);
        }

        entityManager.close();
        entityManagerFactory.close();

    }

    public void readOnlyStudents(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        studentList = entityManager.createNamedQuery("FindUserByType", User.class).setParameter("typeOfUser", "student").getResultList();

        /*
        //Use this in the GUI to collect students
        for (int i = 0; i < studentList.size(); i++){
            System.out.println(studentList.get(i).getFirstname() + " " + studentList.get(i).getLastname());
        }
        */
    }
    public void readOnlyStudentsInClass(String selectedClass){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        studentListFromSpecificClass = entityManager.createNamedQuery("FindUserByTypeAndClass", User.class).setParameter("typeOfUser", "student").setParameter("selectedClass", selectedClass).getResultList();
    }

    public void readOnlyTeacher(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        teacherList = entityManager.createNamedQuery("FindUserByType", User.class).setParameter("typeOfUser", "teacher").getResultList();
    }

    public void readOnlyAdmin(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        adminList = entityManager.createNamedQuery("FindUserByType", User.class).setParameter("typeOfUser", "admin").getResultList();
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setLoginStatus(Boolean status){
        this.loginStatus = status;
    }

    public boolean getLoginStatus(){
        return this.loginStatus;
    }

    public List<User> getStudentListFromSpecificClass() {
        return studentListFromSpecificClass;
    }

    public List<User> getStudentList() {
        return studentList;
    }

    public List<User> getTeacherList() {
        return teacherList;
    }

    public List<User> getAdminList() {
        return adminList;
    }

    public static void main(String[] args) {
        ReadUser readUser = new ReadUser();

        readUser.readOnlyAdmin();
    }

    public int getUserId() {
        return userId;
    }

    private void setUserId(int userId) {
        this.userId = userId;
    }
}
