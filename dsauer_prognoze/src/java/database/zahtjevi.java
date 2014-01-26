/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import datatype.archivedData;
import java.sql.SQLException;
import weather.weatherClient;
import datatype.weatherData;
import datatype.cityData;
import java.sql.ResultSet;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author sheky
 */
public class zahtjevi {

    public static weatherData getWeatherForUSA(String zip, java.util.Date datum, Long id) {
        weatherData wData = new weatherData();
        cityData cdata = new cityData();
        cdata = sqlQuery.getCityDataUSZipCode(zip);
        if (cdata.getError() == true) {
            wData.setErrCode(2);
        } else {
            if (cdata.getArchived() != 0) { //postoji arhivirana prognoza
                wData = sqlQuery.getWeatherByUSZipCode(zip, datum, id);
            } else { //nepostoji arhivirana prognoza
                //dohvati podatke sa weatherBug servisa
                try {
                    wData = weatherClient.GetLiveWeatherByUSZipCode(zip);
                } catch (Exception ex) {
                }

                if (wData.getError() == true && wData.getErrCode() == 4) { //servis nedostupan
                    //pribavi podatke za alternativni ZIP ako postoji iz lokalne baze
                    if (!cdata.getSubstitute_zip().equals("0")) {
                        //wData = sqlQuery.getWeatherByUSZipCode(cdata.getSubstitute_zip(), datum, id);
                        wData = zahtjevi.getWeatherForUSA(cdata.getSubstitute_zip(), datum, id);
                        wData.makeZipAsSubstituteZIP();
                    } else { //obavjest korisniku o nedostupnosti podataka
                        wData.setErrCode(5);
                    }
                } else { //servis je dostupan
                    //pohrani podatke u bazu
                    if (wData.getSubstitute() == false) { //rezultati za trazeni grad
                        sqlQuery.setWeatherByUSZipCode(wData);

                    } else { //rezultati za zamjensku lokaciju
                        /*if (sqlQuery.isZIPExist(wData.getSubstitute_ZIP()) == true) {
                            cdata = sqlQuery.getCityDataUSZipCode(wData.getSubstitute_ZIP());
                            if (cdata.getArchived()!=0) {
                                wData = sqlQuery.getWeatherByUSZipCode(cdata.getZipCode(), datum, id);
                                wData.makeZipAsSubstituteZIP();
                                //sqlQuery.setSubstituteZIP(zip, wData.getSubstitute_ZIP());
                            } else {
                                sqlQuery.setSubstituteZIP(zip, wData.getSubstitute_ZIP());
                                sqlQuery.setWeatherByUSZipCode(wData);
                            }
                        }*/
                        sqlQuery.setSubstituteZIP(zip, wData.getSubstitute_ZIP());
                        zahtjevi.getWeatherForUSA(wData.getSubstitute_ZIP(), datum, id);
                    }
                }
            }
        }
        return wData;
    }


