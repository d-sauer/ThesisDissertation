/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import database.zahtjevi;
import datatype.userData;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author sheky
 */
@WebService()
public class korisnici {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "userAutentification")
    public userData userAutentification(@WebParam(name = "userName") String userName, @WebParam(name = "userPass") String userPass) {
        userData uData = new userData();
        if (userName.length()==0 || userPass.length()==0) {
            uData.setErrCode(4);
        } else {
            uData = zahtjevi.userAutentification(userName, userPass);
        }
        return uData;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "registerUser")
    public userData registerUser(@WebParam(name = "userName") String userName, @WebParam(name = "userPass") String userPass, @WebParam(name = "firstName") String firstName, @WebParam(name = "lastName") String lastName, @WebParam(name = "email") String email) {
        userData uData = new userData();
        if (userName.length()==0 || userPass.length()==0 || firstName.length()==0 || lastName.length()==0) {
            uData.setErrCode(4);
        } else {
            uData = zahtjevi.createUser(firstName, lastName, email, userName, userPass);
        }
        return uData;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "changeUserProperties")
    public userData changeUserProperties(@WebParam(name = "userId") Integer userId, @WebParam(name = "password") String password, @WebParam(name = "email") String email) {
        userData udata = new userData();
        Boolean isOK_1 = null;  //email
        Boolean isOK_2 = null;  //password



        if (password.length() > 0) {
            isOK_2 = zahtjevi.changePassword(userId, password);
        }

        if (email.length() > 0) {
            isOK_1 = zahtjevi.changeEMail(userId, email);
        }

        String str = "";
        if (isOK_1 == null) {
        } else if (isOK_1 == true) {
            str = "E-mail uspješno promijenjen.";
        } else {
            str = "Error: e-mail nije promijenjen.";
        }

        if (isOK_2 == null) {
        } else if (isOK_2 == true) {
            str += "Lozinka uspješno promijenjena.";
        } else {
            str += "Error: lozinka nije promijenjena.";
        }

        if (isOK_1 == null && isOK_2 == null) {
            str = "Error: Niste unjeli potrebne podatke.";
        }

        udata.setUserID(userId);
        udata.setMessage(str);


        return udata;
    }
}
