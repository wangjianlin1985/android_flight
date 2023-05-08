package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Flight {
    /*记录id*/
    private int flightId;
    public int getFlightId() {
        return flightId;
    }
    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    /*航班号*/
    private String flightNo;
    public String getFlightNo() {
        return flightNo;
    }
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    /*航空公司*/
    private Company comparyObj;
    public Company getComparyObj() {
        return comparyObj;
    }
    public void setComparyObj(Company comparyObj) {
        this.comparyObj = comparyObj;
    }

    /*出发城市*/
    private City startCity;
    public City getStartCity() {
        return startCity;
    }
    public void setStartCity(City startCity) {
        this.startCity = startCity;
    }

    /*到达城市*/
    private City arriveCity;
    public City getArriveCity() {
        return arriveCity;
    }
    public void setArriveCity(City arriveCity) {
        this.arriveCity = arriveCity;
    }

    /*航班日期*/
    private Timestamp flightDate;
    public Timestamp getFlightDate() {
        return flightDate;
    }
    public void setFlightDate(Timestamp flightDate) {
        this.flightDate = flightDate;
    }

    /*起飞时间*/
    private String flyTime;
    public String getFlyTime() {
        return flyTime;
    }
    public void setFlyTime(String flyTime) {
        this.flyTime = flyTime;
    }

    /*候机楼*/
    private String waitFloor;
    public String getWaitFloor() {
        return waitFloor;
    }
    public void setWaitFloor(String waitFloor) {
        this.waitFloor = waitFloor;
    }

    /*接机楼*/
    private String meetFloor;
    public String getMeetFloor() {
        return meetFloor;
    }
    public void setMeetFloor(String meetFloor) {
        this.meetFloor = meetFloor;
    }

}