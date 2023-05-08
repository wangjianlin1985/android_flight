<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Dot" %>
<%@ page import="com.chengxusheji.domain.Company" %>
<%@ page import="com.chengxusheji.domain.City" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Company信息
    List<Company> companyList = (List<Company>)request.getAttribute("companyList");
    //获取所有的City信息
    List<City> cityList = (List<City>)request.getAttribute("cityList");
    Dot dot = (Dot)request.getAttribute("dot");

%>
<HTML><HEAD><TITLE>查看网点</TITLE>
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
    <td width=30%>网点id:</td>
    <td width=70%><%=dot.getDotId() %></td>
  </tr>

  <tr>
    <td width=30%>航空公司:</td>
    <td width=70%>
      <%=dot.getCompanyObj().getCompanyName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>网点名称:</td>
    <td width=70%><%=dot.getTitle() %></td>
  </tr>

  <tr>
    <td width=30%>城市:</td>
    <td width=70%>
      <%=dot.getCityObj().getCityName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>电话:</td>
    <td width=70%><%=dot.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>传真:</td>
    <td width=70%><%=dot.getFax() %></td>
  </tr>

  <tr>
    <td width=30%>地址:</td>
    <td width=70%><%=dot.getAddress() %></td>
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
