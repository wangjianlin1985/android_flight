package com.mobileclient.domain;

import java.io.Serializable;

public class City implements Serializable {
    /*城市编号*/
    private String cityNo;
    public String getCityNo() {
        return cityNo;
    }
    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    /*城市名称*/
    private String cityName;
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}