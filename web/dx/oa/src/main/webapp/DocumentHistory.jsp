<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="java.io.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="DBstep.iDBManager2000.*" %>
<%!
  DBstep.iDBManager2000 ObjConnBean = new DBstep.iDBManager2000();
  String DocumentID;                            //�ĵ����
%>

<%

  DocumentID=request.getParameter("DocumentID"); //ȡ�ñ��
  
%>
<html>
<head>
<title>������ǩ��ϵͳiSignature HTML V6</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<link REL="stylesheet" href="Test.css" type="text/css">

</head>
<body bgcolor="#ffffff">


<table border=0  cellspacing='0' cellpadding='0' width=100% align=center class=TBStyle>
<tr>
  <td nowrap align=center class="TDTitleStyle" height="25">ӡ������</td>
  <td nowrap align=center class="TDTitleStyle" height="25">ӡ�µ�λ</td>
  <td nowrap align=center class="TDTitleStyle" height="25">ӡ���û���</td>
  <td nowrap align=center class="TDTitleStyle" height="25">ӡ�����к�</td>
  <td nowrap align=center class="TDTitleStyle" height="25">ȫ��Ψһ��ʶ</td>
  <td nowrap align=center class="TDTitleStyle" height="25">ҳ��ID</td>
  <td nowrap align=center class="TDTitleStyle" height="25">ǩ�����к�</td>
  <td nowrap align=center class="TDTitleStyle" height="25">ǩ�»���IP</td>
  <td nowrap align=center class="TDTitleStyle" height="25">��־��־</td>
  <td nowrap align=center class="TDTitleStyle" height="25">KEY���к�</td>
</tr>
<%
  if (ObjConnBean.OpenConnection()) {
    ResultSet rs = null;
    Statement stmt = null;

	System.out.println(DocumentID);
	System.out.println("��ʼ");

 try {
      String strSql = "select * from HTMLHistory Where DocumentID='" + DocumentID + "'"+"order by SignatureID desc";
      rs = ObjConnBean.ExecuteQuery(strSql);
	  System.out.println("����");
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
