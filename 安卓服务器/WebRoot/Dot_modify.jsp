<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Dot" %>
<%@ page import="com.chengxusheji.domain.Company" %>
<%@ page import="com.chengxusheji.domain.City" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Company��Ϣ
    List<Company> companyList = (List<Company>)request.getAttribute("companyList");
    //��ȡ���е�City��Ϣ
    List<City> cityList = (List<City>)request.getAttribute("cityList");
    Dot dot = (Dot)request.getAttribute("dot");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸�����</TITLE>
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
/*��֤��*/
function checkForm() {
    var title = document.getElementById("dot.title").value;
    if(title=="") {
        alert('��������������!');
        return false;
    }
    var telephone = document.getElementById("dot.telephone").value;
    if(telephone=="") {
        alert('������绰!');
        return false;
    }
    var address = document.getElementById("dot.address").value;
    if(address=="") {
        alert('�������ַ!');
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
    <TD align="left" vAlign=top ><s:form action="Dot/Dot_ModifyDot.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>����id:</td>
    <td width=70%><input id="dot.dotId" name="dot.dotId" type="text" value="<%=dot.getDotId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>���չ�˾:</td>
    <td width=70%>
      <select name="dot.companyObj.companyId">
      <%
        for(Company company:companyList) {
          String selected = "";
          if(company.getCompanyId() == dot.getCompanyObj().getCompanyId())
            selected = "selected";
      %>
          <option value='<%=company.getCompanyId() %>' <%=selected %>><%=company.getCompanyName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="dot.title" name="dot.title" type="text" size="60" value='<%=dot.getTitle() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%>
      <select name="dot.cityObj.cityNo">
      <%
        for(City city:cityList) {
          String selected = "";
          if(city.getCityNo().equals(dot.getCityObj().getCityNo()))
            selected = "selected";
      %>
          <option value='<%=city.getCityNo() %>' <%=selected %>><%=city.getCityName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�绰:</td>
    <td width=70%><input id="dot.telephone" name="dot.telephone" type="text" size="20" value='<%=dot.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="dot.fax" name="dot.fax" type="text" size="20" value='<%=dot.getFax() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ַ:</td>
    <td width=70%><input id="dot.address" name="dot.address" type="text" size="90" value='<%=dot.getAddress() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
