package com.testverktyg.eclipselink.service.user;

import com.testverktyg.eclipselink.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


/**
 * Created by Jonas Johansson, Java2, on 2017-05-02.
 *
 */
public class ReadUser {
    private String userName;
    private String password;
    private String group;
    private boolean loginStatus;

    public ReadUser(String username, String password){
        setLoginStatus(false);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<User> userList = entityManager.createNamedQuery("findByUsernameAndPassword", User.class)
                .setParameter("userName", username)
                .setParameter("password", password)
                .getResultList();

        for(User user : userList){
            setUserName(user.getUserName());
            setPassword(user.getPassword());
            setGroup(user.getTypeOfUser());
        }

        if((getUserName() != null) && (getPassword() != null)){
            setLoginStatus(true);
        }

        entityManager.close();
        entityManagerFactory.close();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
