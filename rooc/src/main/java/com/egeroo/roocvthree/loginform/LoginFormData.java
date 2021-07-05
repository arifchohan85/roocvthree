package com.egeroo.roocvthree.loginform;

import com.egeroo.roocvthree.menulist.Menulist;

import java.io.Serializable;
import java.util.List;

public class LoginFormData implements Serializable {
    //private String userchnltoken;
    //private String chnltoken;
    private String accessToken;

    private String errorMessage;
    private List<Menulist> dataMenu;

    /*public String getUserchnltoken() {
        return userchnltoken;
    }

    public void setUserchnltoken(String userchnltoken) {
        this.userchnltoken = userchnltoken;
    }

    public String getChnltoken() {
        return chnltoken;
    }

    public void setChnltoken(String chnltoken) {
        this.chnltoken = chnltoken;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }*/

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /*public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }*/

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Menulist> getDataMenu() {
        return dataMenu;
    }

    public void setDataMenu(List<Menulist> dataMenu) {
        this.dataMenu = dataMenu;
    }
}
