package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.Company;
import com.chengxusheji.domain.City;
import com.chengxusheji.domain.City;
import com.chengxusheji.domain.Flight;

@Service @Transactional
public class FlightDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddFlight(Flight flight) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(flight);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Flight> QueryFlightInfo(String flightNo,Company comparyObj,City startCity,City arriveCity,String flightDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Flight flight where 1=1";
    	if(!flightNo.equals("")) hql = hql + " and flight.flightNo like '%" + flightNo + "%'";
    	if(null != comparyObj && comparyObj.getCompanyId()!=0) hql += " and flight.comparyObj.companyId=" + comparyObj.getCompanyId();
    	if(null != startCity && !startCity.getCityNo().equals("")) hql += " and flight.startCity.cityNo='" + startCity.getCityNo() + "'";
    	if(null != arriveCity && !arriveCity.getCityNo().equals("")) hql += " and flight.arriveCity.cityNo='" + arriveCity.getCityNo() + "'";
    	if(!flightDate.equals("")) hql = hql + " and flight.flightDate like '%" + flightDate + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List flightList = q.list();
    	return (ArrayList<Flight>) flightList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Flight> QueryFlightInfo(String flightNo,Company comparyObj,City startCity,City arriveCity,String flightDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Flight flight where 1=1";
    	if(!flightNo.equals("")) hql = hql + " and flight.flightNo like '%" + flightNo + "%'";
    	if(null != comparyObj && comparyObj.getCompanyId()!=0) hql += " and flight.comparyObj.companyId=" + comparyObj.getCompanyId();
    	if(null != startCity && !startCity.getCityNo().equals("")) hql += " and flight.startCity.cityNo='" + startCity.getCityNo() + "'";
    	if(null != arriveCity && !arriveCity.getCityNo().equals("")) hql += " and flight.arriveCity.cityNo='" + arriveCity.getCityNo() + "'";
    	if(!flightDate.equals("")) hql = hql + " and flight.flightDate like '%" + flightDate + "%'";
    	Query q = s.createQuery(hql);
    	List flightList = q.list();
    	return (ArrayList<Flight>) flightList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Flight> QueryAllFlightInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Flight";
        Query q = s.createQuery(hql);
        List flightList = q.list();
        return (ArrayList<Flight>) flightList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String flightNo,Company comparyObj,City startCity,City arriveCity,String flightDate) {
        Session s = factory.getCurrentSession();
        String hql = "From Flight flight where 1=1";
        if(!flightNo.equals("")) hql = hql + " and flight.flightNo like '%" + flightNo + "%'";
        if(null != comparyObj && comparyObj.getCompanyId()!=0) hql += " and flight.comparyObj.companyId=" + comparyObj.getCompanyId();
        if(null != startCity && !startCity.getCityNo().equals("")) hql += " and flight.startCity.cityNo='" + startCity.getCityNo() + "'";
        if(null != arriveCity && !arriveCity.getCityNo().equals("")) hql += " and flight.arriveCity.cityNo='" + arriveCity.getCityNo() + "'";
        if(!flightDate.equals("")) hql = hql + " and flight.flightDate like '%" + flightDate + "%'";
        Query q = s.createQuery(hql);
        List flightList = q.list();
        recordNumber = flightList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Flight GetFlightByFlightId(int flightId) {
        Session s = factory.getCurrentSession();
        Flight flight = (Flight)s.get(Flight.class, flightId);
        return flight;
    }

    /*更新Flight信息*/
    public void UpdateFlight(Flight flight) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(flight);
    }

    /*删除Flight信息*/
    public void DeleteFlight (int flightId) throws Exception {
        Session s = factory.getCurrentSession();
        Object flight = s.load(Flight.class, flightId);
        s.delete(flight);
    }

}