    /*
     *Arhiviranje vremenskih prognoza za gradove koji su inicijalizirani
     */
    public static Integer archiveWeatherForUSZipCode(Integer older_than, Integer lenght) {
        ResultSet rs = sqlQuery.getArchivedCity(older_than, lenght);
        weatherData wData = new weatherData();
        String zip = null;
        Integer archivedNo = 0;
        try {
            while (rs.next()) {
                zip = rs.getString("ZIP");
                wData = weatherClient.GetLiveWeatherByUSZipCode(zip);

                if (wData.getError() == true) {
                    System.out.println(wData.getErrCode().toString() + "  " + wData.getMessage());
                    break;
                } else {
                    if (wData.getSubstitute() == false) { //rezultati za trazeni grad
                        sqlQuery.setWeatherByUSZipCode(wData);
                        archivedNo++;
                        System.out.println(archivedNo + ".  zip:" + zip);
                    } else {
                        sqlQuery.setSubstituteZIP(zip, wData.getSubstitute_ZIP());
                        wData.setZIP(wData.getSubstitute_ZIP());
                        sqlQuery.setWeatherByUSZipCode(wData);
                        archivedNo++;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return archivedNo;
    }

    public static LinkedList<cityData> searchCityUSA(String zip, String city, String state, String county, Boolean archivedWeather) {
        LinkedList<cityData> col = new LinkedList();

        ResultSet rs = sqlQuery.searchCityUSA(zip, city, state, county, archivedWeather);
        try {
            while (rs.next()) {
                cityData cData = new cityData();
                cData.setZipCode(rs.getString("ZIP"));
                cData.setCity(rs.getString("CITY"));
                cData.setCounty(rs.getString("COUNTY"));
                cData.setState(rs.getString("STATE"));
                cData.setLatitude(rs.getString("LATITUDE"));
                cData.setLongitude(rs.getString("LONGITUDE"));
                cData.setArchived(rs.getInt("ARCHIVED"));
                cData.setSubstitute_zip(rs.getString("SUBSTITUTE_ZIP"));

                java.sql.Timestamp sDate = null;
                Date null_datum = new Date();
                null_datum.setTime(0);
                try {
                    sDate = rs.getTimestamp("LASTUPDATE");
                    cData.setLastupdate(new java.util.Date(sDate.getTime()));
                } catch (Exception ex) {
                    cData.setLastupdate(null_datum);
                }

                col.add(cData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            cityData cData = new cityData();
            cData.setErrCode(11);
            col.add(cData);
            ex.printStackTrace();
        }

        return col;
    }

    public static LinkedList<archivedData> getArchivedIDs_forZIP(String zip) {
        LinkedList<archivedData> list = new LinkedList();
        ResultSet rs = sqlQuery.getArchivedIDs_forZIP(zip);
        try {
            while (rs.next()) {
                archivedData arData = new archivedData();
                arData.setId(rs.getLong("ID"));

                java.sql.Timestamp sDate = null;
                Date null_datum = new Date();
                null_datum.setTime(0);
                try {
                    sDate = rs.getTimestamp("DATE");
                    arData.setOnDate(new java.util.Date(sDate.getTime()));
                } catch (Exception ex) {
                    arData.setOnDate(null_datum);
                }


                list.add(arData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            archivedData arData = new archivedData();
            arData.setErrCode(11);
            list.add(arData);
            ex.printStackTrace();
        }

        return list;
    }

    public static void main(String[] args) {
        weatherData wData = new weatherData();
        /*Date datum = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
        datum = sdf.parse("2009-08-20 16:00:00");
        } catch (ParseException ex) {
        ex.printStackTrace();
        }
        System.out.println("datum: " + datum.toString());
        wData = getWeatherForUSA("00616", null, null);

        if (wData.getError() == true) {
        System.out.println(wData.getErrCode() + "   " + wData.getMessage());
        } else {
        if (wData.getSubstitute() == true) {
        System.out.println("Zamjenska lokacija");
        }
        System.out.println(wData.getZIP() + "  " + wData.getId().toString() + "  " + wData.getDate().toString());


        }
         */
        //int n = archiveWeatherForUSZipCode(1 * 60, 10);
        //System.out.println("arhivirano prognoza: " + n);


        //Collection<cityData> col = new LinkedList<cityData>();
        /*Collection<cityData> col = searchCityUSA("144", "", "", "", false);
        System.out.println("size: " + col.size());
        Iterator<cityData> itCD = col.iterator();
        cityData cData = new cityData();
        while (itCD.hasNext()) {
        cData = itCD.next();
        System.out.println("ZIP: " + cData.getZipCode() + "  datum:" + cData.getLastupdate().toString());
        }
         */

       /* LinkedList<cityData> ll = new LinkedList<cityData>();
        weatherData wd = new weatherData();
        cityData cd = new cityData();
        ll = searchCityUSA("", "las vegas", "", "", false);
        System.out.println("ll num:" + ll.size());
        Iterator<cityData> itcd = ll.iterator();
        int n = 0;
        while (itcd.hasNext()) {
            n++;
            cd = itcd.next();
            wd = getWeatherForUSA(cd.getZipCode(), null, null);
            System.out.println(n + ". zip:" + cd.getZipCode() + "  substitute:" + wd.getSubstitute() + " subzip:" + wd.getSubstitute_ZIP() + "  zip:" + wd.getZIP());
        }*/
weatherData wd = new weatherData();
wd = getWeatherForUSA("89101", null, Long.valueOf(28));
System.out.println("datum: " + wd.getDate().toString());
    }
}
