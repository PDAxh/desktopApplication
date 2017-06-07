package com.testverktyg.eclipselink.service.Test;

/* Created by Daniel on 2017-05-18. */
public class TimeCounter {

    private int testId;

    private void timeCounter() throws Exception {

        int testTime =30;
        int seconds = testTime * 60;
        while (seconds>0){
            System.out.println(seconds/3600 +":" +((seconds/60)%60) + ":" + (seconds%60));
            try {
                seconds--;
                Thread.sleep(1000);

            }
            catch (InterruptedException e) {
                System.out.println("något gick fel med klockan!" + e);
            }
        }
    }


    public static void main(String[] args) {

    TimeCounter timeCounter = new TimeCounter();
        try {
            timeCounter.timeCounter();
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

