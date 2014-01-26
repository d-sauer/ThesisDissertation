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
public class archivedData {
    private Long id = null;
    private Date onDate = null;
    private Boolean error = false;
    private Integer errCode = null;
    private String message = null;

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
        this.setMessage(errorCode.errMesg(errCode));
        this.error = true;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getOnDate() {
        return onDate;
    }

    public void setOnDate(Date onDate) {
        this.onDate = onDate;
    }

   
}
