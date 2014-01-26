/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package weather;

import java.util.Date;
import database.readProperties;
import database.sqlQuery;
import database.zahtjevi;

/**
 *
 * @author sheky
 */
public class weatherThread extends Thread {

    private static boolean isThStart = false;
    private static boolean thStart = false;
    public static Integer thTime = 10000;
    public static boolean weatherUpdateComplete = false;

    private void weatherThread() {
    }

    public boolean getThreadStatus() {
        return thStart;
    }

    public void setThSleepTime(Integer time) {
        thTime = time;
    }

    public void setOptionThSleepTime() {
        readProperties readProp = new readProperties();
        String prop = readProp.readProp("thread.sleep");
        System.out.println("thread.sleep (min):" + prop);
        Integer time = Integer.parseInt(prop) * 1000 * 60; //minuta u milisekunde
        setThSleepTime(time);
    }

    public Integer getThSleepTime() {
        return thTime;
    }

    @Override
    public void run() {
        setOptionThSleepTime();
        readProperties readProp = new readProperties();
        isThStart = true;
        Integer archivedCity = sqlQuery.getNumberOfArchivedCity();
        Integer older_than = Integer.valueOf(readProp.readProp("weather.lastupdate")); //sec
        Integer checkTime = thTime / 1000; //sec
        Integer n = 0;
        Integer lenght = 50;

        if (archivedCity >= 50) {
            n = older_than / checkTime;
            lenght = n + 1;
        }

        while (thStart) {
            try {
                weatherUpdateComplete = false;
                //TODO weatherUpdateComplete = zahtjevi.refreshWeatherDataUSA();
                System.out.println("+ weatherThread: " + this.getName() + "   " + new Date().toString());
                n = zahtjevi.archiveWeatherForUSZipCode(older_than, lenght);
                System.out.println("  + acrhived data for: " + n.toString() + "(/" + archivedCity.toString() + ")  ZIP codes.");
   
                weatherUpdateComplete = true;
                weatherThread.sleep(thTime);
            } catch (InterruptedException ex) {
                System.out.println(">> Thread ERROR " + this.getName());
                thStart = false;
                ex.printStackTrace();
            }
        }
        isThStart = false;

        System.out.println(">> weatherThread: STOP " + this.getName());
    }

    public void startThread() {
        if (thStart == false) {
            thStart = true;
            System.out.println(">> weatherThread: START " + this.getName());
            start();
        } else {
            System.out.println(">> weatherThread: ALREDY STARTED " + this.getName());
        }
    }

    public void stopThread() {
        thStart = false; //zaustavlja dretvu
        Integer wait = 500;
        Integer ukupno = 0;
        System.err.println("stop thread provjera: " + this.isAlive());
        if (this.isAlive()) {
            while (isThStart == true) {
                try {
                    if (weatherUpdateComplete == true) { //ako je završio UPDATE vrem. podataka, moze prekinuti dretvu i ranije
                        interrupt();
                    }
                    ukupno += wait;
                    System.out.println(">> WAIT for weatherThread finish (" + ukupno.toString() + " ms) " + this.getName());



                    if (ukupno >= 30000) {
                        weatherUpdateComplete = true;
                        this.interrupt();
                    }
                    Thread.sleep(wait);
                } catch (InterruptedException ex) {
                }
            }
        } else {
            System.out.println(">> weatherThread: STOP " + this.getName());
        }
    }
}
