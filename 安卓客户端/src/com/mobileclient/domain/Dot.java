package com.mobileclient.domain;

import java.io.Serializable;

public class Dot implements Serializable {
    /*网点id*/
    private int dotId;
    public int getDotId() {
        return dotId;
    }
    public void setDotId(int dotId) {
        this.dotId = dotId;
    }

    /*航空公司*/
    private int companyObj;
    public int getCompanyObj() {
        return companyObj;
    }
    public void setCompanyObj(int companyObj) {
        this.companyObj = companyObj;
    }

    /*网点名称*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*城市*/
    private String cityObj;
    public String getCityObj() {
        return cityObj;
    }
    public void setCityObj(String cityObj) {
        this.cityObj = cityObj;
    }

    /*电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*传真*/
    private String fax;
    public String getFax() {
        return fax;
    }
    public void setFax(String fax) {
        this.fax = fax;
    }

    /*地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

}