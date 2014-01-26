/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceClient;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import service.UserData;

/**
 *
 * @author sheky
 */
public class korisnici {

    private static URL WSDL = null;
    
    public static URL getWSDL() {
        if (WSDL == null) {
            readProperties rp = new readProperties();
            String wsdlURL = rp.readProp("wsdl.korisnici");
            try {
                URL baseUrl;
                baseUrl = service.KorisniciService.class.getResource(".");

                WSDL = new URL(baseUrl, wsdlURL);
            } catch (MalformedURLException e) {
                WSDL = null;
                e.printStackTrace();
            }
        }
        return WSDL;
    }

    public static service.UserData userAutentification(String userName, String userPass) {
        service.UserData uData = new UserData();
        try { // Call Web Service Operation
            //service.KorisniciService service = new service.KorisniciService();
            service.KorisniciService service = new service.KorisniciService(getWSDL(),new QName("http://service/", "korisniciService"));
            service.Korisnici port = service.getKorisniciPort();

            uData = port.userAutentification(userName, userPass);
            return uData;
        } catch (Exception ex) {
            ex.printStackTrace();
            uData.setError(true);
            uData.setUserID(-1);
            return uData;
        }

    }

    public static service.UserData registerUser(String userName, String userPass, String firstName, String lastName, String email) {
        service.UserData newUserData = new UserData();
        try { // Call Web Service Operation
            //service.KorisniciService service = new service.KorisniciService();
            service.KorisniciService service = new service.KorisniciService(getWSDL(),new QName("http://service/", "korisniciService"));
            service.Korisnici port = service.getKorisniciPort();

            newUserData = port.registerUser(userName, userPass, firstName, lastName, email);
        } catch (Exception ex) {
            newUserData.setError(true);
            newUserData.setMessage("Doslo je do pogreske na strani klijenta, prilikom registracije korisnika!");
            ex.printStackTrace();
        }
        return newUserData;
    }

    public static service.UserData changeUserProprties(Integer userId, String userPass, String email) {
        service.UserData newUserData = new UserData();
        try { // Call Web Service Operation
            //service.KorisniciService service = new service.KorisniciService();
            service.KorisniciService service = new service.KorisniciService(getWSDL(),new QName("http://service/", "korisniciService"));
            service.Korisnici port = service.getKorisniciPort();

            newUserData = port.changeUserProperties(userId, userPass, email);
        } catch (Exception ex) {
            newUserData.setError(true);
            newUserData.setMessage("Doslo je do pogreske na strani klijenta, prilikom promjene korisničkih podaka!");
            ex.printStackTrace();
        }
        return newUserData;
    }
}
