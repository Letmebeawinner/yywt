<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="java.io.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="DBstep.iDBManager2000.*" %>
<%!
  DBstep.iDBManager2000 ObjConnBean = new DBstep.iDBManager2000();
  String DocumentID;                            //文档编号
%>

<%

  DocumentID=request.getParameter("DocumentID"); //取得编号
  
%>
<html>
<head>
<title>金格电子签章系统iSignature HTML V6</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<link REL="stylesheet" href="Test.css" type="text/css">

</head>
<body bgcolor="#ffffff">


<table border=0  cellspacing='0' cellpadding='0' width=100% align=center class=TBStyle>
<tr>
  <td nowrap align=center class="TDTitleStyle" height="25">印章名称</td>
  <td nowrap align=center class="TDTitleStyle" height="25">印章单位</td>
  <td nowrap align=center class="TDTitleStyle" height="25">印章用户名</td>
  <td nowrap align=center class="TDTitleStyle" height="25">印章序列号</td>
  <td nowrap align=center class="TDTitleStyle" height="25">全球唯一标识</td>
  <td nowrap align=center class="TDTitleStyle" height="25">页面ID</td>
  <td nowrap align=center class="TDTitleStyle" height="25">签章序列号</td>
  <td nowrap align=center class="TDTitleStyle" height="25">签章机器IP</td>
  <td nowrap align=center class="TDTitleStyle" height="25">日志标志</td>
  <td nowrap align=center class="TDTitleStyle" height="25">KEY序列号</td>
</tr>
<%
  if (ObjConnBean.OpenConnection()) {
    ResultSet rs = null;
    Statement stmt = null;

	System.out.println(DocumentID);
	System.out.println("开始");

 try {
      String strSql = "select * from HTMLHistory Where DocumentID='" + DocumentID + "'"+"order by SignatureID desc";
      rs = ObjConnBean.ExecuteQuery(strSql);
	  System.out.println("错误");
	   while (rs.next()){
%>
<tr>
  <td class="TDStyle"><%=rs.getString("SignatureName")%>&nbsp;</td>
  <td class="TDStyle"><%=rs.getString("SignatureUnit")%>&nbsp;</td>
  <td class="TDStyle"><%=rs.getString("SignatureUser")%>&nbsp;</td>
  <td class="TDStyle"><%=rs.getString("SignatureSN")%>&nbsp;</td>
  <td class="TDStyle"><%=rs.getString("SignatureGUID")%>&nbsp;</td>
  <td class="TDStyle"><%=rs.getString("DocumentID")%>&nbsp;</td>
  <td class="TDStyle"><%=rs.getString("SignatureID")%>&nbsp;</td>
  <td class="TDStyle"><%=rs.getString("IP")%>&nbsp;</td>
  <td class="TDStyle"><%=rs.getString("LogType")%>&nbsp;</td>
  <td class="TDStyle"><%=rs.getString("KeySN")%>&nbsp;</td>
</tr>
<%
   }
  rs.close();
    }catch (SQLException e) {
      out.println(e.getMessage());
    }
    ObjConnBean.CloseConnection();
  }
%>
</table>
</body>
</html>
