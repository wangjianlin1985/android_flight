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

    /*界面层需要查询的属性: 航空公司*/
    private Company companyObj;
    public void setCompanyObj(Company companyObj) {
        this.companyObj = companyObj;
    }
    public Company getCompanyObj() {
        return this.companyObj;
    }

    /*界面层需要查询的属性: 网点名称*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*界面层需要查询的属性: 城市*/
    private City cityObj;
    public void setCityObj(City cityObj) {
        this.cityObj = cityObj;
    }
    public City getCityObj() {
        return this.cityObj;
    }

    /*界面层需要查询的属性: 电话*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource CompanyDAO companyDAO;
    @Resource CityDAO cityDAO;
    @Resource DotDAO dotDAO;

    /*待操作的Dot对象*/
    private Dot dot;
    public void setDot(Dot dot) {
        this.dot = dot;
    }
    public Dot getDot() {
        return this.dot;
    }

    /*跳转到添加Dot视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Company信息*/
        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        /*查询所有的City信息*/
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        return "add_view";
    }

    /*添加Dot信息*/
    @SuppressWarnings("deprecation")
    public String AddDot() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Company companyObj = companyDAO.GetCompanyByCompanyId(dot.getCompanyObj().getCompanyId());
            dot.setCompanyObj(companyObj);
            City cityObj = cityDAO.GetCityByCityNo(dot.getCityObj().getCityNo());
            dot.setCityObj(cityObj);
            dotDAO.AddDot(dot);
            ctx.put("message",  java.net.URLEncoder.encode("Dot添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Dot添加失败!"));
            return "error";
        }
    }

    /*查询Dot信息*/
    public String QueryDot() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(telephone == null) telephone = "";
        List<Dot> dotList = dotDAO.QueryDotInfo(companyObj, title, cityObj, telephone, currentPage);
        /*计算总的页数和总的记录数*/
        dotDAO.CalculateTotalPageAndRecordNumber(companyObj, title, cityObj, telephone);
        /*获取到总的页码数目*/
        totalPage = dotDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryDotOutputToExcel() { 
        if(title == null) title = "";
        if(telephone == null) telephone = "";
        List<Dot> dotList = dotDAO.QueryDotInfo(companyObj,title,cityObj,telephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Dot信息记录"; 
        String[] headers = { "网点id","航空公司","网点名称","城市","电话","传真"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Dot.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
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
    /*前台查询Dot信息*/
    public String FrontQueryDot() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(telephone == null) telephone = "";
        List<Dot> dotList = dotDAO.QueryDotInfo(companyObj, title, cityObj, telephone, currentPage);
        /*计算总的页数和总的记录数*/
        dotDAO.CalculateTotalPageAndRecordNumber(companyObj, title, cityObj, telephone);
        /*获取到总的页码数目*/
        totalPage = dotDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Dot信息*/
    public String ModifyDotQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键dotId获取Dot对象*/
        Dot dot = dotDAO.GetDotByDotId(dotId);

        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        ctx.put("dot",  dot);
        return "modify_view";
    }

    /*查询要修改的Dot信息*/
    public String FrontShowDotQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键dotId获取Dot对象*/
        Dot dot = dotDAO.GetDotByDotId(dotId);

        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        ctx.put("dot",  dot);
        return "front_show_view";
    }

    /*更新修改Dot信息*/
    public String ModifyDot() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Company companyObj = companyDAO.GetCompanyByCompanyId(dot.getCompanyObj().getCompanyId());
            dot.setCompanyObj(companyObj);
            City cityObj = cityDAO.GetCityByCityNo(dot.getCityObj().getCityNo());
            dot.setCityObj(cityObj);
            dotDAO.UpdateDot(dot);
            ctx.put("message",  java.net.URLEncoder.encode("Dot信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Dot信息更新失败!"));
            return "error";
       }
   }

    /*删除Dot信息*/
    public String DeleteDot() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            dotDAO.DeleteDot(dotId);
            ctx.put("message",  java.net.URLEncoder.encode("Dot删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Dot删除失败!"));
            return "error";
        }
    }

}
