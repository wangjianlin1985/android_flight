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
import com.chengxusheji.dao.DotDAO;
import com.chengxusheji.domain.Dot;
import com.chengxusheji.dao.CompanyDAO;
import com.chengxusheji.domain.Company;
import com.chengxusheji.dao.CityDAO;
import com.chengxusheji.domain.City;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class DotAction extends BaseAction {

    /*�������Ҫ��ѯ������: ���չ�˾*/
    private Company companyObj;
    public void setCompanyObj(Company companyObj) {
        this.companyObj = companyObj;
    }
    public Company getCompanyObj() {
        return this.companyObj;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*�������Ҫ��ѯ������: ����*/
    private City cityObj;
    public void setCityObj(City cityObj) {
        this.cityObj = cityObj;
    }
    public City getCityObj() {
        return this.cityObj;
    }

    /*�������Ҫ��ѯ������: �绰*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

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

    private int dotId;
    public void setDotId(int dotId) {
        this.dotId = dotId;
    }
    public int getDotId() {
        return dotId;
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
    @Resource CompanyDAO companyDAO;
    @Resource CityDAO cityDAO;
    @Resource DotDAO dotDAO;

    /*��������Dot����*/
    private Dot dot;
    public void setDot(Dot dot) {
        this.dot = dot;
    }
    public Dot getDot() {
        return this.dot;
    }

    /*��ת�����Dot��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Company��Ϣ*/
        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        /*��ѯ���е�City��Ϣ*/
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        return "add_view";
    }

    /*���Dot��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddDot() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Company companyObj = companyDAO.GetCompanyByCompanyId(dot.getCompanyObj().getCompanyId());
            dot.setCompanyObj(companyObj);
            City cityObj = cityDAO.GetCityByCityNo(dot.getCityObj().getCityNo());
            dot.setCityObj(cityObj);
            dotDAO.AddDot(dot);
            ctx.put("message",  java.net.URLEncoder.encode("Dot��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Dot���ʧ��!"));
            return "error";
        }
    }

    /*��ѯDot��Ϣ*/
    public String QueryDot() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(telephone == null) telephone = "";
        List<Dot> dotList = dotDAO.QueryDotInfo(companyObj, title, cityObj, telephone, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        dotDAO.CalculateTotalPageAndRecordNumber(companyObj, title, cityObj, telephone);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = dotDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = dotDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("dotList",  dotList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("companyObj", companyObj);
        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        ctx.put("title", title);
        ctx.put("cityObj", cityObj);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        ctx.put("telephone", telephone);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryDotOutputToExcel() { 
        if(title == null) title = "";
        if(telephone == null) telephone = "";
        List<Dot> dotList = dotDAO.QueryDotInfo(companyObj,title,cityObj,telephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Dot��Ϣ��¼"; 
        String[] headers = { "����id","���չ�˾","��������","����","�绰","����"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<dotList.size();i++) {
        	Dot dot = dotList.get(i); 
        	dataset.add(new String[]{dot.getDotId() + "",dot.getCompanyObj().getCompanyName(),
dot.getTitle(),dot.getCityObj().getCityName(),
dot.getTelephone(),dot.getFax()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Dot.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯDot��Ϣ*/
    public String FrontQueryDot() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(telephone == null) telephone = "";
        List<Dot> dotList = dotDAO.QueryDotInfo(companyObj, title, cityObj, telephone, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        dotDAO.CalculateTotalPageAndRecordNumber(companyObj, title, cityObj, telephone);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = dotDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = dotDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("dotList",  dotList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("companyObj", companyObj);
        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        ctx.put("title", title);
        ctx.put("cityObj", cityObj);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        ctx.put("telephone", telephone);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Dot��Ϣ*/
    public String ModifyDotQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������dotId��ȡDot����*/
        Dot dot = dotDAO.GetDotByDotId(dotId);

        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        ctx.put("dot",  dot);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Dot��Ϣ*/
    public String FrontShowDotQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������dotId��ȡDot����*/
        Dot dot = dotDAO.GetDotByDotId(dotId);

        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        ctx.put("dot",  dot);
        return "front_show_view";
    }

    /*�����޸�Dot��Ϣ*/
    public String ModifyDot() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Company companyObj = companyDAO.GetCompanyByCompanyId(dot.getCompanyObj().getCompanyId());
            dot.setCompanyObj(companyObj);
            City cityObj = cityDAO.GetCityByCityNo(dot.getCityObj().getCityNo());
            dot.setCityObj(cityObj);
            dotDAO.UpdateDot(dot);
            ctx.put("message",  java.net.URLEncoder.encode("Dot��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Dot��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Dot��Ϣ*/
    public String DeleteDot() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            dotDAO.DeleteDot(dotId);
            ctx.put("message",  java.net.URLEncoder.encode("Dotɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Dotɾ��ʧ��!"));
            return "error";
        }
    }

}
