<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Company" %>
<%@ page import="com.chengxusheji.domain.City" %>
<%@ page import="com.chengxusheji.domain.City" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Company信息
    List<Company> companyList = (List<Company>)request.getAttribute("companyList");
    //获取所有的City信息
    List<City> cityList = (List<City>)request.getAttribute("cityList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加航班</TITLE> 
<STYLE type=text/css>
BODY {
    	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var flightNo = document.getElementById("flight.flightNo").value;
    if(flightNo=="") {
        alert('请输入航班号!');
        return false;
    }
    var flyTime = document.getElementById("flight.flyTime").value;
    if(flyTime=="") {
        alert('请输入起飞时间!');
        return false;
    }
    var waitFloor = document.getElementById("flight.waitFloor").value;
    if(waitFloor=="") {
        alert('请输入候机楼!');
        return false;
    }
    var meetFloor = document.getElementById("flight.meetFloor").value;
    if(meetFloor=="") {
        alert('请输入接机楼!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>

<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top >
    <s:form action="Flight/Flight_AddFlight.action" method="post" id="flightAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>航班号:</td>
    <td width=70%><input id="flight.flightNo" name="flight.flightNo" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>航空公司:</td>
    <td width=70%>
      <select name="flight.comparyObj.companyId">
      <%
        for(Company company:companyList) {
      %>
          <option value='<%=company.getCompanyId() %>'><%=company.getCompanyName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>出发城市:</td>
    <td width=70%>
      <select name="flight.startCity.cityNo">
      <%
        for(City city:cityList) {
      %>
          <option value='<%=city.getCityNo() %>'><%=city.getCityName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>到达城市:</td>
    <td width=70%>
      <select name="flight.arriveCity.cityNo">
      <%
        for(City city:cityList) {
      %>
          <option value='<%=city.getCityNo() %>'><%=city.getCityName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>航班日期:</td>
    <td width=70%><input type="text" readonly id="flight.flightDate"  name="flight.flightDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>起飞时间:</td>
    <td width=70%><input id="flight.flyTime" name="flight.flyTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>候机楼:</td>
    <td width=70%><input id="flight.waitFloor" name="flight.waitFloor" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>接机楼:</td>
    <td width=70%><input id="flight.meetFloor" name="flight.meetFloor" type="text" size="20" /></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
