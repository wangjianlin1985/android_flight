package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Company;
import com.mobileserver.util.DB;

public class CompanyDAO {

	public List<Company> QueryCompany(String companyName,String personName,String telephone,Timestamp bornDate) {
		List<Company> companyList = new ArrayList<Company>();
		DB db = new DB();
		String sql = "select * from Company where 1=1";
		if (!companyName.equals(""))
			sql += " and companyName like '%" + companyName + "%'";
		if (!personName.equals(""))
			sql += " and personName like '%" + personName + "%'";
		if (!telephone.equals(""))
			sql += " and telephone like '%" + telephone + "%'";
		if(bornDate!=null)
			sql += " and bornDate='" + bornDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Company company = new Company();
				company.setCompanyId(rs.getInt("companyId"));
				company.setCompanyName(rs.getString("companyName"));
				company.setPersonName(rs.getString("personName"));
				company.setTelephone(rs.getString("telephone"));
				company.setBornDate(rs.getTimestamp("bornDate"));
				companyList.add(company);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return companyList;
	}
	/* ���뺽�չ�˾���󣬽��к��չ�˾�����ҵ�� */
	public String AddCompany(Company company) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����º��չ�˾ */
			String sqlString = "insert into Company(companyName,personName,telephone,bornDate) values (";
			sqlString += "'" + company.getCompanyName() + "',";
			sqlString += "'" + company.getPersonName() + "',";
			sqlString += "'" + company.getTelephone() + "',";
			sqlString += "'" + company.getBornDate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "���չ�˾��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���չ�˾���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ�����չ�˾ */
	public String DeleteCompany(int companyId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Company where companyId=" + companyId;
			db.executeUpdate(sqlString);
			result = "���չ�˾ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���չ�˾ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݹ�˾id��ȡ�����չ�˾ */
	public Company GetCompany(int companyId) {
		Company company = null;
		DB db = new DB();
		String sql = "select * from Company where companyId=" + companyId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				company = new Company();
				company.setCompanyId(rs.getInt("companyId"));
				company.setCompanyName(rs.getString("companyName"));
				company.setPersonName(rs.getString("personName"));
				company.setTelephone(rs.getString("telephone"));
				company.setBornDate(rs.getTimestamp("bornDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return company;
	}
	/* ���º��չ�˾ */
	public String UpdateCompany(Company company) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Company set ";
			sql += "companyName='" + company.getCompanyName() + "',";
			sql += "personName='" + company.getPersonName() + "',";
			sql += "telephone='" + company.getTelephone() + "',";
			sql += "bornDate='" + company.getBornDate() + "'";
			sql += " where companyId=" + company.getCompanyId();
			db.executeUpdate(sql);
			result = "���չ�˾���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���չ�˾����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
