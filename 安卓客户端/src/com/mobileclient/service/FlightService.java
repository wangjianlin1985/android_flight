package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Flight;
import com.mobileclient.util.HttpUtil;

/*航班管理业务逻辑层*/
public class FlightService {
	/* 添加航班 */
	public String AddFlight(Flight flight) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("flightId", flight.getFlightId() + "");
		params.put("flightNo", flight.getFlightNo());
		params.put("comparyObj", flight.getComparyObj() + "");
		params.put("startCity", flight.getStartCity());
		params.put("arriveCity", flight.getArriveCity());
		params.put("flightDate", flight.getFlightDate().toString());
		params.put("flyTime", flight.getFlyTime());
		params.put("waitFloor", flight.getWaitFloor());
		params.put("meetFloor", flight.getMeetFloor());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "FlightServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询航班 */
	public List<Flight> QueryFlight(Flight queryConditionFlight) throws Exception {
		String urlString = HttpUtil.BASE_URL + "FlightServlet?action=query";
		if(queryConditionFlight != null) {
			urlString += "&flightNo=" + URLEncoder.encode(queryConditionFlight.getFlightNo(), "UTF-8") + "";
			urlString += "&comparyObj=" + queryConditionFlight.getComparyObj();
			urlString += "&startCity=" + URLEncoder.encode(queryConditionFlight.getStartCity(), "UTF-8") + "";
			urlString += "&arriveCity=" + URLEncoder.encode(queryConditionFlight.getArriveCity(), "UTF-8") + "";
			if(queryConditionFlight.getFlightDate() != null) {
				urlString += "&flightDate=" + URLEncoder.encode(queryConditionFlight.getFlightDate().toString(), "UTF-8");
			}
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		FlightListHandler flightListHander = new FlightListHandler();
		xr.setContentHandler(flightListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Flight> flightList = flightListHander.getFlightList();
		return flightList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Flight> flightList = new ArrayList<Flight>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Flight flight = new Flight();
				flight.setFlightId(object.getInt("flightId"));
				flight.setFlightNo(object.getString("flightNo"));
				flight.setComparyObj(object.getInt("comparyObj"));
				flight.setStartCity(object.getString("startCity"));
				flight.setArriveCity(object.getString("arriveCity"));
				flight.setFlightDate(Timestamp.valueOf(object.getString("flightDate")));
				flight.setFlyTime(object.getString("flyTime"));
				flight.setWaitFloor(object.getString("waitFloor"));
				flight.setMeetFloor(object.getString("meetFloor"));
				flightList.add(flight);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flightList;
	}

	/* 更新航班 */
	public String UpdateFlight(Flight flight) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("flightId", flight.getFlightId() + "");
		params.put("flightNo", flight.getFlightNo());
		params.put("comparyObj", flight.getComparyObj() + "");
		params.put("startCity", flight.getStartCity());
		params.put("arriveCity", flight.getArriveCity());
		params.put("flightDate", flight.getFlightDate().toString());
		params.put("flyTime", flight.getFlyTime());
		params.put("waitFloor", flight.getWaitFloor());
		params.put("meetFloor", flight.getMeetFloor());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "FlightServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除航班 */
	public String DeleteFlight(int flightId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("flightId", flightId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "FlightServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "航班信息删除失败!";
		}
	}

	/* 根据记录id获取航班对象 */
	public Flight GetFlight(int flightId)  {
		List<Flight> flightList = new ArrayList<Flight>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("flightId", flightId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "FlightServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Flight flight = new Flight();
				flight.setFlightId(object.getInt("flightId"));
				flight.setFlightNo(object.getString("flightNo"));
				flight.setComparyObj(object.getInt("comparyObj"));
				flight.setStartCity(object.getString("startCity"));
				flight.setArriveCity(object.getString("arriveCity"));
				flight.setFlightDate(Timestamp.valueOf(object.getString("flightDate")));
				flight.setFlyTime(object.getString("flyTime"));
				flight.setWaitFloor(object.getString("waitFloor"));
				flight.setMeetFloor(object.getString("meetFloor"));
				flightList.add(flight);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = flightList.size();
		if(size>0) return flightList.get(0); 
		else return null; 
	}
}
