<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="java.io.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="DBstep.iDBManager2000.*" %>
<%!
  Integer mDocumentCount;
  DBstep.iDBManager2000 ObjConnBean = new DBstep.iDBManager2000();
%>
<%
    String mScriptName = "/DocumentList.jsp";
    String mServerName="/Service.jsp";
    String mHttpUrlName=request.getRequestURI();
    String mServerUrl="http://"+request.getServerName()+":"+request.getServerPort()+mHttpUrlName.substring(0,mHttpUrlName.lastIndexOf(mScriptName))+mServerName;
%>
<html>
<head>
<title>金格电子签章系统iSignature HTML V8</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<link REL="stylesheet" href="Test.css" type="text/css">
<style type="text/css">
<!--
.SmallButton
{
	BORDER-RIGHT: 1pt ridge;
	BORDER-TOP: 1pt ridge;
	BORDER-LEFT: 1pt ridge;
	WIDTH: 200pt;
	CURSOR: hand;
	COLOR: #000055;
	BORDER-BOTTOM: 1pt ridge;
	BACKGROUND-COLOR: #D1DEEF;
	TEXT-ALIGN: center;
	font: 宋体;
	font-size: 10pt;
}
-->
</style>
<script language="javascript">

//作用：全部选择
function SelectAllDocument()
{
  var mLength=document.getElementsByName("DocumentChk").length;
  var vItem;
  for (var i=0;i<mLength;i++)
  {
         vItem=document.getElementsByName("DocumentChk")[i];
	 vItem.checked=true;
   }
}

//作用：全部不选择
function NoSelectAllDocument()
{
  var mLength=document.getElementsByName("DocumentChk").length;
  var vItem;
  for (var i=0;i<mLength;i++)
  {
         vItem=document.getElementsByName("DocumentChk")[i];
	 vItem.checked=false;
   }
}

//作用：进行批量签章
function RunBatchSignature()
{
  var mLength=document.getElementsByName("DocumentChk").length;
  var vItem;
  var DocumentList="";
  for (var i=0;i<mLength;i++)
  {
         vItem=document.getElementsByName("DocumentChk")[i];
         if (vItem.checked)
         {
         if (i!=mLength-1)
         {
	   DocumentList=DocumentList+vItem.value+";";
          } else
         {
           DocumentList=DocumentList+vItem.value;
         }
         }
   }
  mainForm.SignatureControl.FieldsList="XYBH=协议编号;BMJH=保密级别;JF=甲方签章;YF=乙方签章;HZNR=合作内容;QLZR=权利责任;CPMC=产品名称;DGSL=订购数量;DGRQ=订购日期"       //所保护字段
  mainForm.SignatureControl.Position(0,0);                        //签章位置
  mainForm.SignatureControl.DocumentList=DocumentList;                        //签章页面ID
  mainForm.SignatureControl.WebSetFontOther("True","同意通过","0","宋体","11","000128","True");  //默认签章附加信息及字体,具体参数信息参阅技术白皮书
  mainForm.SignatureControl.SaveHistory="True";                    //是否自动保存历史记录,true保存  false不保存  默认值false
  mainForm.SignatureControl.UserName="wjd";                          //文件版签章用户
  mainForm.SignatureControl.WebCancelOrder=0;			                 //签章撤消原则设置, 0无顺序 1先进后出  2先进先出  默认值0
  mainForm.SignatureControl.DivId = "yfdiv";                    //签章所在层
  mainForm.SignatureControl.RunBatchSignature();                //执行批量签章
}

</script>
</head>
<form name="mainForm">
<body bgcolor="#ffffff">


<div align="center"><font size=4 color=ff0000>金格电子签章系统iSignature HTML V8</font></div>
<hr color="red">
<div>
<font size=2 color=ff0000>
注：正式版可实现更强大的功能，如要正式版，请与金格科技联系。如要正确演示本示例，你必须:<br>
&nbsp;&nbsp;&nbsp;&nbsp;1、如未安装插件，请等待几秒钟安装iSignature HTML签章中间件，请你在打开本页面的弹出窗口时，选择[是]按钮,才能正常运行。<br>
&nbsp;&nbsp;&nbsp;&nbsp;2、iSignature HTML电子签章演示版能直接在IE8、IE9、IE10、IE11里进行签名和印章.

