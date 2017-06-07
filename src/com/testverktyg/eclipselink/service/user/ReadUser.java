package com.testverktyg.eclipselink.service.user;

import com.testverktyg.eclipselink.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/* Created by Jonas Johansson, Java2, on 2017-05-02. */

public class ReadUser {

    private String email;
    private String password;
    private String group;
    private boolean loginStatus;
    //private List<User> studentListFromSpecificClass;
    private List<User> studentList;
    private List<User> teacherList;
    private List<User> adminList;
    private List<User> userLoggedIn;
    private int userId;

    private List<User> userIdByClassList;
    private List<User> findClassWithUserIdList;

    public ReadUser(){}

    public ReadUser(String email, String password){
        setLoginStatus(false);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<User> userList = entityManager.createNamedQuery("findByEmailAndPassword", User.class).setParameter("email", email).setParameter("password", password).getResultList();

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
    }

    //Never used according to intellij
    /*public void readOnlyStudentsInClass(String selectedClass){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        studentListFromSpecificClass = entityManager.createNamedQuery("FindUserByTypeAndClass", User.class).setParameter("typeOfUser", "student").setParameter("selectedClass", selectedClass).getResultList();
    }*/

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

    public void getUserIdByClass(String klass){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        setUserIdByClassList(entityManager.createNamedQuery("findUserIdByClass", User.class).setParameter("klass", klass).getResultList());

        entityManager.close();
        entityManagerFactory.close();
    }

    public void getFindClassWithUserId(int userId){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        setFindClassWithUserIdList(entityManager.createNamedQuery("findClassWithUserId", User.class).setParameter("userId", userId).getResultList());

        entityManager.close();
        entityManagerFactory.close();
    }

    public List<User> getUserIdByClassList() {
        return userIdByClassList;
    }

    private void setUserIdByClassList(List<User> userIdByClassList) {
        this.userIdByClassList = userIdByClassList;
    }

    public List<User> getFindClassWithUserIdList() {
        return findClassWithUserIdList;
    }

    private void setFindClassWithUserIdList(List<User> findClassWithUserIdList) {
        this.findClassWithUserIdList = findClassWithUserIdList;
    }

    private String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public String getGroup() {
        return group;
    }

    private void setGroup(String group) {
        this.group = group;
    }

    private void setLoginStatus(Boolean status){
        this.loginStatus = status;
    }

    public boolean getLoginStatus(){
        return this.loginStatus;
    }

    /*public List<User> getStudentListFromSpecificClass() {
        return studentListFromSpecificClass;
    }*/

    public List<User> getStudentList() {
        return studentList;
    }

    public List<User> getTeacherList() {
        return teacherList;
    }

    public List<User> getAdminList() {
        return adminList;
    }

    public void setLoggedInUser(int userid){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        userLoggedIn = entityManager.createNamedQuery("FindLoggedInUser", User.class).setParameter("uId", userid).getResultList();
    }

    public List<User> getLoggedInUser() {
        return userLoggedIn;
    }

    public int getUserId() {
        return userId;
    }

    private void setUserId(int userId) {
        this.userId = userId;
    }
}
