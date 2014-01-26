/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import datatype.userData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author sheky
 */
public class sqlQuery {

    /**
     * Dodavanje novog korisnika
     */
    public static Integer isUserExist(String korIme, String email) {
        try {
            email = email.trim();
            korIme = korIme.trim();
            String sql = null;
            PreparedStatement sqlStat = null;
            Connection conn = database.getDBConnection();

            if (email.length() != 0) {
                sql = "SELECT COUNT(*) FROM user WHERE korIme=? OR email=?";
                sqlStat = conn.prepareStatement(sql);
                sqlStat.setString(1, korIme);
                sqlStat.setString(2, email);
            } else {
                sql = "SELECT COUNT(*) FROM user WHERE korIme=?";
                sqlStat = conn.prepareStatement(sql);
                sqlStat.setString(1, korIme);
            }


            ResultSet rs = sqlStat.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) == 0) {
                    return 0; //false - korisnik nepostoji
                } else {
                    return -1; //true - korisnik postoji
                }
            } else {
                return -2;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -3; //pogreska prilikom izvrsavanja sql upita
        }
    }

    /**
     * Dodavanje novog korisnika
     * -1 postojece korisnicko ime ILI korisnik s identicnom E-Mail adresom
     * null - pogreška prilikom izvođenja upita
     */
    public static userData addUser(String ime, String prezime, String email, String korIme, String lozinka) {
        Integer i = isUserExist(korIme, email);
        userData uData = new userData();
        if (i < 0) {
            if (i == -1) {
                uData.setErrCode(5);
            }
            if (i == -2 || i == -3) {
                uData.setErrCode(6);
            }            
            return uData;
        }

        try {
            String sql = "INSERT INTO user(ime, prezime, email, korIme, lozinka) VALUES (?,?,?,?,?);";
            Connection conn = database.getDBConnection();
            PreparedStatement sqlStat = conn.prepareStatement(sql);
            sqlStat.setString(1, ime);
            sqlStat.setString(2, prezime);
            sqlStat.setString(3, email);
            sqlStat.setString(4, korIme);
            sqlStat.setString(5, lozinka);
            sqlStat.execute();
            ResultSet rs = sqlStat.executeQuery("SELECT LAST_INSERT_ID();");
            if (rs.next()) {
                uData.setUserID(rs.getInt(1));
                uData.setEmail(email);
                uData.setUserName(korIme);
                uData.setFirstName(ime);
                uData.setLastName(prezime);
                uData.setMessage("Info: Korisnik uspješno dodan.");
                
                return uData;
            } else {
                uData.setErrCode(6);                
                return uData;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            uData.setErrCode(6);            
            return uData;
        }

    }

    /**
     * Prijavljivanje korisnika.
     * Ako korisnik postoji, vraca njegov ID (Integer). U protivnom -1
     */
    public static userData loginUser(String korIme, String lozinka) {
        userData uData = new userData();
        try {
            String sql = "SELECT idUser,ime,prezime,email FROM user WHERE korIme=? AND lozinka=?";

            Connection conn = database.getDBConnection();
            PreparedStatement sqlStat = conn.prepareStatement(sql);
            sqlStat.setString(1, korIme);
            sqlStat.setString(2, lozinka);

            ResultSet rs = sqlStat.executeQuery();

            if (rs.next()) {
                uData.setUserID(rs.getInt("idUser"));
                uData.setFirstName(rs.getString("ime"));
                uData.setLastName(rs.getString("prezime"));
                uData.setEmail(rs.getString("email"));
                uData.setUserName(korIme);
                return uData;
            } else {
                uData.setErrCode(1);
                return uData;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            uData.setErrCode(2);
            return uData;
        }

    }

    /**
     * Prijavljivanje korisnika.
     * Ako korisnik postoji, vraca njegov ID (Integer). U protivnom -1
     */
    public static userData userData(Integer id) {
        userData uData = new userData();
        try {
            String sql = "SELECT * FROM user WHERE idUser=?";

            Connection conn = database.getDBConnection();
            PreparedStatement sqlStat = conn.prepareStatement(sql);
            sqlStat.setInt(1, id);


            ResultSet rs = sqlStat.executeQuery();

            if (rs.next()) {
                uData.setUserID(rs.getInt("idUser"));
                uData.setUserName(rs.getString("korIme"));
                uData.setFirstName(rs.getString("ime"));
                uData.setLastName(rs.getString("prezime"));
                uData.setEmail(rs.getString("email"));

                return uData;
            } else {
                uData.setErrCode(3);
                return uData;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            uData.setErrCode(2);
            return uData;
        }

    }

    /**
     * Promjena lozinke
     * true - uspjesna promjena
     * false - neuspjesna (error)
     */
    public static boolean changePassword(Integer idUser, String newPass) {
        try {
            String sql = "UPDATE user SET lozinka=? WHERE idUser=?";
            Connection conn = database.getDBConnection();
            PreparedStatement sqlStat = conn.prepareStatement(sql);
            sqlStat.setString(1, newPass);
            sqlStat.setInt(2, idUser);
            sqlStat.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Promjena e-maila
     * true - uspjesna promjena
     * false - neuspjesna (error)
     */
    public static boolean changeEMail(Integer idUser, String newEMail) {
        try {
            String sql = "UPDATE user SET email=? WHERE idUser=?";
            Connection conn = database.getDBConnection();
            PreparedStatement sqlStat = conn.prepareStatement(sql);
            sqlStat.setString(1, newEMail);
            sqlStat.setInt(2, idUser);
            sqlStat.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
