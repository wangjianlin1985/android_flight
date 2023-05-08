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

	/*构造航班业务层对象*/
	private FlightDAO flightDAO = new FlightDAO();

	/*默认构造函数*/
	public FlightServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询航班的参数信息*/
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

			/*调用业务逻辑层执行航班查询*/
			List<Flight> flightList = flightDAO.QueryFlight(flightNo,comparyObj,startCity,arriveCity,flightDate);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加航班：获取航班参数，参数保存到新建的航班对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = flightDAO.AddFlight(flight);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除航班：获取航班的记录id*/
			int flightId = Integer.parseInt(request.getParameter("flightId"));
			/*调用业务逻辑层执行删除操作*/
			String result = flightDAO.DeleteFlight(flightId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新航班之前先根据flightId查询某个航班*/
			int flightId = Integer.parseInt(request.getParameter("flightId"));
			Flight flight = flightDAO.GetFlight(flightId);

			// 客户端查询的航班对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新航班：获取航班参数，参数保存到新建的航班对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = flightDAO.UpdateFlight(flight);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
