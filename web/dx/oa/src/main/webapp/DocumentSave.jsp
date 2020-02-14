<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="java.io.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="DBstep.iDBManager2000.*" %>
<%!
  DBstep.iDBManager2000 ObjConnBean = new DBstep.iDBManager2000();
  boolean mResult;
%>
<%
  //作用：此页面文件主要是保存表单基本要素信息，不会保存签章数据信息

	String DocumentID = new String(request.getParameter("DocumentID"));

   if (ObjConnBean.OpenConnection()){
	    String strSql = "SELECT * from HTMLDocument Where DocumentID='" + DocumentID + "'";
    	ResultSet rs = null;
    	rs = ObjConnBean.ExecuteQuery(strSql);
    	if (rs.next()) {
//       		strSql = "update HTMLDocument set DocumentID='"+DocumentID+"',XYBH='"+XYBH+"',BMJH='"+BMJH+"',JF='"+JF+"',YF='"+YF+"',HZNR='"+HZNR+"',";
//       		strSql = strSql + " QLZR='"+QLZR+"',CPMC='"+CPMC+"',DGSL='"+DGSL+"',DGRQ='"+DGRQ+"' where DocumentID = '"+DocumentID+"'";
//		    ObjConnBean.ExecuteUpdate(strSql);
    	}else{
      		java.sql.PreparedStatement prestmt=null;
      		try{
        		String Sql="insert into HTMLDocument (DocumentID) values (?) ";
		        prestmt =ObjConnBean.Conn.prepareStatement(Sql);
        		prestmt.setString(1, DocumentID);
//		        prestmt.setString(2, XYBH);
//        		prestmt.setString(3, BMJH);
//		        prestmt.setString(4, JF);
//				prestmt.setString(5, YF);
//        		prestmt.setString(6, HZNR);
//		        prestmt.setString(7, QLZR);
//        		prestmt.setString(8, CPMC);
//		        prestmt.setString(9, DGSL);
//        		prestmt.setString(10, DGRQ);
		        ObjConnBean.Conn.setAutoCommit(true);
        		prestmt.execute();
		        //ObjConnBean.Conn.commit();
        		prestmt.close();
		        mResult=true;
      		}
      		catch(SQLException e){
       	 		//System.out.println(e.toString());
        		mResult=false;
      		}
    }
  	ObjConnBean.CloseConnection();
  	response.sendRedirect("/admin/oa/task/to/claim/list.json");
  }
%>