/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import database.zahtjevi;
import datatype.archivedData;
import datatype.cityData;
import datatype.weatherData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author sheky
 */
@XmlSeeAlso({cityData.class, archivedData.class})

@WebService()
public class prognoze {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "cityWeather")
    public weatherData cityWeather(@WebParam(name = "zip") String zip, @WebParam(name = "date") String date, @WebParam(name = "id") String id) {

        weatherData wDate = new weatherData();
        Date datum = null;
        Long dbID = null;

        

        if (zip.length()==0) {
            wDate.setErrCode(10);
            return wDate;
        }

        if (date.length()!=0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                datum = sdf.parse(date);
            } catch (ParseException ex) {
                wDate.setErrCode(9);
                return wDate;
            }
        }
        if (id.length()!=0) {
            try {
                dbID = Long.valueOf(id);
            } catch (Exception ex) {
                wDate.setErrCode(10);
                return wDate;
            }
        }

        wDate = zahtjevi.getWeatherForUSA(zip, datum, dbID);

        return wDate;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "searchCity")
    public LinkedList searchCity(@WebParam(name = "zip") String zip, @WebParam(name = "city") String city, @WebParam(name = "state") String state, @WebParam(name = "county") String county, @WebParam(name = "hasArchive") boolean hasArchive) {
        LinkedList list = null;
        int n =0;

        if (zip.equals("?")) {
            zip = "";
            n++;
        }
        if (city.equals("?")) {
            city = "";
            n++;
        }
        if (state.equals("?")) {
            state = "";
            n++;
        }
        if (county.equals("?")) {
            county = "";
            n++;            
        }
        
        if (n==4) {
            LinkedList<cityData> ll = new LinkedList<cityData>();
            cityData cData = new cityData();
            cData.setErrCode(12);
            ll.add(cData);
            list = ll;
        } else {
            list = zahtjevi.searchCityUSA(zip, city, state, county, hasArchive);
        }
        
        return list;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "listCityArchive")
    public LinkedList listCityArchive(
            @WebParam(name = "zip") String zip) {

        LinkedList list = null;

        if(!zip.equals("?")) {
            list = zahtjevi.getArchivedIDs_forZIP(zip);
        }else {
            LinkedList<archivedData> ll = new LinkedList<archivedData>();
            archivedData ard = new archivedData();
            ard.setErrCode(12);
            ll.add(ard);
            list = ll;
        }

        return list;
    }


    /**
     * Web service operation
     */
    @WebMethod(operationName = "cityInfo")
    public cityData cityInfo(@WebParam(name = "zip")
    String zip) {
        LinkedList<cityData> col = new LinkedList();
        col = zahtjevi.searchCityUSA(zip, "", "", "", false);
        return col.getFirst();
    }
    /**
     * Web service operation
     */

}



