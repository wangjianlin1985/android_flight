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

	/*�������ҵ������*/
	private CityDAO cityDAO = new CityDAO();

	/*Ĭ�Ϲ��캯��*/
	public CityServlet() {
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
			/*��ȡ��ѯ���еĲ�����Ϣ*/

			/*����ҵ���߼���ִ�г��в�ѯ*/
			List<City> cityList = cityDAO.QueryCity();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӳ��У���ȡ���в������������浽�½��ĳ��ж��� */ 
			City city = new City();
			String cityNo = new String(request.getParameter("cityNo").getBytes("iso-8859-1"), "UTF-8");
			city.setCityNo(cityNo);
			String cityName = new String(request.getParameter("cityName").getBytes("iso-8859-1"), "UTF-8");
			city.setCityName(cityName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = cityDAO.AddCity(city);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����У���ȡ���еĳ��б��*/
			String cityNo = new String(request.getParameter("cityNo").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = cityDAO.DeleteCity(cityNo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���³���֮ǰ�ȸ���cityNo��ѯĳ������*/
			String cityNo = new String(request.getParameter("cityNo").getBytes("iso-8859-1"), "UTF-8");
			City city = cityDAO.GetCity(cityNo);

			// �ͻ��˲�ѯ�ĳ��ж��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���³��У���ȡ���в������������浽�½��ĳ��ж��� */ 
			City city = new City();
			String cityNo = new String(request.getParameter("cityNo").getBytes("iso-8859-1"), "UTF-8");
			city.setCityNo(cityNo);
			String cityName = new String(request.getParameter("cityName").getBytes("iso-8859-1"), "UTF-8");
			city.setCityName(cityName);

			/* ����ҵ���ִ�и��²��� */
			String result = cityDAO.UpdateCity(city);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
