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
<title>������ǩ��ϵͳiSignature HTML V8</title>
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
	font: ����;
	font-size: 10pt;
}
-->
</style>
<script language="javascript">

//���ã�ȫ��ѡ��
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

//���ã�ȫ����ѡ��
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

//���ã���������ǩ��
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
  mainForm.SignatureControl.FieldsList="XYBH=Э����;BMJH=���ܼ���;JF=�׷�ǩ��;YF=�ҷ�ǩ��;HZNR=��������;QLZR=Ȩ������;CPMC=��Ʒ����;DGSL=��������;DGRQ=��������"       //�������ֶ�
  mainForm.SignatureControl.Position(0,0);                        //ǩ��λ��
  mainForm.SignatureControl.DocumentList=DocumentList;                        //ǩ��ҳ��ID
  mainForm.SignatureControl.WebSetFontOther("True","ͬ��ͨ��","0","����","11","000128","True");  //Ĭ��ǩ�¸�����Ϣ������,���������Ϣ���ļ�����Ƥ��
  mainForm.SignatureControl.SaveHistory="True";                    //�Ƿ��Զ�������ʷ��¼,true����  false������  Ĭ��ֵfalse
  mainForm.SignatureControl.UserName="wjd";                          //�ļ���ǩ���û�
  mainForm.SignatureControl.WebCancelOrder=0;			                 //ǩ�³���ԭ������, 0��˳�� 1�Ƚ����  2�Ƚ��ȳ�  Ĭ��ֵ0
  mainForm.SignatureControl.DivId = "yfdiv";                    //ǩ�����ڲ�
  mainForm.SignatureControl.RunBatchSignature();                //ִ������ǩ��
}

</script>
</head>
<form name="mainForm">
<body bgcolor="#ffffff">


<div align="center"><font size=4 color=ff0000>������ǩ��ϵͳiSignature HTML V8</font></div>
<hr color="red">
<div>
<font size=2 color=ff0000>
ע����ʽ���ʵ�ָ�ǿ��Ĺ��ܣ���Ҫ��ʽ�棬������Ƽ���ϵ����Ҫ��ȷ��ʾ��ʾ���������:<br>
&nbsp;&nbsp;&nbsp;&nbsp;1����δ��װ�������ȴ������Ӱ�װiSignature HTMLǩ���м���������ڴ򿪱�ҳ��ĵ�������ʱ��ѡ��[��]��ť,�����������С�<br>
&nbsp;&nbsp;&nbsp;&nbsp;2��iSignature HTML����ǩ����ʾ����ֱ����IE8��IE9��IE10��IE11�����ǩ����ӡ��.

<br></div>

<div id="install" style="display:none">
&nbsp;&nbsp;&nbsp;&nbsp;3�������Ե����水Ť��iSignature����ǩ��HTML��������ص����ذ�װ
</font>
    <input type="button" class="SmallButton" OnClick="location.href='iSignatureHTML.zip';" value="���ؽ��iSignature����ǩ��HTML�����"/>
</div>

<div id="chk" >
<img src="load.gif" width="100%" height="16">
</div>

<div id="obj" style="display:none">
<OBJECT id="SignatureControl" classid="clsid:D85C89BE-263C-472D-9B6B-5264CD85B36E" codebase="iSignatureHTML.cab#version=8,2,2,56" width="0" height="0" >
<param name="ServiceUrl" value="<%=mServerUrl%>"> <!--��ȥ���ݿ������Ϣ-->
</OBJECT>
</div>

<input type=button value="�½��ĵ�"  onclick="javascript:location.href='DocumentEdit.jsp';">&nbsp;
<input type=button value="����ǩ��"  onclick="RunBatchSignature();">&nbsp;
<input type=button value="ȫ    ѡ"  onclick="SelectAllDocument();">&nbsp;
<input type=button value="ȫ �� ѡ"  onclick="NoSelectAllDocument();">&nbsp;
<table border=0  cellspacing='0' cellpadding='0' width=100% align=center class=TBStyle>
<tr>
  <td nowrap align=center class="TDTitleStyle" height="25">ѡ��</td>
  <td nowrap align=center class="TDTitleStyle" height="25">Э����</td>
  <td nowrap align=center class="TDTitleStyle">���ܼ���</td>
  <td nowrap align=center class="TDTitleStyle">�ҷ�����</td>
  <td nowrap align=center class="TDTitleStyle">��������</td>
  <td nowrap align=center class="TDTitleStyle">��������</td>
  <td nowrap align=center class="TDTitleStyle">����</td>
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
    <input type=button onclick="javascript:location.href='DocumentEdit.jsp?DocumentID=<%=rs.getString("DocumentID")%>';" value="ǩ��ӡ��">
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
