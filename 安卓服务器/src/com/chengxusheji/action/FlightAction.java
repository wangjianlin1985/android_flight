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
import com.chengxusheji.dao.FlightDAO;
import com.chengxusheji.domain.Flight;
import com.chengxusheji.dao.CompanyDAO;
import com.chengxusheji.domain.Company;
import com.chengxusheji.dao.CityDAO;
import com.chengxusheji.domain.City;
import com.chengxusheji.dao.CityDAO;
import com.chengxusheji.domain.City;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class FlightAction extends BaseAction {

    /*界面层需要查询的属性: 航班号*/
    private String flightNo;
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }
    public String getFlightNo() {
        return this.flightNo;
    }

    /*界面层需要查询的属性: 航空公司*/
    private Company comparyObj;
    public void setComparyObj(Company comparyObj) {
        this.comparyObj = comparyObj;
    }
    public Company getComparyObj() {
        return this.comparyObj;
    }

    /*界面层需要查询的属性: 出发城市*/
    private City startCity;
    public void setStartCity(City startCity) {
        this.startCity = startCity;
    }
    public City getStartCity() {
        return this.startCity;
    }

    /*界面层需要查询的属性: 到达城市*/
    private City arriveCity;
    public void setArriveCity(City arriveCity) {
        this.arriveCity = arriveCity;
    }
    public City getArriveCity() {
        return this.arriveCity;
    }

    /*界面层需要查询的属性: 航班日期*/
    private String flightDate;
    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }
    public String getFlightDate() {
        return this.flightDate;
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

    private int flightId;
    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }
    public int getFlightId() {
        return flightId;
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
    @Resource FlightDAO flightDAO;

    /*待操作的Flight对象*/
    private Flight flight;
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    public Flight getFlight() {
        return this.flight;
    }

    /*跳转到添加Flight视图*/
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

    /*添加Flight信息*/
    @SuppressWarnings("deprecation")
    public String AddFlight() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Company comparyObj = companyDAO.GetCompanyByCompanyId(flight.getComparyObj().getCompanyId());
            flight.setComparyObj(comparyObj);
            City startCity = cityDAO.GetCityByCityNo(flight.getStartCity().getCityNo());
            flight.setStartCity(startCity);
            City arriveCity = cityDAO.GetCityByCityNo(flight.getArriveCity().getCityNo());
            flight.setArriveCity(arriveCity);
            flightDAO.AddFlight(flight);
            ctx.put("message",  java.net.URLEncoder.encode("Flight添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Flight添加失败!"));
            return "error";
        }
    }

    /*查询Flight信息*/
    public String QueryFlight() {
        if(currentPage == 0) currentPage = 1;
        if(flightNo == null) flightNo = "";
        if(flightDate == null) flightDate = "";
        List<Flight> flightList = flightDAO.QueryFlightInfo(flightNo, comparyObj, startCity, arriveCity, flightDate, currentPage);
        /*计算总的页数和总的记录数*/
        flightDAO.CalculateTotalPageAndRecordNumber(flightNo, comparyObj, startCity, arriveCity, flightDate);
        /*获取到总的页码数目*/
        totalPage = flightDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = flightDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("flightList",  flightList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("flightNo", flightNo);
        ctx.put("comparyObj", comparyObj);
        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        ctx.put("startCity", startCity);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        ctx.put("arriveCity", arriveCity);
        ctx.put("flightDate", flightDate);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryFlightOutputToExcel() { 
        if(flightNo == null) flightNo = "";
        if(flightDate == null) flightDate = "";
        List<Flight> flightList = flightDAO.QueryFlightInfo(flightNo,comparyObj,startCity,arriveCity,flightDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Flight信息记录"; 
        String[] headers = { "记录id","航班号","航空公司","出发城市","到达城市","航班日期","起飞时间","候机楼","接机楼"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<flightList.size();i++) {
        	Flight flight = flightList.get(i); 
        	dataset.add(new String[]{flight.getFlightId() + "",flight.getFlightNo(),flight.getComparyObj().getCompanyName(),
flight.getStartCity().getCityName(),
flight.getArriveCity().getCityName(),
new SimpleDateFormat("yyyy-MM-dd").format(flight.getFlightDate()),flight.getFlyTime(),flight.getWaitFloor(),flight.getMeetFloor()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Flight.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Flight信息*/
    public String FrontQueryFlight() {
        if(currentPage == 0) currentPage = 1;
        if(flightNo == null) flightNo = "";
        if(flightDate == null) flightDate = "";
        List<Flight> flightList = flightDAO.QueryFlightInfo(flightNo, comparyObj, startCity, arriveCity, flightDate, currentPage);
        /*计算总的页数和总的记录数*/
        flightDAO.CalculateTotalPageAndRecordNumber(flightNo, comparyObj, startCity, arriveCity, flightDate);
        /*获取到总的页码数目*/
        totalPage = flightDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = flightDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("flightList",  flightList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("flightNo", flightNo);
        ctx.put("comparyObj", comparyObj);
        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        ctx.put("startCity", startCity);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        ctx.put("arriveCity", arriveCity);
        ctx.put("flightDate", flightDate);
        return "front_query_view";
    }

    /*查询要修改的Flight信息*/
    public String ModifyFlightQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键flightId获取Flight对象*/
        Flight flight = flightDAO.GetFlightByFlightId(flightId);

        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        ctx.put("flight",  flight);
        return "modify_view";
    }

    /*查询要修改的Flight信息*/
    public String FrontShowFlightQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键flightId获取Flight对象*/
        Flight flight = flightDAO.GetFlightByFlightId(flightId);

        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        ctx.put("flight",  flight);
        return "front_show_view";
    }

    /*更新修改Flight信息*/
    public String ModifyFlight() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Company comparyObj = companyDAO.GetCompanyByCompanyId(flight.getComparyObj().getCompanyId());
            flight.setComparyObj(comparyObj);
            City startCity = cityDAO.GetCityByCityNo(flight.getStartCity().getCityNo());
            flight.setStartCity(startCity);
            City arriveCity = cityDAO.GetCityByCityNo(flight.getArriveCity().getCityNo());
            flight.setArriveCity(arriveCity);
            flightDAO.UpdateFlight(flight);
            ctx.put("message",  java.net.URLEncoder.encode("Flight信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Flight信息更新失败!"));
            return "error";
       }
   }

    /*删除Flight信息*/
    public String DeleteFlight() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            flightDAO.DeleteFlight(flightId);
            ctx.put("message",  java.net.URLEncoder.encode("Flight删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Flight删除失败!"));
            return "error";
        }
    }

}
