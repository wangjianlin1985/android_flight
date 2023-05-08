package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.FlightDAO;
import com.mobileserver.domain.Flight;

import org.json.JSONStringer;

public class FlightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*���캽��ҵ������*/
	private FlightDAO flightDAO = new FlightDAO();

	/*Ĭ�Ϲ��캯��*/
	public FlightServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ����Ĳ�����Ϣ*/
			String flightNo = request.getParameter("flightNo");
			flightNo = flightNo == null ? "" : new String(request.getParameter(
					"flightNo").getBytes("iso-8859-1"), "UTF-8");
			int comparyObj = 0;
			if (request.getParameter("comparyObj") != null)
				comparyObj = Integer.parseInt(request.getParameter("comparyObj"));
			String startCity = "";
			if (request.getParameter("startCity") != null)
				startCity = request.getParameter("startCity");
			String arriveCity = "";
			if (request.getParameter("arriveCity") != null)
				arriveCity = request.getParameter("arriveCity");
			Timestamp flightDate = null;
			if (request.getParameter("flightDate") != null)
				flightDate = Timestamp.valueOf(request.getParameter("flightDate"));

			/*����ҵ���߼���ִ�к����ѯ*/
			List<Flight> flightList = flightDAO.QueryFlight(flightNo,comparyObj,startCity,arriveCity,flightDate);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Flights>").append("\r\n");
			for (int i = 0; i < flightList.size(); i++) {
				sb.append("	<Flight>").append("\r\n")
				.append("		<flightId>")
				.append(flightList.get(i).getFlightId())
				.append("</flightId>").append("\r\n")
				.append("		<flightNo>")
				.append(flightList.get(i).getFlightNo())
				.append("</flightNo>").append("\r\n")
				.append("		<comparyObj>")
				.append(flightList.get(i).getComparyObj())
				.append("</comparyObj>").append("\r\n")
				.append("		<startCity>")
				.append(flightList.get(i).getStartCity())
				.append("</startCity>").append("\r\n")
				.append("		<arriveCity>")
				.append(flightList.get(i).getArriveCity())
				.append("</arriveCity>").append("\r\n")
				.append("		<flightDate>")
				.append(flightList.get(i).getFlightDate())
				.append("</flightDate>").append("\r\n")
				.append("		<flyTime>")
				.append(flightList.get(i).getFlyTime())
				.append("</flyTime>").append("\r\n")
				.append("		<waitFloor>")
				.append(flightList.get(i).getWaitFloor())
				.append("</waitFloor>").append("\r\n")
				.append("		<meetFloor>")
				.append(flightList.get(i).getMeetFloor())
				.append("</meetFloor>").append("\r\n")
				.append("	</Flight>").append("\r\n");
			}
			sb.append("</Flights>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Flight flight: flightList) {
				  stringer.object();
			  stringer.key("flightId").value(flight.getFlightId());
			  stringer.key("flightNo").value(flight.getFlightNo());
			  stringer.key("comparyObj").value(flight.getComparyObj());
			  stringer.key("startCity").value(flight.getStartCity());
			  stringer.key("arriveCity").value(flight.getArriveCity());
			  stringer.key("flightDate").value(flight.getFlightDate());
			  stringer.key("flyTime").value(flight.getFlyTime());
			  stringer.key("waitFloor").value(flight.getWaitFloor());
			  stringer.key("meetFloor").value(flight.getMeetFloor());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��Ӻ��ࣺ��ȡ����������������浽�½��ĺ������ */ 
			Flight flight = new Flight();
			int flightId = Integer.parseInt(request.getParameter("flightId"));
			flight.setFlightId(flightId);
			String flightNo = new String(request.getParameter("flightNo").getBytes("iso-8859-1"), "UTF-8");
			flight.setFlightNo(flightNo);
			int comparyObj = Integer.parseInt(request.getParameter("comparyObj"));
			flight.setComparyObj(comparyObj);
			String startCity = new String(request.getParameter("startCity").getBytes("iso-8859-1"), "UTF-8");
			flight.setStartCity(startCity);
			String arriveCity = new String(request.getParameter("arriveCity").getBytes("iso-8859-1"), "UTF-8");
			flight.setArriveCity(arriveCity);
			Timestamp flightDate = Timestamp.valueOf(request.getParameter("flightDate"));
			flight.setFlightDate(flightDate);
			String flyTime = new String(request.getParameter("flyTime").getBytes("iso-8859-1"), "UTF-8");
			flight.setFlyTime(flyTime);
			String waitFloor = new String(request.getParameter("waitFloor").getBytes("iso-8859-1"), "UTF-8");
			flight.setWaitFloor(waitFloor);
			String meetFloor = new String(request.getParameter("meetFloor").getBytes("iso-8859-1"), "UTF-8");
			flight.setMeetFloor(meetFloor);

			/* ����ҵ���ִ����Ӳ��� */
			String result = flightDAO.AddFlight(flight);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����ࣺ��ȡ����ļ�¼id*/
			int flightId = Integer.parseInt(request.getParameter("flightId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = flightDAO.DeleteFlight(flightId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���º���֮ǰ�ȸ���flightId��ѯĳ������*/
			int flightId = Integer.parseInt(request.getParameter("flightId"));
			Flight flight = flightDAO.GetFlight(flightId);

			// �ͻ��˲�ѯ�ĺ�����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("flightId").value(flight.getFlightId());
			  stringer.key("flightNo").value(flight.getFlightNo());
			  stringer.key("comparyObj").value(flight.getComparyObj());
			  stringer.key("startCity").value(flight.getStartCity());
			  stringer.key("arriveCity").value(flight.getArriveCity());
			  stringer.key("flightDate").value(flight.getFlightDate());
			  stringer.key("flyTime").value(flight.getFlyTime());
			  stringer.key("waitFloor").value(flight.getWaitFloor());
			  stringer.key("meetFloor").value(flight.getMeetFloor());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���º��ࣺ��ȡ����������������浽�½��ĺ������ */ 
			Flight flight = new Flight();
			int flightId = Integer.parseInt(request.getParameter("flightId"));
			flight.setFlightId(flightId);
			String flightNo = new String(request.getParameter("flightNo").getBytes("iso-8859-1"), "UTF-8");
			flight.setFlightNo(flightNo);
			int comparyObj = Integer.parseInt(request.getParameter("comparyObj"));
			flight.setComparyObj(comparyObj);
			String startCity = new String(request.getParameter("startCity").getBytes("iso-8859-1"), "UTF-8");
			flight.setStartCity(startCity);
			String arriveCity = new String(request.getParameter("arriveCity").getBytes("iso-8859-1"), "UTF-8");
			flight.setArriveCity(arriveCity);
			Timestamp flightDate = Timestamp.valueOf(request.getParameter("flightDate"));
			flight.setFlightDate(flightDate);
			String flyTime = new String(request.getParameter("flyTime").getBytes("iso-8859-1"), "UTF-8");
			flight.setFlyTime(flyTime);
			String waitFloor = new String(request.getParameter("waitFloor").getBytes("iso-8859-1"), "UTF-8");
			flight.setWaitFloor(waitFloor);
			String meetFloor = new String(request.getParameter("meetFloor").getBytes("iso-8859-1"), "UTF-8");
			flight.setMeetFloor(meetFloor);

			/* ����ҵ���ִ�и��²��� */
			String result = flightDAO.UpdateFlight(flight);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
