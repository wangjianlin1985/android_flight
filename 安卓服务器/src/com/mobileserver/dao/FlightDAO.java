package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Flight;
import com.mobileserver.util.DB;

public class FlightDAO {

	public List<Flight> QueryFlight(String flightNo,int comparyObj,String startCity,String arriveCity,Timestamp flightDate) {
		List<Flight> flightList = new ArrayList<Flight>();
		DB db = new DB();
		String sql = "select * from Flight where 1=1";
		if (!flightNo.equals(""))
			sql += " and flightNo like '%" + flightNo + "%'";
		if (comparyObj != 0)
			sql += " and comparyObj=" + comparyObj;
		if (!startCity.equals(""))
			sql += " and startCity = '" + startCity + "'";
		if (!arriveCity.equals(""))
			sql += " and arriveCity = '" + arriveCity + "'";
		if(flightDate!=null)
			sql += " and flightDate='" + flightDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Flight flight = new Flight();
				flight.setFlightId(rs.getInt("flightId"));
				flight.setFlightNo(rs.getString("flightNo"));
				flight.setComparyObj(rs.getInt("comparyObj"));
				flight.setStartCity(rs.getString("startCity"));
				flight.setArriveCity(rs.getString("arriveCity"));
				flight.setFlightDate(rs.getTimestamp("flightDate"));
				flight.setFlyTime(rs.getString("flyTime"));
				flight.setWaitFloor(rs.getString("waitFloor"));
				flight.setMeetFloor(rs.getString("meetFloor"));
				flightList.add(flight);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return flightList;
	}
	/* ���뺽����󣬽��к�������ҵ�� */
	public String AddFlight(Flight flight) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����º��� */
			String sqlString = "insert into Flight(flightNo,comparyObj,startCity,arriveCity,flightDate,flyTime,waitFloor,meetFloor) values (";
			sqlString += "'" + flight.getFlightNo() + "',";
			sqlString += flight.getComparyObj() + ",";
			sqlString += "'" + flight.getStartCity() + "',";
			sqlString += "'" + flight.getArriveCity() + "',";
			sqlString += "'" + flight.getFlightDate() + "',";
			sqlString += "'" + flight.getFlyTime() + "',";
			sqlString += "'" + flight.getWaitFloor() + "',";
			sqlString += "'" + flight.getMeetFloor() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ������ */
	public String DeleteFlight(int flightId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Flight where flightId=" + flightId;
			db.executeUpdate(sqlString);
			result = "����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼id��ȡ������ */
	public Flight GetFlight(int flightId) {
		Flight flight = null;
		DB db = new DB();
		String sql = "select * from Flight where flightId=" + flightId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				flight = new Flight();
				flight.setFlightId(rs.getInt("flightId"));
				flight.setFlightNo(rs.getString("flightNo"));
				flight.setComparyObj(rs.getInt("comparyObj"));
				flight.setStartCity(rs.getString("startCity"));
				flight.setArriveCity(rs.getString("arriveCity"));
				flight.setFlightDate(rs.getTimestamp("flightDate"));
				flight.setFlyTime(rs.getString("flyTime"));
				flight.setWaitFloor(rs.getString("waitFloor"));
				flight.setMeetFloor(rs.getString("meetFloor"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return flight;
	}
	/* ���º��� */
	public String UpdateFlight(Flight flight) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Flight set ";
			sql += "flightNo='" + flight.getFlightNo() + "',";
			sql += "comparyObj=" + flight.getComparyObj() + ",";
			sql += "startCity='" + flight.getStartCity() + "',";
			sql += "arriveCity='" + flight.getArriveCity() + "',";
			sql += "flightDate='" + flight.getFlightDate() + "',";
			sql += "flyTime='" + flight.getFlyTime() + "',";
			sql += "waitFloor='" + flight.getWaitFloor() + "',";
			sql += "meetFloor='" + flight.getMeetFloor() + "'";
			sql += " where flightId=" + flight.getFlightId();
			db.executeUpdate(sql);
			result = "������³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
