/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datatype;

import java.lang.reflect.Field;
import java.util.Date;

/**
 *
 * @author sheky
 */


public class weatherData {

    private Long id = null;
    private String ZIP = null;
    private Date date = null;
    private String CurrIcon = null;
    private String CurrDesc = null;
    private Float Temperature = null;
    private Float TemperatureHigh = null;
    private Float TemperatureLow = null;
    private String TemperatureUnit = null;
    private String WindDirection = null;
    private Float WindSpeed = null;
    private String WindSpeedUnit = null;
    private Float Humidity = null;
    private String HumidityUnit = null;
    private Float HumidityHigh = null;
    private Float HumidityLow = null;
    private String MoonPhaseImage = null;
    private Float Pressure = null;
    private String PressureUnit = null;
    private Float PressureHigh = null;
    private Float PressureLow = null;
    private Float RainRate = null;
    private Float RainRateMax = null;
    private String RainRateUnit = null;
    private Float RainToday = null;
    private String RainUnit = null;
    private String TimeZone = null;
    private Integer TimeZoneOffset = null;
    private Date Sunrise = null;
    private Date Sunset = null;
    //--alternativne informacije za grad
    private Boolean substitute = false;
    private String substitute_ZIP = null;
    private Boolean error = false;
    private Integer errCode = null;
    private String message = null;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Boolean getSubstitute() {
        return substitute;
    }

    public void setSubstitute(Boolean substitute) {
        this.substitute = substitute;        
    }

    public String getHumidityUnit() {
        return HumidityUnit;
    }

    public void setHumidityUnit(String HumidityUnit) {
        this.HumidityUnit = HumidityUnit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
        this.error = true;
        this.setMessage(errorCode.errMesg(errCode));
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubstitute_ZIP() {
        return substitute_ZIP;
    }

    public void setSubstitute_ZIP(String substitute_ZIP) {
        this.substitute_ZIP = substitute_ZIP;
        this.substitute = true;
    }
   

    public String getCurrDesc() {
        return CurrDesc;
    }

    public void setCurrDesc(String CurrDesc) {
        this.CurrDesc = CurrDesc;
    }

    public String getCurrIcon() {
        return CurrIcon;
    }

    public void setCurrIcon(String CurrIcon) {
        this.CurrIcon = CurrIcon;
    }

    public Float getHumidity() {
        return Humidity;
    }

    public void setHumidity(Float Humidity) {
        this.Humidity = Humidity;
    }

    public Float getHumidityHigh() {
        return HumidityHigh;
    }

    public void setHumidityHigh(Float HumidityHigh) {
        this.HumidityHigh = HumidityHigh;
    }

    public Float getHumidityLow() {
        return HumidityLow;
    }

    public void setHumidityLow(Float HumidityLow) {
        this.HumidityLow = HumidityLow;
    }

    public String getHumidityUni() {
        return HumidityUnit;
    }

    public void setHumidityUni(String HumidityUni) {
        this.HumidityUnit = HumidityUni;
    }

    public String getMoonPhaseImage() {
        return MoonPhaseImage;
    }

    public void setMoonPhaseImage(String MoonPhaseImage) {
        this.MoonPhaseImage = MoonPhaseImage;
    }

    public Float getPressure() {
        return Pressure;
    }

    public void setPressure(Float Pressure) {
        this.Pressure = Pressure;
    }

    public Float getPressureHigh() {
        return PressureHigh;
    }

    public void setPressureHigh(Float PressureHigh) {
        this.PressureHigh = PressureHigh;
    }

    public Float getPressureLow() {
        return PressureLow;
    }

    public void setPressureLow(Float PressureLow) {
        this.PressureLow = PressureLow;
    }

    public String getPressureUnit() {
        return PressureUnit;
    }

    public void setPressureUnit(String PressureUnit) {
        this.PressureUnit = PressureUnit;
    }

    public Float getRainRate() {
        return RainRate;
    }

    public void setRainRate(Float RainRate) {
        this.RainRate = RainRate;
    }

    public Float getRainRateMax() {
        return RainRateMax;
    }

    public void setRainRateMax(Float RainRateMax) {
        this.RainRateMax = RainRateMax;
    }

    public String getRainRateUnit() {
        return RainRateUnit;
    }

    public void setRainRateUnit(String RainRateUnit) {
        this.RainRateUnit = RainRateUnit;
    }

    public Float getRainToday() {
        return RainToday;
    }

    public void setRainToday(Float RainToday) {
        this.RainToday = RainToday;
    }

    public String getRainUnit() {
        return RainUnit;
    }

    public void setRainUnit(String RainUnit) {
        this.RainUnit = RainUnit;
    }

    public Date getSunrise() {
        return Sunrise;
    }

    public void setSunrise(Date Sunrise) {
        this.Sunrise = Sunrise;
    }

    public Date getSunset() {
        return Sunset;
    }

    public void setSunset(Date Sunset) {
        this.Sunset = Sunset;
    }

    public Float getTemperature() {
        return Temperature;
    }

    public void setTemperature(Float Temperature) {
        this.Temperature = Temperature;
    }

    public Float getTemperatureHigh() {
        return TemperatureHigh;
    }

    public void setTemperatureHigh(Float TemperatureHigh) {
        this.TemperatureHigh = TemperatureHigh;
    }

    public Float getTemperatureLow() {
        return TemperatureLow;
    }

    public void setTemperatureLow(Float TemperatureLow) {
        this.TemperatureLow = TemperatureLow;
    }

    public String getTemperatureUnit() {
        return TemperatureUnit;
    }

    public void setTemperatureUnit(String TemperatureUnit) {
        this.TemperatureUnit = TemperatureUnit;
    }

    public String getTimeZone() {
        return TimeZone;
    }

    public void setTimeZone(String TimeZone) {
        this.TimeZone = TimeZone;
    }

    public Integer getTimeZoneOffset() {
        return TimeZoneOffset;
    }

    public void setTimeZoneOffset(Integer TimeZoneOffset) {
        this.TimeZoneOffset = TimeZoneOffset;
    }

    public String getWindDirection() {
        return WindDirection;
    }

    public void setWindDirection(String WindDirection) {
        this.WindDirection = WindDirection;
    }

    public Float getWindSpeed() {
        return WindSpeed;
    }

    public void setWindSpeed(Float WindSpeed) {
        this.WindSpeed = WindSpeed;
    }

    public String getWindSpeedUnit() {
        return WindSpeedUnit;
    }

    public void setWindSpeedUnit(String WindSpeedUnit) {
        this.WindSpeedUnit = WindSpeedUnit;
    }

    public String getZIP() {
        return ZIP;
    }

    public void setZIP(String ZIP) {
        if (ZIP != null) {
            this.ZIP = ZIP;
        } else {
            this.ZIP = "";
        }
    }

    public void makeZipAsSubstituteZIP () {
        this.substitute_ZIP = this.ZIP;        
        this.substitute = true;
        setErrCode(6);
        this.error= false;
    }

    
    @Override
    public String toString() {
        String data= "";
        Object obj = null;
        data+="ZIP:" + ZIP + "  >>";
        Class klasa = this.getClass();
        Field[] polja = klasa.getDeclaredFields();
        for(int i =0;i<polja.length;i++) {
            data+=polja[i].getName() + ", ";
        }
        return data;
    }


}
