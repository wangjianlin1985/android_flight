package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Flight {
    /*��¼id*/
    private int flightId;
    public int getFlightId() {
        return flightId;
    }
    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    /*�����*/
    private String flightNo;
    public String getFlightNo() {
        return flightNo;
    }
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    /*���չ�˾*/
    private Company comparyObj;
    public Company getComparyObj() {
        return comparyObj;
    }
    public void setComparyObj(Company comparyObj) {
        this.comparyObj = comparyObj;
    }

    /*��������*/
    private City startCity;
    public City getStartCity() {
        return startCity;
    }
    public void setStartCity(City startCity) {
        this.startCity = startCity;
    }

    /*�������*/
    private City arriveCity;
    public City getArriveCity() {
        return arriveCity;
    }
    public void setArriveCity(City arriveCity) {
        this.arriveCity = arriveCity;
    }

    /*��������*/
    private Timestamp flightDate;
    public Timestamp getFlightDate() {
        return flightDate;
    }
    public void setFlightDate(Timestamp flightDate) {
        this.flightDate = flightDate;
    }

    /*���ʱ��*/
    private String flyTime;
    public String getFlyTime() {
        return flyTime;
    }
    public void setFlyTime(String flyTime) {
        this.flyTime = flyTime;
    }

    /*���¥*/
    private String waitFloor;
    public String getWaitFloor() {
        return waitFloor;
    }
    public void setWaitFloor(String waitFloor) {
        this.waitFloor = waitFloor;
    }

    /*�ӻ�¥*/
    private String meetFloor;
    public String getMeetFloor() {
        return meetFloor;
    }
    public void setMeetFloor(String meetFloor) {
        this.meetFloor = meetFloor;
    }

}