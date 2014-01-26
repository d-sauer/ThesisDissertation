package service;

import database.readProperties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import weather.weatherThread;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Web application lifecycle listener.
 * @author sheky
 */
public class ServletListener implements ServletContextListener {

    public static weatherThread wThread = null;

    public void contextInitialized(ServletContextEvent sce) {
        try {
            readProperties rp = new readProperties();
            System.out.println("ServletListener >> getRootPath: " + rp.getRoot());

            String wThreadStart = rp.readProp("weather.thread");
            if (wThreadStart.equals("true")) {
                System.out.println("Pokretanje dretve za update podataka o vremenskim prognozama");
                wThread = new weatherThread();
                wThread.startThread();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void contextDestroyed(ServletContextEvent sce) {
        if (wThread != null) {
            wThread.stopThread();
            System.out.println("Zaustavljanje dretve za update podataka o vremenskim prognozama");
        }
    }
}
