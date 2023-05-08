package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.CityDAO;
import com.chengxusheji.domain.City;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class CityAction extends BaseAction {

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private String cityNo;
    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }
    public String getCityNo() {
        return cityNo;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource CityDAO cityDAO;

    /*��������City����*/
    private City city;
    public void setCity(City city) {
        this.city = city;
    }
    public City getCity() {
        return this.city;
    }

    /*��ת�����City��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���City��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddCity() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤���б���Ƿ��Ѿ�����*/
        String cityNo = city.getCityNo();
        City db_city = cityDAO.GetCityByCityNo(cityNo);
        if(null != db_city) {
            ctx.put("error",  java.net.URLEncoder.encode("�ó��б���Ѿ�����!"));
            return "error";
        }
        try {
            cityDAO.AddCity(city);
            ctx.put("message",  java.net.URLEncoder.encode("City��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("City���ʧ��!"));
            return "error";
        }
    }

    /*��ѯCity��Ϣ*/
    public String QueryCity() {
        if(currentPage == 0) currentPage = 1;
        List<City> cityList = cityDAO.QueryCityInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        cityDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = cityDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = cityDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("cityList",  cityList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryCityOutputToExcel() { 
        List<City> cityList = cityDAO.QueryCityInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "City��Ϣ��¼"; 
        String[] headers = { "���б��","��������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<cityList.size();i++) {
        	City city = cityList.get(i); 
        	dataset.add(new String[]{city.getCityNo(),city.getCityName()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"City.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯCity��Ϣ*/
    public String FrontQueryCity() {
        if(currentPage == 0) currentPage = 1;
        List<City> cityList = cityDAO.QueryCityInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        cityDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = cityDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = cityDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("cityList",  cityList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�City��Ϣ*/
    public String ModifyCityQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������cityNo��ȡCity����*/
        City city = cityDAO.GetCityByCityNo(cityNo);

        ctx.put("city",  city);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�City��Ϣ*/
    public String FrontShowCityQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������cityNo��ȡCity����*/
        City city = cityDAO.GetCityByCityNo(cityNo);

        ctx.put("city",  city);
        return "front_show_view";
    }

    /*�����޸�City��Ϣ*/
    public String ModifyCity() {
        ActionContext ctx = ActionContext.getContext();
        try {
            cityDAO.UpdateCity(city);
            ctx.put("message",  java.net.URLEncoder.encode("City��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("City��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��City��Ϣ*/
    public String DeleteCity() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            cityDAO.DeleteCity(cityNo);
            ctx.put("message",  java.net.URLEncoder.encode("Cityɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Cityɾ��ʧ��!"));
            return "error";
        }
    }

}
