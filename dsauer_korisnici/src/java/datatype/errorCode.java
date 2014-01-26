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
                msg = "Error: Neispravno korisnicko ime ili lozinka";
                break;
            case 2:
                msg = "Error: Pogreška na servisu, prilikom autentifikacije korisnika.";
                break;
            case 3:
                msg = "Error: Nepostojeći korisnik.";
                break;
            case 4:
                msg = "Error: Nisu uneseni svi potrebni podaci.";
                break;
            case 5:
                msg = "Error: Korisničko ime već postoji.";
                break;
            case 6:
                msg = "Error: Pogreška na servisu prilikom kreiranja korisničkog računa.";
                break;         
             default:
                break;
        }
        return msg;
    }

}
