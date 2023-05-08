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
	/* 传入航班对象，进行航班的添加业务 */
	public String AddFlight(Flight flight) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新航班 */
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
			result = "航班添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "航班添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除航班 */
	public String DeleteFlight(int flightId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Flight where flightId=" + flightId;
			db.executeUpdate(sqlString);
			result = "航班删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "航班删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录id获取到航班 */
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
	/* 更新航班 */
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
			result = "航班更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "航班更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
