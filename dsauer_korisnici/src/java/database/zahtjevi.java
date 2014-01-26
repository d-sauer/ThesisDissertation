/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import datatype.userData;


/**
 *
 * @author sheky
 */
public class zahtjevi {

    /**
     * Kreiranje korisnika
     * Vrsta: 0
     */
    public static userData createUser(String ime, String prezime, String email, String korIme, String lozinka) {
        return sqlQuery.addUser(ime, prezime, email, korIme, lozinka);
        
    }
   

    /**
     * Autentifikacija korisnika i vraca sve podatke o korisniku, osim lozinke
     * Vrsta: 1
     */
    public static userData userAutentification(String korIme, String lozinka) {
        userData uData = sqlQuery.loginUser(korIme, lozinka);
        return uData;
    }

    public static userData userData(Integer id) {
        return sqlQuery.userData(id);        
    }


    public static boolean changePassword(Integer idKor, String newPass) {
        boolean change = sqlQuery.changePassword(idKor, newPass);        
        return change;
    }

    public static boolean changeEMail(Integer idKor, String newEMail) {
        boolean change = sqlQuery.changeEMail(idKor, newEMail);
        return change;
    }


    /*public static void main (String [] args) {
        userData ud = new userData();
        ud = userAutentification("dsauer", "dw0rk1n");
        System.out.println("user id:" + ud.getUserID().toString());
    }*/
    
}
