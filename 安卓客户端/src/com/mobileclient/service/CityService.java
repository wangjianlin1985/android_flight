package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.City;
import com.mobileclient.util.HttpUtil;

/*城市管理业务逻辑层*/
public class CityService {
	/* 添加城市 */
	public String AddCity(City city) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cityNo", city.getCityNo());
		params.put("cityName", city.getCityName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CityServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询城市 */
	public List<City> QueryCity(City queryConditionCity) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CityServlet?action=query";
		if(queryConditionCity != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		CityListHandler cityListHander = new CityListHandler();
		xr.setContentHandler(cityListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<City> cityList = cityListHander.getCityList();
		return cityList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<City> cityList = new ArrayList<City>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				City city = new City();
				city.setCityNo(object.getString("cityNo"));
				city.setCityName(object.getString("cityName"));
				cityList.add(city);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityList;
	}

	/* 更新城市 */
	public String UpdateCity(City city) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cityNo", city.getCityNo());
		params.put("cityName", city.getCityName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CityServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除城市 */
	public String DeleteCity(String cityNo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cityNo", cityNo);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CityServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "城市信息删除失败!";
		}
	}

	/* 根据城市编号获取城市对象 */
	public City GetCity(String cityNo)  {
		List<City> cityList = new ArrayList<City>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cityNo", cityNo);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CityServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				City city = new City();
				city.setCityNo(object.getString("cityNo"));
				city.setCityName(object.getString("cityName"));
				cityList.add(city);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = cityList.size();
		if(size>0) return cityList.get(0); 
		else return null; 
	}
}
