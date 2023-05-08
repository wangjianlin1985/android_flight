package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Dot;
import com.mobileserver.util.DB;

public class DotDAO {

	public List<Dot> QueryDot(int companyObj,String title,String cityObj,String telephone) {
		List<Dot> dotList = new ArrayList<Dot>();
		DB db = new DB();
		String sql = "select * from Dot where 1=1";
		if (companyObj != 0)
			sql += " and companyObj=" + companyObj;
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if (!cityObj.equals(""))
			sql += " and cityObj = '" + cityObj + "'";
		if (!telephone.equals(""))
			sql += " and telephone like '%" + telephone + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Dot dot = new Dot();
				dot.setDotId(rs.getInt("dotId"));
				dot.setCompanyObj(rs.getInt("companyObj"));
				dot.setTitle(rs.getString("title"));
				dot.setCityObj(rs.getString("cityObj"));
				dot.setTelephone(rs.getString("telephone"));
				dot.setFax(rs.getString("fax"));
				dot.setAddress(rs.getString("address"));
				dotList.add(dot);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return dotList;
	}
	/* ����������󣬽�����������ҵ�� */
	public String AddDot(Dot dot) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в��������� */
			String sqlString = "insert into Dot(companyObj,title,cityObj,telephone,fax,address) values (";
			sqlString += dot.getCompanyObj() + ",";
			sqlString += "'" + dot.getTitle() + "',";
			sqlString += "'" + dot.getCityObj() + "',";
			sqlString += "'" + dot.getTelephone() + "',";
			sqlString += "'" + dot.getFax() + "',";
			sqlString += "'" + dot.getAddress() + "'";
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
	public String DeleteDot(int dotId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Dot where dotId=" + dotId;
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

	/* ��������id��ȡ������ */
	public Dot GetDot(int dotId) {
		Dot dot = null;
		DB db = new DB();
		String sql = "select * from Dot where dotId=" + dotId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				dot = new Dot();
				dot.setDotId(rs.getInt("dotId"));
				dot.setCompanyObj(rs.getInt("companyObj"));
				dot.setTitle(rs.getString("title"));
				dot.setCityObj(rs.getString("cityObj"));
				dot.setTelephone(rs.getString("telephone"));
				dot.setFax(rs.getString("fax"));
				dot.setAddress(rs.getString("address"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return dot;
	}
	/* �������� */
	public String UpdateDot(Dot dot) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Dot set ";
			sql += "companyObj=" + dot.getCompanyObj() + ",";
			sql += "title='" + dot.getTitle() + "',";
			sql += "cityObj='" + dot.getCityObj() + "',";
			sql += "telephone='" + dot.getTelephone() + "',";
			sql += "fax='" + dot.getFax() + "',";
			sql += "address='" + dot.getAddress() + "'";
			sql += " where dotId=" + dot.getDotId();
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
