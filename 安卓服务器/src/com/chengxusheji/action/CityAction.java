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

    private String cityNo;
    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }
    public String getCityNo() {
        return cityNo;
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
    @Resource CityDAO cityDAO;

    /*待操作的City对象*/
    private City city;
    public void setCity(City city) {
        this.city = city;
    }
    public City getCity() {
        return this.city;
    }

    /*跳转到添加City视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加City信息*/
    @SuppressWarnings("deprecation")
    public String AddCity() {
        ActionContext ctx = ActionContext.getContext();
        /*验证城市编号是否已经存在*/
        String cityNo = city.getCityNo();
        City db_city = cityDAO.GetCityByCityNo(cityNo);
        if(null != db_city) {
            ctx.put("error",  java.net.URLEncoder.encode("该城市编号已经存在!"));
            return "error";
        }
        try {
            cityDAO.AddCity(city);
            ctx.put("message",  java.net.URLEncoder.encode("City添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("City添加失败!"));
            return "error";
        }
    }

    /*查询City信息*/
    public String QueryCity() {
        if(currentPage == 0) currentPage = 1;
        List<City> cityList = cityDAO.QueryCityInfo(currentPage);
        /*计算总的页数和总的记录数*/
        cityDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = cityDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = cityDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("cityList",  cityList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryCityOutputToExcel() { 
        List<City> cityList = cityDAO.QueryCityInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "City信息记录"; 
        String[] headers = { "城市编号","城市名称"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"City.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询City信息*/
    public String FrontQueryCity() {
        if(currentPage == 0) currentPage = 1;
        List<City> cityList = cityDAO.QueryCityInfo(currentPage);
        /*计算总的页数和总的记录数*/
        cityDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = cityDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = cityDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("cityList",  cityList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的City信息*/
    public String ModifyCityQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键cityNo获取City对象*/
        City city = cityDAO.GetCityByCityNo(cityNo);

        ctx.put("city",  city);
        return "modify_view";
    }

    /*查询要修改的City信息*/
    public String FrontShowCityQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键cityNo获取City对象*/
        City city = cityDAO.GetCityByCityNo(cityNo);

        ctx.put("city",  city);
        return "front_show_view";
    }

    /*更新修改City信息*/
    public String ModifyCity() {
        ActionContext ctx = ActionContext.getContext();
        try {
            cityDAO.UpdateCity(city);
            ctx.put("message",  java.net.URLEncoder.encode("City信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("City信息更新失败!"));
            return "error";
       }
   }

    /*删除City信息*/
    public String DeleteCity() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            cityDAO.DeleteCity(cityNo);
            ctx.put("message",  java.net.URLEncoder.encode("City删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("City删除失败!"));
            return "error";
        }
    }

}
