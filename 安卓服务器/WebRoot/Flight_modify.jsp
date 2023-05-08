<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Flight" %>
<%@ page import="com.chengxusheji.domain.Company" %>
<%@ page import="com.chengxusheji.domain.City" %>
<%@ page import="com.chengxusheji.domain.City" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Company信息
    List<Company> companyList = (List<Company>)request.getAttribute("companyList");
    //获取所有的City信息
    List<City> cityList = (List<City>)request.getAttribute("cityList");
    Flight flight = (Flight)request.getAttribute("flight");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改航班</TITLE>
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
    <TD align="left" vAlign=top ><s:form action="Flight/Flight_ModifyFlight.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>记录id:</td>
    <td width=70%><input id="flight.flightId" name="flight.flightId" type="text" value="<%=flight.getFlightId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>航班号:</td>
    <td width=70%><input id="flight.flightNo" name="flight.flightNo" type="text" size="20" value='<%=flight.getFlightNo() %>'/></td>
  </tr>

  <tr>
    <td width=30%>航空公司:</td>
    <td width=70%>
      <select name="flight.comparyObj.companyId">
      <%
        for(Company company:companyList) {
          String selected = "";
          if(company.getCompanyId() == flight.getComparyObj().getCompanyId())
            selected = "selected";
      %>
          <option value='<%=company.getCompanyId() %>' <%=selected %>><%=company.getCompanyName() %></option>
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
          String selected = "";
          if(city.getCityNo().equals(flight.getStartCity().getCityNo()))
            selected = "selected";
      %>
          <option value='<%=city.getCityNo() %>' <%=selected %>><%=city.getCityName() %></option>
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
          String selected = "";
          if(city.getCityNo().equals(flight.getArriveCity().getCityNo()))
            selected = "selected";
      %>
          <option value='<%=city.getCityNo() %>' <%=selected %>><%=city.getCityName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>航班日期:</td>
    <% DateFormat flightDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="flight.flightDate"  name="flight.flightDate" onclick="setDay(this);" value='<%=flightDateSDF.format(flight.getFlightDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>起飞时间:</td>
    <td width=70%><input id="flight.flyTime" name="flight.flyTime" type="text" size="20" value='<%=flight.getFlyTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>候机楼:</td>
    <td width=70%><input id="flight.waitFloor" name="flight.waitFloor" type="text" size="20" value='<%=flight.getWaitFloor() %>'/></td>
  </tr>

  <tr>
    <td width=30%>接机楼:</td>
    <td width=70%><input id="flight.meetFloor" name="flight.meetFloor" type="text" size="20" value='<%=flight.getMeetFloor() %>'/></td>
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
