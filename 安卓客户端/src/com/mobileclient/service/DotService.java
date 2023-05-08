package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Dot;
import com.mobileclient.util.HttpUtil;

/*网点管理业务逻辑层*/
public class DotService {
	/* 添加网点 */
	public String AddDot(Dot dot) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("dotId", dot.getDotId() + "");
		params.put("companyObj", dot.getCompanyObj() + "");
		params.put("title", dot.getTitle());
		params.put("cityObj", dot.getCityObj());
		params.put("telephone", dot.getTelephone());
		params.put("fax", dot.getFax());
		params.put("address", dot.getAddress());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DotServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询网点 */
	public List<Dot> QueryDot(Dot queryConditionDot) throws Exception {
		String urlString = HttpUtil.BASE_URL + "DotServlet?action=query";
		if(queryConditionDot != null) {
			urlString += "&companyObj=" + queryConditionDot.getCompanyObj();
			urlString += "&title=" + URLEncoder.encode(queryConditionDot.getTitle(), "UTF-8") + "";
			urlString += "&cityObj=" + URLEncoder.encode(queryConditionDot.getCityObj(), "UTF-8") + "";
			urlString += "&telephone=" + URLEncoder.encode(queryConditionDot.getTelephone(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		DotListHandler dotListHander = new DotListHandler();
		xr.setContentHandler(dotListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Dot> dotList = dotListHander.getDotList();
		return dotList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Dot> dotList = new ArrayList<Dot>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Dot dot = new Dot();
				dot.setDotId(object.getInt("dotId"));
				dot.setCompanyObj(object.getInt("companyObj"));
				dot.setTitle(object.getString("title"));
				dot.setCityObj(object.getString("cityObj"));
				dot.setTelephone(object.getString("telephone"));
				dot.setFax(object.getString("fax"));
				dot.setAddress(object.getString("address"));
				dotList.add(dot);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dotList;
	}

	/* 更新网点 */
	public String UpdateDot(Dot dot) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("dotId", dot.getDotId() + "");
		params.put("companyObj", dot.getCompanyObj() + "");
		params.put("title", dot.getTitle());
		params.put("cityObj", dot.getCityObj());
		params.put("telephone", dot.getTelephone());
		params.put("fax", dot.getFax());
		params.put("address", dot.getAddress());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DotServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除网点 */
	public String DeleteDot(int dotId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("dotId", dotId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DotServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "网点信息删除失败!";
		}
	}

	/* 根据网点id获取网点对象 */
	public Dot GetDot(int dotId)  {
		List<Dot> dotList = new ArrayList<Dot>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("dotId", dotId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DotServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Dot dot = new Dot();
				dot.setDotId(object.getInt("dotId"));
				dot.setCompanyObj(object.getInt("companyObj"));
				dot.setTitle(object.getString("title"));
				dot.setCityObj(object.getString("cityObj"));
				dot.setTelephone(object.getString("telephone"));
				dot.setFax(object.getString("fax"));
				dot.setAddress(object.getString("address"));
				dotList.add(dot);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = dotList.size();
		if(size>0) return dotList.get(0); 
		else return null; 
	}
}
