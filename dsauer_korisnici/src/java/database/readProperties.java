/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 *
 * @author sheky
 */
public class readProperties {

    public static String propFile = "korisnici.properties";
    public Properties prop = null;

    public String getRoot() {
        URL url = null;
        String path = null;

        try {
            url = this.getClass().getClassLoader().getResource("/");
            path = url.toString();
        } catch (Exception ex) {
            url = this.getClass().getResource("/");
            path = url.toString();
        }
        return path;
    }

    public Properties readPropFile() {
        prop = new Properties();
        URL url = null;
        File file = null;
        String path = null;

        String rootPath = null;

        url = this.getClass().getResource("/");
        rootPath = url.toString();
        path = url.toString();
        path = path.substring(0, path.lastIndexOf("/")); //jedan folder prema gore
        path = path.substring(0, path.lastIndexOf("/"));
        path = path.substring(0, path.lastIndexOf("/")) + "/META-INF/" + propFile;

        try {
            url = new URL(path);
            file = new File(url.toURI());
        } catch (URISyntaxException ex) {
            System.out.println("error: property file, URI");
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            System.out.println("error: property file, URL");
        }

        System.out.println("Using property file: " + path);


        FileInputStream fis = null;
        if (file.exists()) {
            try {
                fis = new FileInputStream(file);
                prop.load(fis);
                fis.close();
                return prop;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("error: Properties file don't exist!");
            System.out.println("root path (url): " + rootPath);
            System.out.println("properties path (url): " + path);
        }
        return null;
    }


    public String readProp(String naziv) {
        if(prop == null) {
            prop = readPropFile();
        }
        return prop.getProperty(naziv).toString();
    }

    /*public static void main(String [] args) {
    readProperties rp = new readProperties();
    System.out.println("prop:" + rp.readProp("db.name"));
    System.out.println("prop:" + rp.readProp("db.user"));
    }*/
}
