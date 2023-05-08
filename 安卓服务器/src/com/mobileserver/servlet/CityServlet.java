package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.CityDAO;
import com.mobileserver.domain.City;

import org.json.JSONStringer;

public class CityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造城市业务层对象*/
	private CityDAO cityDAO = new CityDAO();

	/*默认构造函数*/
	public CityServlet() {
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
			/*获取查询城市的参数信息*/

			/*调用业务逻辑层执行城市查询*/
			List<City> cityList = cityDAO.QueryCity();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Citys>").append("\r\n");
			for (int i = 0; i < cityList.size(); i++) {
				sb.append("	<City>").append("\r\n")
				.append("		<cityNo>")
				.append(cityList.get(i).getCityNo())
				.append("</cityNo>").append("\r\n")
				.append("		<cityName>")
				.append(cityList.get(i).getCityName())
				.append("</cityName>").append("\r\n")
				.append("	</City>").append("\r\n");
			}
			sb.append("</Citys>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(City city: cityList) {
				  stringer.object();
			  stringer.key("cityNo").value(city.getCityNo());
			  stringer.key("cityName").value(city.getCityName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加城市：获取城市参数，参数保存到新建的城市对象 */ 
			City city = new City();
			String cityNo = new String(request.getParameter("cityNo").getBytes("iso-8859-1"), "UTF-8");
			city.setCityNo(cityNo);
			String cityName = new String(request.getParameter("cityName").getBytes("iso-8859-1"), "UTF-8");
			city.setCityName(cityName);

			/* 调用业务层执行添加操作 */
			String result = cityDAO.AddCity(city);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除城市：获取城市的城市编号*/
			String cityNo = new String(request.getParameter("cityNo").getBytes("iso-8859-1"), "UTF-8");
			/*调用业务逻辑层执行删除操作*/
			String result = cityDAO.DeleteCity(cityNo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新城市之前先根据cityNo查询某个城市*/
			String cityNo = new String(request.getParameter("cityNo").getBytes("iso-8859-1"), "UTF-8");
			City city = cityDAO.GetCity(cityNo);

			// 客户端查询的城市对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("cityNo").value(city.getCityNo());
			  stringer.key("cityName").value(city.getCityName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新城市：获取城市参数，参数保存到新建的城市对象 */ 
			City city = new City();
			String cityNo = new String(request.getParameter("cityNo").getBytes("iso-8859-1"), "UTF-8");
			city.setCityNo(cityNo);
			String cityName = new String(request.getParameter("cityName").getBytes("iso-8859-1"), "UTF-8");
			city.setCityName(cityName);

			/* 调用业务层执行更新操作 */
			String result = cityDAO.UpdateCity(city);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
