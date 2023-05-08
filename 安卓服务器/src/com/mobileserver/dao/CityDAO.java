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
	/* ������ж��󣬽��г��е����ҵ�� */
	public String AddCity(City city) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����³��� */
			String sqlString = "insert into City(cityNo,cityName) values (";
			sqlString += "'" + city.getCityNo() + "',";
			sqlString += "'" + city.getCityName() + "'";
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
	public String DeleteCity(String cityNo) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from City where cityNo='" + cityNo + "'";
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

	/* ���ݳ��б�Ż�ȡ������ */
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
	/* ���³��� */
	public String UpdateCity(City city) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update City set ";
			sql += "cityName='" + city.getCityName() + "'";
			sql += " where cityNo='" + city.getCityNo() + "'";
			db.executeUpdate(sql);
			result = "���и��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���и���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