<br></div>

<div id="install" style="display:none">
&nbsp;&nbsp;&nbsp;&nbsp;3、您可以点下面按扭将iSignature电子签章HTML版软件下载到本地安装
</font>
    <input type="button" class="SmallButton" OnClick="location.href='iSignatureHTML.zip';" value="下载金格iSignature电子签章HTML版软件"/>
</div>

<div id="chk" >
<img src="load.gif" width="100%" height="16">
</div>

<div id="obj" style="display:none">
<OBJECT id="SignatureControl" classid="clsid:D85C89BE-263C-472D-9B6B-5264CD85B36E" codebase="iSignatureHTML.cab#version=8,2,2,56" width="0" height="0" >
<param name="ServiceUrl" value="<%=mServerUrl%>"> <!--读去数据库相关信息-->
</OBJECT>
</div>

<input type=button value="新建文档"  onclick="javascript:location.href='DocumentEdit.jsp';">&nbsp;
<input type=button value="批量签章"  onclick="RunBatchSignature();">&nbsp;
<input type=button value="全    选"  onclick="SelectAllDocument();">&nbsp;
<input type=button value="全 不 选"  onclick="NoSelectAllDocument();">&nbsp;
<table border=0  cellspacing='0' cellpadding='0' width=100% align=center class=TBStyle>
<tr>
  <td nowrap align=center class="TDTitleStyle" height="25">选择</td>
  <td nowrap align=center class="TDTitleStyle" height="25">协议编号</td>
  <td nowrap align=center class="TDTitleStyle">保密级别</td>
  <td nowrap align=center class="TDTitleStyle">乙方名称</td>
  <td nowrap align=center class="TDTitleStyle">订购数量</td>
  <td nowrap align=center class="TDTitleStyle">订购日期</td>
  <td nowrap align=center class="TDTitleStyle">操作</td>
</tr>
<%
  String strSql = "",strDocumentID = "";
  if (ObjConnBean.OpenConnection()) {
    ResultSet rs = null;
    Statement stmt = null;

    try {
      strSql = "select DocumentID,BMJH,YF,DGSL,DGRQ from HTMLDocument order by AutoNo desc";
      rs = ObjConnBean.ExecuteQuery(strSql);
      while (rs.next()){
        strDocumentID = rs.getString("DocumentID");
%>
<tr>
  <td class="TDStyle" align=center>
    <input type=checkbox name="DocumentChk" value=<%=rs.getString("DocumentID")%>>
  </td>
  <td class="TDStyle"><%=rs.getString("DocumentID")%>&nbsp;</td>
  <td class="TDStyle"><%=rs.getString("BMJH")%>&nbsp;</td>
  <td class="TDStyle"><%=rs.getString("YF")%>&nbsp;</td>
  <td class="TDStyle"><%=rs.getString("DGSL")%>&nbsp;</td>
  <td class="TDStyle"><%=rs.getString("DGRQ")%>&nbsp;</td>
  <td class="TDStyle"  nowrap>
    <input type=button onclick="javascript:location.href='DocumentEdit.jsp?DocumentID=<%=rs.getString("DocumentID")%>';" value="签名印章">
  </td>
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
</form>
<script language="javascript" type="text/javascript">
  var mObject=false;
  try{
    var mFieldList = mainForm.SignatureControl.FieldsList;
  }catch(e){mObject=true;}

if (!mObject){
  document.all.install.style.display="none";
  document.all.obj.style.display="block";
  document.all.chk.style.display="none";
}
else{
  document.all.install.style.display="block";
  document.all.obj.style.display="none";
  document.all.chk.style.display="none";
}
</script>
</html>
