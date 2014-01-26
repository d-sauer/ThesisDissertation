/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datatype;

import java.util.Date;


/**
 *
 * @author sheky
 */
public class cityData {
    private String zipCode= null;
    private String city = null;
    private String latitude=null;
    private String longitude = null;
    private String state = null;
    private String county = null;
    private Integer archived = null;
    private String substitute_zip = null;
    private Boolean error = false;
    private Integer errCode = null;
    private String message = null;
    private Date lastupdate = null;

    public Date getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getSubstitute_zip() {
        return substitute_zip;
    }

    public void setSubstitute_zip(String substitute_zip) {
        this.substitute_zip = substitute_zip;
    }
    

    public Integer getArchived() {
        return archived;
    }

    public void setArchived(Integer archived) {
        this.archived = archived;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
        error = true;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
}
