package com.mobileserver.domain;

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
    private int comparyObj;
    public int getComparyObj() {
        return comparyObj;
    }
    public void setComparyObj(int comparyObj) {
        this.comparyObj = comparyObj;
    }

    /*��������*/
    private String startCity;
    public String getStartCity() {
        return startCity;
    }
    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    /*�������*/
    private String arriveCity;
    public String getArriveCity() {
        return arriveCity;
    }
    public void setArriveCity(String arriveCity) {
        this.arriveCity = arriveCity;
    }

    /*��������*/
    private java.sql.Timestamp flightDate;
    public java.sql.Timestamp getFlightDate() {
        return flightDate;
    }
    public void setFlightDate(java.sql.Timestamp flightDate) {
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