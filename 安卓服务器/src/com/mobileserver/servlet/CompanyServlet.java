package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.CompanyDAO;
import com.mobileserver.domain.Company;

import org.json.JSONStringer;

public class CompanyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*���캽�չ�˾ҵ������*/
	private CompanyDAO companyDAO = new CompanyDAO();

	/*Ĭ�Ϲ��캯��*/
	public CompanyServlet() {
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
			/*��ȡ��ѯ���չ�˾�Ĳ�����Ϣ*/
			String companyName = request.getParameter("companyName");
			companyName = companyName == null ? "" : new String(request.getParameter(
					"companyName").getBytes("iso-8859-1"), "UTF-8");
			String personName = request.getParameter("personName");
			personName = personName == null ? "" : new String(request.getParameter(
					"personName").getBytes("iso-8859-1"), "UTF-8");
			String telephone = request.getParameter("telephone");
			telephone = telephone == null ? "" : new String(request.getParameter(
					"telephone").getBytes("iso-8859-1"), "UTF-8");
			Timestamp bornDate = null;
			if (request.getParameter("bornDate") != null)
				bornDate = Timestamp.valueOf(request.getParameter("bornDate"));

			/*����ҵ���߼���ִ�к��չ�˾��ѯ*/
			List<Company> companyList = companyDAO.QueryCompany(companyName,personName,telephone,bornDate);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Companys>").append("\r\n");
			for (int i = 0; i < companyList.size(); i++) {
				sb.append("	<Company>").append("\r\n")
				.append("		<companyId>")
				.append(companyList.get(i).getCompanyId())
				.append("</companyId>").append("\r\n")
				.append("		<companyName>")
				.append(companyList.get(i).getCompanyName())
				.append("</companyName>").append("\r\n")
				.append("		<personName>")
				.append(companyList.get(i).getPersonName())
				.append("</personName>").append("\r\n")
				.append("		<telephone>")
				.append(companyList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<bornDate>")
				.append(companyList.get(i).getBornDate())
				.append("</bornDate>").append("\r\n")
				.append("	</Company>").append("\r\n");
			}
			sb.append("</Companys>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Company company: companyList) {
				  stringer.object();
			  stringer.key("companyId").value(company.getCompanyId());
			  stringer.key("companyName").value(company.getCompanyName());
			  stringer.key("personName").value(company.getPersonName());
			  stringer.key("telephone").value(company.getTelephone());
			  stringer.key("bornDate").value(company.getBornDate());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��Ӻ��չ�˾����ȡ���չ�˾�������������浽�½��ĺ��չ�˾���� */ 
			Company company = new Company();
			int companyId = Integer.parseInt(request.getParameter("companyId"));
			company.setCompanyId(companyId);
			String companyName = new String(request.getParameter("companyName").getBytes("iso-8859-1"), "UTF-8");
			company.setCompanyName(companyName);
			String personName = new String(request.getParameter("personName").getBytes("iso-8859-1"), "UTF-8");
			company.setPersonName(personName);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			company.setTelephone(telephone);
			Timestamp bornDate = Timestamp.valueOf(request.getParameter("bornDate"));
			company.setBornDate(bornDate);

			/* ����ҵ���ִ����Ӳ��� */
			String result = companyDAO.AddCompany(company);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����չ�˾����ȡ���չ�˾�Ĺ�˾id*/
			int companyId = Integer.parseInt(request.getParameter("companyId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = companyDAO.DeleteCompany(companyId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���º��չ�˾֮ǰ�ȸ���companyId��ѯĳ�����չ�˾*/
			int companyId = Integer.parseInt(request.getParameter("companyId"));
			Company company = companyDAO.GetCompany(companyId);

			// �ͻ��˲�ѯ�ĺ��չ�˾���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("companyId").value(company.getCompanyId());
			  stringer.key("companyName").value(company.getCompanyName());
			  stringer.key("personName").value(company.getPersonName());
			  stringer.key("telephone").value(company.getTelephone());
			  stringer.key("bornDate").value(company.getBornDate());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���º��չ�˾����ȡ���չ�˾�������������浽�½��ĺ��չ�˾���� */ 
			Company company = new Company();
			int companyId = Integer.parseInt(request.getParameter("companyId"));
			company.setCompanyId(companyId);
			String companyName = new String(request.getParameter("companyName").getBytes("iso-8859-1"), "UTF-8");
			company.setCompanyName(companyName);
			String personName = new String(request.getParameter("personName").getBytes("iso-8859-1"), "UTF-8");
			company.setPersonName(personName);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			company.setTelephone(telephone);
			Timestamp bornDate = Timestamp.valueOf(request.getParameter("bornDate"));
			company.setBornDate(bornDate);

			/* ����ҵ���ִ�и��²��� */
			String result = companyDAO.UpdateCompany(company);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
