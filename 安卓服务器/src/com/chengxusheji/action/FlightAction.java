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

    /*�������Ҫ��ѯ������: �����*/
    private String flightNo;
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }
    public String getFlightNo() {
        return this.flightNo;
    }

    /*�������Ҫ��ѯ������: ���չ�˾*/
    private Company comparyObj;
    public void setComparyObj(Company comparyObj) {
        this.comparyObj = comparyObj;
    }
    public Company getComparyObj() {
        return this.comparyObj;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private City startCity;
    public void setStartCity(City startCity) {
        this.startCity = startCity;
    }
    public City getStartCity() {
        return this.startCity;
    }

    /*�������Ҫ��ѯ������: �������*/
    private City arriveCity;
    public void setArriveCity(City arriveCity) {
        this.arriveCity = arriveCity;
    }
    public City getArriveCity() {
        return this.arriveCity;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String flightDate;
    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }
    public String getFlightDate() {
        return this.flightDate;
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

    private int flightId;
    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }
    public int getFlightId() {
        return flightId;
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
    @Resource FlightDAO flightDAO;

    /*��������Flight����*/
    private Flight flight;
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    public Flight getFlight() {
        return this.flight;
    }

    /*��ת�����Flight��ͼ*/
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

    /*���Flight��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("Flight��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Flight���ʧ��!"));
            return "error";
        }
    }

    /*��ѯFlight��Ϣ*/
    public String QueryFlight() {
        if(currentPage == 0) currentPage = 1;
        if(flightNo == null) flightNo = "";
        if(flightDate == null) flightDate = "";
        List<Flight> flightList = flightDAO.QueryFlightInfo(flightNo, comparyObj, startCity, arriveCity, flightDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        flightDAO.CalculateTotalPageAndRecordNumber(flightNo, comparyObj, startCity, arriveCity, flightDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = flightDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryFlightOutputToExcel() { 
        if(flightNo == null) flightNo = "";
        if(flightDate == null) flightDate = "";
        List<Flight> flightList = flightDAO.QueryFlightInfo(flightNo,comparyObj,startCity,arriveCity,flightDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Flight��Ϣ��¼"; 
        String[] headers = { "��¼id","�����","���չ�˾","��������","�������","��������","���ʱ��","���¥","�ӻ�¥"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Flight.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯFlight��Ϣ*/
    public String FrontQueryFlight() {
        if(currentPage == 0) currentPage = 1;
        if(flightNo == null) flightNo = "";
        if(flightDate == null) flightDate = "";
        List<Flight> flightList = flightDAO.QueryFlightInfo(flightNo, comparyObj, startCity, arriveCity, flightDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        flightDAO.CalculateTotalPageAndRecordNumber(flightNo, comparyObj, startCity, arriveCity, flightDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = flightDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Flight��Ϣ*/
    public String ModifyFlightQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������flightId��ȡFlight����*/
        Flight flight = flightDAO.GetFlightByFlightId(flightId);

        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        ctx.put("flight",  flight);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Flight��Ϣ*/
    public String FrontShowFlightQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������flightId��ȡFlight����*/
        Flight flight = flightDAO.GetFlightByFlightId(flightId);

        List<Company> companyList = companyDAO.QueryAllCompanyInfo();
        ctx.put("companyList", companyList);
        List<City> cityList = cityDAO.QueryAllCityInfo();
        ctx.put("cityList", cityList);
        ctx.put("flight",  flight);
        return "front_show_view";
    }

    /*�����޸�Flight��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("Flight��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Flight��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Flight��Ϣ*/
    public String DeleteFlight() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            flightDAO.DeleteFlight(flightId);
            ctx.put("message",  java.net.URLEncoder.encode("Flightɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Flightɾ��ʧ��!"));
            return "error";
        }
    }

}
