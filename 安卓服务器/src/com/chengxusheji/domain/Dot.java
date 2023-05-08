package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Dot {
    /*网点id*/
    private int dotId;
    public int getDotId() {
        return dotId;
    }
    public void setDotId(int dotId) {
        this.dotId = dotId;
    }

    /*航空公司*/
    private Company companyObj;
    public Company getCompanyObj() {
        return companyObj;
    }
    public void setCompanyObj(Company companyObj) {
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
    private City cityObj;
    public City getCityObj() {
        return cityObj;
    }
    public void setCityObj(City cityObj) {
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