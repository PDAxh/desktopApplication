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

        int testid =0;
        int testTime =30;
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

