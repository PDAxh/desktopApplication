package com.testverktyg.eclipselink.service.Test;

import com.testverktyg.eclipselink.entity.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;


/**
 * Created by Daniel on 2017-05-18.
 */
public class TimeCounter {

    private int testId;

    public void TimeCounter() throws Exception {



        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();

        Scanner scanner = new Scanner(System.in);
        System.out.println("enter a test´s id to check the given time");
        int testid = scanner.nextInt();
        Test test = entitymanager.find(Test.class, testid);
        int testTime = test.getTimeForTestMinutes();

        int seconds = testTime * 60;
        while (seconds>0){
            System.out.println(seconds/3600 +":" +((seconds/60)%60) + ":" + (seconds%60));
            try {
                seconds--;
                Thread.sleep(1000);

            }
            catch (InterruptedException e) {

            }
        }
    }


    public static void main(String[] args) {

    TimeCounter timeCounter = new TimeCounter();
        try {
            timeCounter.TimeCounter();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }
}

