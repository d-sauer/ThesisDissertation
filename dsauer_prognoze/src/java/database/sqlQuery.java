/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import datatype.weatherData;
import datatype.cityData;
import java.util.Date;

/**
 *
 * @author sheky
 */
public class sqlQuery {

    public static ResultSet searchCityUSA(String zip, String city, String state, String county, Boolean archivedWeather) {
        String subQuery = "";
        boolean q = false;
        if (zip.length() != 0) {
            subQuery += " ZIP REGEXP '^" + zip + "'  ";
            q = true;
        }

        if (city.length() != 0) {
            if (q == true) {
                subQuery += " AND CITY REGEXP '^" + city + "'  ";
            } else {
                subQuery += " CITY REGEXP '^" + city + "'  ";
                q = true;
            }
        }

        if (state.length() != 0) {
            if (q == true) {
                subQuery += " AND STATE REGEXP '^" + state + "'  ";
            } else {
                subQuery += " STATE REGEXP '^" + state + "'  ";
                q = true;
            }
        }

        if (county.length() != 0) {
            if (q == true) {
                subQuery += " AND COUNTY REGEXP '^" + county + "'  ";
            } else {
                subQuery += " COUNTY REGEXP '^" + county + "'  ";
                q = true;
            }
        }

        if (archivedWeather == true) {
            if (q == true) {
                subQuery += " AND ARCHIVED >0  ";
            } else {
                subQuery += " ARCHIVED > 0  ";
                q = true;
            }
        }

        try {
            String sql = "SELECT STATE, COUNTY, CITY, ZIP, SUBSTITUTE_ZIP, LATITUDE, LONGITUDE, ARCHIVED, LASTUPDATE from ZIP_CODES WHERE " + subQuery + " ORDER BY STATE,CITY";
            Connection conn = database.getDBConnection();
            PreparedStatement sqlStat = conn.prepareStatement(sql);
            ResultSet rs = sqlStat.executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static weatherData getWeatherByUSZipCode(String zip, java.util.Date datum, Long id) {
        weatherData wData = new weatherData();
        try {
            Connection conn = database.getDBConnection();
            PreparedStatement sqlStat = null;
            String sql = "SELECT * FROM weather WHERE ";
            if (id != null) {
                sql = "SELECT * FROM weather WHERE ID=?";
                sqlStat = conn.prepareStatement(sql);
                sqlStat.setLong(1, id);
            } else {
                if (datum != null) {
                    sql = "SELECT * , ABS( TIMEDIFF( DATE, ? ) ) AS TDIFF FROM `weather` WHERE ZIP=? ORDER BY TDIFF";
                    sqlStat = conn.prepareStatement(sql);
                    sqlStat.setTimestamp(1, new java.sql.Timestamp(datum.getTime()));
                    sqlStat.setString(2, zip);
                } else {
                    sql = "SELECT * FROM weather WHERE ZIP=? ORDER BY DATE DESC";
                    sqlStat = conn.prepareStatement(sql);
                    sqlStat.setString(1, zip);
                }
            }

            ResultSet rs = sqlStat.executeQuery();
            if (rs.next()) {
                wData.setCurrDesc(rs.getString("CurrDesc"));
                wData.setCurrIcon(rs.getString("CurrIcon"));
                wData.setHumidity(rs.getFloat("Humidity"));
                wData.setHumidityHigh(rs.getFloat("HumidityHigh"));
                wData.setHumidityLow(rs.getFloat("HumidityLow"));
                wData.setHumidityUni(rs.getString("HumidityUnit"));
                wData.setMoonPhaseImage(rs.getString("MoonPhaseImage"));
                wData.setPressure(rs.getFloat("Pressure"));
                wData.setPressureHigh(rs.getFloat("PressureHigh"));
                wData.setPressureLow(rs.getFloat("PressureLow"));
                wData.setPressureUnit(rs.getString("PressureUnit"));
                wData.setRainRate(rs.getFloat("RainRate"));
                wData.setRainRateMax(rs.getFloat("RainRateMax"));
                wData.setRainRateUnit(rs.getString("RainRateUnit"));
                wData.setRainToday(rs.getFloat("RainToday"));
                wData.setRainUnit(rs.getString("RainUnit"));
                wData.setSunrise(new Date(rs.getTimestamp("Sunrise").getTime()));
                wData.setSunset(new Date(rs.getTimestamp("Sunset").getTime()));
                wData.setTemperature(rs.getFloat("Temperature"));
                wData.setTemperatureHigh(rs.getFloat("TemperatureHigh"));
                wData.setTemperatureLow(rs.getFloat("TemperatureLow"));
                wData.setTemperatureUnit(rs.getString("TemperatureUnit"));
                wData.setTimeZone(rs.getString("TimeZone"));
                wData.setTimeZoneOffset(rs.getInt("TimeZoneOffset"));
                wData.setWindDirection(rs.getString("WindDirection"));
                wData.setWindSpeed(rs.getFloat("WindSpeed"));
                wData.setWindSpeedUnit(rs.getString("WindSpeedUnit"));
                wData.setZIP(rs.getString("ZIP"));
                wData.setId(rs.getLong("ID"));
                wData.setDate(new Date(rs.getTimestamp("DATE").getTime()));
            } else {
                wData.setErrCode(8);
            }

            return wData;
        } catch (SQLException ex) {
            ex.printStackTrace();
            wData.setErrCode(7);
            return wData;
        }
    }

    public static void setWeatherByUSZipCode(weatherData wData) {
        boolean isOK = true;
        //treba provjeriti dali 


        try {
            String sql = "INSERT INTO weather (ZIP, DATE ,CurrIcon ,CurrDesc ,Temperature ,TemperatureHigh ," +
                    "TemperatureLow ,TemperatureUnit ,WindDirection ,WindSpeed ,WindSpeedUnit ," +
                    "Humidity ,HumidityUnit ,HumidityHigh ,HumidityLow ,MoonPhaseImage ," +
                    "Pressure ,PressureUnit ,PressureHigh ,PressureLow ," +
                    "RainRate ,RainRateMax ,RainRateUnit ,RainToday ,RainUnit ,TimeZone ,TimeZoneOffset ,Sunrise ,Sunset) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            Connection conn = database.getDBConnection();
            PreparedStatement sqlStat = conn.prepareStatement(sql);

            if (wData.getSubstitute() == true) {
                wData.setZIP(wData.getSubstitute_ZIP());
            }

            sqlStat.setString(1, wData.getZIP());
            sqlStat.setTimestamp(2, new java.sql.Timestamp(wData.getDate().getTime()));
            sqlStat.setString(3, wData.getCurrIcon());
            sqlStat.setString(4, wData.getCurrDesc());
            sqlStat.setFloat(5, wData.getTemperature());
            sqlStat.setFloat(6, wData.getTemperatureHigh());
            sqlStat.setFloat(7, wData.getTemperatureLow());
            sqlStat.setString(8, wData.getTemperatureUnit());
            sqlStat.setString(9, wData.getWindDirection());
            sqlStat.setFloat(10, wData.getWindSpeed());
            sqlStat.setString(11, wData.getWindSpeedUnit());
            sqlStat.setFloat(12, wData.getHumidity());
            sqlStat.setString(13, wData.getHumidityUni());
            sqlStat.setFloat(14, wData.getHumidityHigh());
            sqlStat.setFloat(15, wData.getHumidityLow());
            sqlStat.setString(16, wData.getMoonPhaseImage());
            sqlStat.setFloat(17, wData.getPressure());
            sqlStat.setString(18, wData.getPressureUnit());
            sqlStat.setFloat(19, wData.getPressureHigh());
            sqlStat.setFloat(20, wData.getPressureLow());
            sqlStat.setFloat(21, wData.getRainRate());
            sqlStat.setFloat(22, wData.getRainRateMax());
            sqlStat.setString(23, wData.getRainRateUnit());
            sqlStat.setFloat(24, wData.getRainToday());
            sqlStat.setString(25, wData.getRainUnit());
            sqlStat.setString(26, wData.getTimeZone());
            sqlStat.setInt(27, wData.getTimeZoneOffset());
            sqlStat.setTimestamp(28, new java.sql.Timestamp(wData.getSunrise().getTime()));
            sqlStat.setTimestamp(29, new java.sql.Timestamp(wData.getSunset().getTime()));

            sqlStat.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            isOK = false;
        }
        if (isOK == true) {
            incrementArchived(wData.getZIP(), wData.getDate());
        }

    }

    public static void incrementArchived(String zip, java.util.Date datum) {
        try {
            String sql = "UPDATE ZIP_CODES SET ARCHIVED = ARCHIVED + 1, LASTUPDATE=? WHERE ZIP =?";
            Connection conn = database.getDBConnection();
            PreparedStatement sqlStat = conn.prepareStatement(sql);
            sqlStat.setTimestamp(1, new java.sql.Timestamp(datum.getTime()));
            sqlStat.setString(2, zip);
            sqlStat.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isZIPExist(String zip) {
        try {
            String sql = "SELECT COUNT(*) FROM ZIP_CODES WHERE ZIP =?";
            Connection conn = database.getDBConnection();
            PreparedStatement sqlStat = conn.prepareStatement(sql);
            sqlStat.setString(1, zip);
            ResultSet rs = sqlStat.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void setSubstituteZIP(String ZIP, String SubZIP) {
        try {
            String sql = "UPDATE ZIP_CODES SET SUBSTITUTE_ZIP = ? WHERE ZIP =?";
            Connection conn = database.getDBConnection();
            PreparedStatement sqlStat = conn.prepareStatement(sql);
            sqlStat.setString(1, SubZIP);
            sqlStat.setString(2, ZIP);
            sqlStat.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static cityData getCityDataUSZipCode(String zip) {
        cityData usaCityData = new cityData();
        try {
            String sql = "SELECT * FROM ZIP_CODES WHERE ZIP =?";
            Connection conn = database.getDBConnection();
            PreparedStatement sqlStat = conn.prepareStatement(sql);
            sqlStat.setString(1, zip);
            ResultSet rs = sqlStat.executeQuery();
            if (rs.next()) {
                usaCityData.setCity(rs.getString("CITY"));
                usaCityData.setZipCode(rs.getString("ZIP"));
                usaCityData.setState(rs.getString("STATE"));
                usaCityData.setCounty(rs.getString("COUNTY"));
                usaCityData.setLatitude(rs.getString("LATITUDE"));
                usaCityData.setLongitude(rs.getString("LONGITUDE"));
                usaCityData.setArchived(rs.getInt("ARCHIVED"));
                usaCityData.setSubstitute_zip(rs.getString("SUBSTITUTE_ZIP"));

                return usaCityData;
            } else {
                usaCityData.setErrCode(2);
                return usaCityData;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            usaCityData.setErrCode(3);
            return usaCityData;
        }
    }

    public static Double parseStringToLong(String var) {
        var = var.replace("+", "");
        var = var.replace("\n", "");
        var = var.trim();
        return Double.parseDouble(var);
    }


    /*
     *Vraca gradove cije je zadnje arhiviranje su starije X sekundi od trenutnog datuma i vremena
     * limit - null, nema limita
     */
    public static ResultSet getArchivedCity(Integer seconds, Integer limit) {
        ResultSet rs = null;
        String sql = null;
        try {
            if (limit != null) {
                sql = "SELECT ZIP , TIMEDIFF( LASTUPDATE, NOW( ) ) AS TDIFF FROM `ZIP_CODES` WHERE (ARCHIVED >0) HAVING ABS( TIME_TO_SEC( TDIFF ) ) >= ( ? ) ORDER BY TDIFF DESC LIMIT 0 , ? ";
            } else {
                sql = "SELECT ZIP , TIMEDIFF( LASTUPDATE, NOW( ) ) AS TDIFF FROM `ZIP_CODES` WHERE (ARCHIVED >0) HAVING ABS( TIME_TO_SEC( TDIFF ) ) >= ( ? ) ORDER BY TDIFF DESC";
            }
            Connection conn = database.getDBConnection();
            PreparedStatement sqlStat = conn.prepareStatement(sql);
            sqlStat.setInt(1, seconds);

            if (limit != null) {
                sqlStat.setInt(2, limit);
            }
            rs = sqlStat.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    /*
     *Vraca koliko gradova ima arhive
     */
    public static Integer getNumberOfArchivedCity() {
        ResultSet rs = null;
        Integer aNumber = 0;
        try {
            String sql = "SELECT Count(*) FROM `ZIP_CODES` WHERE ARCHIVED>0";
            Connection conn = database.getDBConnection();
            PreparedStatement sqlStat = conn.prepareStatement(sql);

            rs = sqlStat.executeQuery();
            if (rs.next()) {
                aNumber = rs.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
        return aNumber;
    }

    public static ResultSet getArchivedIDs_forZIP(String zip) {
        ResultSet rs = null;
        try {
            String sql = "SELECT ID, DATE FROM `weather` WHERE ZIP=?";
            Connection conn = database.getDBConnection();
            PreparedStatement sqlStat = conn.prepareStatement(sql);
            sqlStat.setString(1, zip);
            rs = sqlStat.executeQuery();

            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }    
}
