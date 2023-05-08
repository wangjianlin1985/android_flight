package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.City;
import com.mobileserver.util.DB;

public class CityDAO {

	public List<City> QueryCity() {
		List<City> cityList = new ArrayList<City>();
		DB db = new DB();
		String sql = "select * from City where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				City city = new City();
				city.setCityNo(rs.getString("cityNo"));
				city.setCityName(rs.getString("cityName"));
				cityList.add(city);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return cityList;
	}
	/* 传入城市对象，进行城市的添加业务 */
	public String AddCity(City city) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新城市 */
			String sqlString = "insert into City(cityNo,cityName) values (";
			sqlString += "'" + city.getCityNo() + "',";
			sqlString += "'" + city.getCityName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "城市添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "城市添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除城市 */
	public String DeleteCity(String cityNo) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from City where cityNo='" + cityNo + "'";
			db.executeUpdate(sqlString);
			result = "城市删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "城市删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据城市编号获取到城市 */
	public City GetCity(String cityNo) {
		City city = null;
		DB db = new DB();
		String sql = "select * from City where cityNo='" + cityNo + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				city = new City();
				city.setCityNo(rs.getString("cityNo"));
				city.setCityName(rs.getString("cityName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return city;
	}
	/* 更新城市 */
	public String UpdateCity(City city) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update City set ";
			sql += "cityName='" + city.getCityName() + "'";
			sql += " where cityNo='" + city.getCityNo() + "'";
			db.executeUpdate(sql);
			result = "城市更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "城市更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
