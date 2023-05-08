package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Company;
import com.mobileclient.util.HttpUtil;

/*航空公司管理业务逻辑层*/
public class CompanyService {
	/* 添加航空公司 */
	public String AddCompany(Company company) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("companyId", company.getCompanyId() + "");
		params.put("companyName", company.getCompanyName());
		params.put("personName", company.getPersonName());
		params.put("telephone", company.getTelephone());
		params.put("bornDate", company.getBornDate().toString());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CompanyServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询航空公司 */
	public List<Company> QueryCompany(Company queryConditionCompany) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CompanyServlet?action=query";
		if(queryConditionCompany != null) {
			urlString += "&companyName=" + URLEncoder.encode(queryConditionCompany.getCompanyName(), "UTF-8") + "";
			urlString += "&personName=" + URLEncoder.encode(queryConditionCompany.getPersonName(), "UTF-8") + "";
			urlString += "&telephone=" + URLEncoder.encode(queryConditionCompany.getTelephone(), "UTF-8") + "";
			if(queryConditionCompany.getBornDate() != null) {
				urlString += "&bornDate=" + URLEncoder.encode(queryConditionCompany.getBornDate().toString(), "UTF-8");
			}
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		CompanyListHandler companyListHander = new CompanyListHandler();
		xr.setContentHandler(companyListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Company> companyList = companyListHander.getCompanyList();
		return companyList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Company> companyList = new ArrayList<Company>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Company company = new Company();
				company.setCompanyId(object.getInt("companyId"));
				company.setCompanyName(object.getString("companyName"));
				company.setPersonName(object.getString("personName"));
				company.setTelephone(object.getString("telephone"));
				company.setBornDate(Timestamp.valueOf(object.getString("bornDate")));
				companyList.add(company);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return companyList;
	}

	/* 更新航空公司 */
	public String UpdateCompany(Company company) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("companyId", company.getCompanyId() + "");
		params.put("companyName", company.getCompanyName());
		params.put("personName", company.getPersonName());
		params.put("telephone", company.getTelephone());
		params.put("bornDate", company.getBornDate().toString());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CompanyServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除航空公司 */
	public String DeleteCompany(int companyId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("companyId", companyId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CompanyServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "航空公司信息删除失败!";
		}
	}

	/* 根据公司id获取航空公司对象 */
	public Company GetCompany(int companyId)  {
		List<Company> companyList = new ArrayList<Company>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("companyId", companyId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CompanyServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Company company = new Company();
				company.setCompanyId(object.getInt("companyId"));
				company.setCompanyName(object.getString("companyName"));
				company.setPersonName(object.getString("personName"));
				company.setTelephone(object.getString("telephone"));
				company.setBornDate(Timestamp.valueOf(object.getString("bornDate")));
				companyList.add(company);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = companyList.size();
		if(size>0) return companyList.get(0); 
		else return null; 
	}
}
