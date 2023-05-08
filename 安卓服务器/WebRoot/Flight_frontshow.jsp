<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Flight" %>
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
    Flight flight = (Flight)request.getAttribute("flight");

%>
<HTML><HEAD><TITLE>查看航班</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>记录id:</td>
    <td width=70%><%=flight.getFlightId() %></td>
  </tr>

  <tr>
    <td width=30%>航班号:</td>
    <td width=70%><%=flight.getFlightNo() %></td>
  </tr>

  <tr>
    <td width=30%>航空公司:</td>
    <td width=70%>
      <%=flight.getComparyObj().getCompanyName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>出发城市:</td>
    <td width=70%>
      <%=flight.getStartCity().getCityName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>到达城市:</td>
    <td width=70%>
      <%=flight.getStartCity().getCityName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>航班日期:</td>
        <% java.text.DateFormat flightDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=flightDateSDF.format(flight.getFlightDate()) %></td>
  </tr>

  <tr>
    <td width=30%>起飞时间:</td>
    <td width=70%><%=flight.getFlyTime() %></td>
  </tr>

  <tr>
    <td width=30%>候机楼:</td>
    <td width=70%><%=flight.getWaitFloor() %></td>
  </tr>

  <tr>
    <td width=30%>接机楼:</td>
    <td width=70%><%=flight.getMeetFloor() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
