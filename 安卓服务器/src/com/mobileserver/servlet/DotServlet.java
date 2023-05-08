package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.DotDAO;
import com.mobileserver.domain.Dot;

import org.json.JSONStringer;

public class DotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*��������ҵ������*/
	private DotDAO dotDAO = new DotDAO();

	/*Ĭ�Ϲ��캯��*/
	public DotServlet() {
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
			int companyObj = 0;
			if (request.getParameter("companyObj") != null)
				companyObj = Integer.parseInt(request.getParameter("companyObj"));
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			String cityObj = "";
			if (request.getParameter("cityObj") != null)
				cityObj = request.getParameter("cityObj");
			String telephone = request.getParameter("telephone");
			telephone = telephone == null ? "" : new String(request.getParameter(
					"telephone").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�������ѯ*/
			List<Dot> dotList = dotDAO.QueryDot(companyObj,title,cityObj,telephone);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Dots>").append("\r\n");
			for (int i = 0; i < dotList.size(); i++) {
				sb.append("	<Dot>").append("\r\n")
				.append("		<dotId>")
				.append(dotList.get(i).getDotId())
				.append("</dotId>").append("\r\n")
				.append("		<companyObj>")
				.append(dotList.get(i).getCompanyObj())
				.append("</companyObj>").append("\r\n")
				.append("		<title>")
				.append(dotList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<cityObj>")
				.append(dotList.get(i).getCityObj())
				.append("</cityObj>").append("\r\n")
				.append("		<telephone>")
				.append(dotList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<fax>")
				.append(dotList.get(i).getFax())
				.append("</fax>").append("\r\n")
				.append("		<address>")
				.append(dotList.get(i).getAddress())
				.append("</address>").append("\r\n")
				.append("	</Dot>").append("\r\n");
			}
			sb.append("</Dots>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Dot dot: dotList) {
				  stringer.object();
			  stringer.key("dotId").value(dot.getDotId());
			  stringer.key("companyObj").value(dot.getCompanyObj());
			  stringer.key("title").value(dot.getTitle());
			  stringer.key("cityObj").value(dot.getCityObj());
			  stringer.key("telephone").value(dot.getTelephone());
			  stringer.key("fax").value(dot.getFax());
			  stringer.key("address").value(dot.getAddress());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ������㣺��ȡ����������������浽�½���������� */ 
			Dot dot = new Dot();
			int dotId = Integer.parseInt(request.getParameter("dotId"));
			dot.setDotId(dotId);
			int companyObj = Integer.parseInt(request.getParameter("companyObj"));
			dot.setCompanyObj(companyObj);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			dot.setTitle(title);
			String cityObj = new String(request.getParameter("cityObj").getBytes("iso-8859-1"), "UTF-8");
			dot.setCityObj(cityObj);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			dot.setTelephone(telephone);
			String fax = new String(request.getParameter("fax").getBytes("iso-8859-1"), "UTF-8");
			dot.setFax(fax);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			dot.setAddress(address);

			/* ����ҵ���ִ����Ӳ��� */
			String result = dotDAO.AddDot(dot);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����㣺��ȡ���������id*/
			int dotId = Integer.parseInt(request.getParameter("dotId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = dotDAO.DeleteDot(dotId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*��������֮ǰ�ȸ���dotId��ѯĳ������*/
			int dotId = Integer.parseInt(request.getParameter("dotId"));
			Dot dot = dotDAO.GetDot(dotId);

			// �ͻ��˲�ѯ��������󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("dotId").value(dot.getDotId());
			  stringer.key("companyObj").value(dot.getCompanyObj());
			  stringer.key("title").value(dot.getTitle());
			  stringer.key("cityObj").value(dot.getCityObj());
			  stringer.key("telephone").value(dot.getTelephone());
			  stringer.key("fax").value(dot.getFax());
			  stringer.key("address").value(dot.getAddress());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �������㣺��ȡ����������������浽�½���������� */ 
			Dot dot = new Dot();
			int dotId = Integer.parseInt(request.getParameter("dotId"));
			dot.setDotId(dotId);
			int companyObj = Integer.parseInt(request.getParameter("companyObj"));
			dot.setCompanyObj(companyObj);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			dot.setTitle(title);
			String cityObj = new String(request.getParameter("cityObj").getBytes("iso-8859-1"), "UTF-8");
			dot.setCityObj(cityObj);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			dot.setTelephone(telephone);
			String fax = new String(request.getParameter("fax").getBytes("iso-8859-1"), "UTF-8");
			dot.setFax(fax);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			dot.setAddress(address);

			/* ����ҵ���ִ�и��²��� */
			String result = dotDAO.UpdateDot(dot);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
