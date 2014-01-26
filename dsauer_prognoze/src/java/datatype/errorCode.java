/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datatype;

/**
 *
 * @author sheky
 */
public class errorCode {

    public static String errMesg(Integer errCode) {
        String msg = null;
        switch(errCode) {
            case 1:
                msg = "Error: Neispravan upit web serveru za prognoze.";
                break;
            case 2:
                msg = "Error: Nepostoji grad sa traženim ZIP kodom.";
                break;
            case 3:
                msg = "Error: Pogreška u sql upitu, u funkciji getCityDataUSZipCode.";
                break;
            case 4:
                msg = "Error: Udaljeni servis je nedostupan!";
                break;
            case 5:
                msg = "Error: Podaci za traženi grad su nedostupni.";
                break;
            case 6:
                msg = "Info: Nepostoje podaci za traženi ZIP kod. Već za prvu bližu lokaciju.";
                break;
            case 7:
                msg = "Error: Pogreška na serveru, prilikom zatraživanja prognoze za ZIP kod.";
                break;
            case 8:
                msg = "Error: Nepostoji vremenska prognoza prema zahtjevanim parametre.";
                break;
            case 9:
                msg = "Error: Neispravno unesen datum!";
                break;
            case 10:
                msg = "Error: Neispravno unesen id.";
                break;
            case 11:
                msg = "Error: Pogreška na servisu.";
                break;
            case 12:
                msg = "Error: Nepotpuni podaci za upit.";
                break;
             default:
                break;
        }
        return msg;
    }

}
