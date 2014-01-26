/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import javax.xml.namespace.QName;
import service.ArchivedData;
import service.CityData;
import service.WeatherData;

/**
 *
 * @author sheky
 */
public class prognoze {

    private static URL WSDL = null;

    public static URL getWSDL() {
        if (WSDL == null) {
            readProperties rp = new readProperties();
            String wsdlURL = rp.readProp("wsdl.prognoze");
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

    public static LinkedList<service.CityData> searchCity(String zip, String city, String state, String county, Boolean hasArchive) {
        LinkedList<service.CityData> listWData = new LinkedList<CityData>();

        if (zip == null) {
            zip = "";
        }
        if (city == null) {
            city = "";
        }
        if (state == null) {
            state = "";
        }
        if (county == null) {
            county = "";
        }
        if (hasArchive == null) {
            hasArchive = false;
        }

        try { // Call Web Service Operation
            //service.PrognozeService service = new service.PrognozeService();
            service.PrognozeService service = new service.PrognozeService(getWSDL(),new QName("http://service/", "prognozeService"));
            service.Prognoze port = service.getPrognozePort();

            java.util.List<java.lang.Object> result = port.searchCity(zip, city, state, county, hasArchive);

            Iterator<java.lang.Object> itRes = result.iterator();
            while (itRes.hasNext()) {
                CityData cdata = new CityData();
                cdata = (CityData) itRes.next();
                listWData.add(cdata);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listWData;
    }

    public static WeatherData cityWeather(String zip, String date, String id) {
        WeatherData wData = new WeatherData();
        try { // Call Web Service Operation
            if (zip == null) {
                zip = "";
            }
            if (date == null) {
                date = "";
            }
            if (id == null) {
                id = "";
            }
            //service.PrognozeService service = new service.PrognozeService();
            service.PrognozeService service = new service.PrognozeService(getWSDL(),new QName("http://service/", "prognozeService"));
            service.Prognoze port = service.getPrognozePort();
            wData = port.cityWeather(zip, date, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            wData.setError(true);
            wData.setMessage("Pogreška prilikom dohvaćanja vremenske prognoze sa servisa!");
        }
        return wData;
    }

    public static service.CityData cityInfo(String zip) {
        service.CityData result = new CityData();
        try { // Call Web Service Operation
            //service.PrognozeService service = new service.PrognozeService();
            service.PrognozeService service = new service.PrognozeService(getWSDL(),new QName("http://service/", "prognozeService"));
            service.Prognoze port = service.getPrognozePort();

            result = port.cityInfo(zip);            
        } catch (Exception ex) {
            result.setError(true);
            result.setMessage("Pogreška prilikom dohvaćanja podataka o gradu " + zip);
        }
        return result;
    }

    public static LinkedList<service.ArchivedData> listCityArchive(String zip) {
        LinkedList<service.ArchivedData> listArcData = new LinkedList<ArchivedData>();

        try { // Call Web Service Operation
            //service.PrognozeService service = new service.PrognozeService();
            service.PrognozeService service = new service.PrognozeService(getWSDL(),new QName("http://service/", "prognozeService"));
            service.Prognoze port = service.getPrognozePort();
            java.util.List<java.lang.Object> result = port.listCityArchive(zip);

            Iterator<java.lang.Object> itRes = result.iterator();
            while (itRes.hasNext()) {
                ArchivedData adat = new ArchivedData();
                adat = (ArchivedData)itRes.next();
                listArcData.add(adat);
            }
        } catch (Exception ex) {
             ArchivedData adat = new ArchivedData();
             adat.setError(true);
             adat.setMessage("Pogreška prilikom dohvaćanja podataka o arhivama.");
             listArcData.add(adat);
             ex.printStackTrace();
        }
        return listArcData;
    }
}
