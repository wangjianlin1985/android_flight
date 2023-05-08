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
import com.chengxusheji.domain.Dot;

@Service @Transactional
public class DotDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddDot(Dot dot) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(dot);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Dot> QueryDotInfo(Company companyObj,String title,City cityObj,String telephone,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Dot dot where 1=1";
    	if(null != companyObj && companyObj.getCompanyId()!=0) hql += " and dot.companyObj.companyId=" + companyObj.getCompanyId();
    	if(!title.equals("")) hql = hql + " and dot.title like '%" + title + "%'";
    	if(null != cityObj && !cityObj.getCityNo().equals("")) hql += " and dot.cityObj.cityNo='" + cityObj.getCityNo() + "'";
    	if(!telephone.equals("")) hql = hql + " and dot.telephone like '%" + telephone + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List dotList = q.list();
    	return (ArrayList<Dot>) dotList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Dot> QueryDotInfo(Company companyObj,String title,City cityObj,String telephone) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Dot dot where 1=1";
    	if(null != companyObj && companyObj.getCompanyId()!=0) hql += " and dot.companyObj.companyId=" + companyObj.getCompanyId();
    	if(!title.equals("")) hql = hql + " and dot.title like '%" + title + "%'";
    	if(null != cityObj && !cityObj.getCityNo().equals("")) hql += " and dot.cityObj.cityNo='" + cityObj.getCityNo() + "'";
    	if(!telephone.equals("")) hql = hql + " and dot.telephone like '%" + telephone + "%'";
    	Query q = s.createQuery(hql);
    	List dotList = q.list();
    	return (ArrayList<Dot>) dotList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Dot> QueryAllDotInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Dot";
        Query q = s.createQuery(hql);
        List dotList = q.list();
        return (ArrayList<Dot>) dotList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Company companyObj,String title,City cityObj,String telephone) {
        Session s = factory.getCurrentSession();
        String hql = "From Dot dot where 1=1";
        if(null != companyObj && companyObj.getCompanyId()!=0) hql += " and dot.companyObj.companyId=" + companyObj.getCompanyId();
        if(!title.equals("")) hql = hql + " and dot.title like '%" + title + "%'";
        if(null != cityObj && !cityObj.getCityNo().equals("")) hql += " and dot.cityObj.cityNo='" + cityObj.getCityNo() + "'";
        if(!telephone.equals("")) hql = hql + " and dot.telephone like '%" + telephone + "%'";
        Query q = s.createQuery(hql);
        List dotList = q.list();
        recordNumber = dotList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Dot GetDotByDotId(int dotId) {
        Session s = factory.getCurrentSession();
        Dot dot = (Dot)s.get(Dot.class, dotId);
        return dot;
    }

    /*����Dot��Ϣ*/
    public void UpdateDot(Dot dot) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(dot);
    }

    /*ɾ��Dot��Ϣ*/
    public void DeleteDot (int dotId) throws Exception {
        Session s = factory.getCurrentSession();
        Object dot = s.load(Dot.class, dotId);
        s.delete(dot);
    }

}
