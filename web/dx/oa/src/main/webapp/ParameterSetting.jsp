<%@ page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>金格电子签章系统——参数设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<link REL="stylesheet" href="Test.css" type="text/css">
<script language="javascript">
var mParameter = window.dialogArguments;

//输入检测
function Check(){
	if(Signature_Big.value == ""){
		alert("字体大小不能为空！");
		return false;
	}
	if(Signature_Color.value == ""){
		alert("字体颜色不能为空！");
		return false;
	}
	if(Signature_Font.value == ""){
		alert("签章字体不能为空！");
		return false;
	}
	return true;
}

//参数设置
function ParameterSetting(){
	if(!Check()){
		return;
	}
	alert("参数设置成功！");
	var tmp = Language.value + ";" + (AutoSign.checked?"1":"0") + ";" + CancelOrder.value + ";" + DefaultPassword.value + ";" + 
		(Signature_ShowTime.checked?"1":"0") + ";" + Signature_InputAddvice.value + ";" + 
		Signature_Position.value + ";" + Signature_Font.value + ";" +
		Signature_Big.value + ";" + Signature_Color.value + ";" +  
		(Signature_Bold.checked?"1":"0")+ ";"+ ProtectMethod.value+";"+(AutoLockDoc.checked?"1":"0");
	window.returnValue = tmp;
	window.close();
	window.opener = null;
}

//关闭
function Close()
{
    window.close();
}

//默认状态
function DefaultSetting(){
	Language.value = "1";
	AutoLockDoc.checked = true ;
	AutoSign.checked = true;
	CancelOrder.value = "0";
	DefaultPassword.value = ""; 
	Signature_ShowTime.checked = false;
	Signature_InputAddvice.value = ""; 
	Signature_Position.value = "0";
	Signature_Font.value = "宋体";
	Signature_Big.value = "11"; 
	Signature_Color.value = "$000000";
	ProtectMethod.value = mParameter[1];
	Signature_Bold.checked = false;    
	ProtectMethod.value = "1";
}

//当前状态赋值
function CurrentSetting(){
	DefaultSetting();
	Language.value = mParameter[0]=="GBC_"?"1":(mParameter[0]=="ENG_"?"3":"2");
	AutoSign.checked = mParameter[1]=="1"?true:false;
	CancelOrder.value = mParameter[2];
	DefaultPassword.value = mParameter[3];
  AutoLockDoc.checked = mParameter[5]=="1"?true:false;
  
	if(mParameter[4] != undefined){
		Signature_ShowTime.checked = mParameter[4][4]=="1"?true:false;
		Signature_InputAddvice.value = mParameter[4][5];
		Signature_Position.value = mParameter[4][6];
		Signature_Font.value = mParameter[4][7];
		Signature_Big.value = mParameter[4][8];
		Signature_Color.value = mParameter[4][9];
		Signature_Bold.checked = mParameter[4][10]=="1"?true:false;
		ProtectMethod.value = mParameter[4][11];
	}
}
</script>
</head>
<body onload="CurrentSetting();">
  <table cellSpacing="0" cellPadding="0">
    <tr>
	  <td height="40" colspan="3" align="center"><strong>签章相关参数设置（可二次开发调用）</strong></td>
	</tr>
	<tr>
	  <td width="30" height="25" align="right" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">多语言显示</td>
	  <td width="120" height="25" valign="middle" align="left">
	      <SELECT name="Language"  id="Language" style="width:80px">
              <OPTION value="1">简体中文</OPTION>
			  <OPTION value="2" >繁体中文</OPTION>
			  <OPTION value="3" >English</OPTION>
          </SELECT>
	  </td>
	</tr>
	<tr>
	  <td height="25" align="right" valign="middle">&nbsp;</td>
	  <td align="left" valign="middle" height="25">自动锁定文档</td>
	  <td valign="middle" height="25" align="left">
	      <input type="checkbox" name="AutoLockDoc" id="AutoLockDoc"></input>
	  </td>
	</tr>

	<tr>
	  <td height="25" align="right" valign="middle">&nbsp;</td>
	  <td align="left" valign="middle" height="25">自动数字签名</td>
	  <td valign="middle" height="25" align="left">
	      <input type="checkbox" name="AutoSign" id="AutoSign"></input>
	  </td>
	</tr>
		
	<tr>
	  <td width="30" height="25" align="right" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">撤消顺序原则</td>
	  <td width="120" height="25" valign="middle" align="left">
	      <SELECT name="CancelOrder"  id="CancelOrder" style="width:120px">
              <OPTION value="0">无顺序</OPTION>
			  <OPTION value="1">先签章后撤消顺序</OPTION>
			  <OPTION value="2">先签章先撤消顺序</OPTION>
          </SELECT>
	  </td>
	</tr>

	<tr>
	  <td width="30" height="25" align="right" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">保护表单数据</td>
	  <td width="120" height="25" valign="middle" align="left">
	      <SELECT name="ProtectMethod"  id="ProtectMethod" style="width:160px">
              <OPTION value="0">不保护</OPTION>
			  <OPTION value="1">保护表单数据，可操作</OPTION>
			  <OPTION value="2">保存表单数据，并不能操作</OPTION>
          </SELECT>
	  </td>
	</tr>

	<tr>
	  <td width="30" height="25" align="right" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">签章密码默认</td>
	  <td width="120" height="25" valign="middle" align="left">
		  <input type="password" name="DefaultPassword" id="DefaultPassword" style="width:120px"></input>
	  </td>
	</tr>
</table>

<fieldset align="center" style="padding=8px; width:90%; border : 1px solid #E9E8E8;line-height:2.0;text-align:left;COLOR:#EE3C65;FONT-SIZE: 12px;font-family: Verdana">
<legend align="left">盖章设置</legend>
<table align="left">
	<tr>
	  <td height="25" align="left" valign="middle">&nbsp;</td>
	  <td align="left" valign="middle" height="25">显示日期时间</td>
	  <td valign="middle" height="25" align="left">
	      <input type="checkbox" name="Signature_ShowTime" id="Signature_ShowTime" checked></input>
	  </td>
	</tr>

	<tr>
	  <td height="25" align="left" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">签章意见</td>
	  <td width="120" height="25" valign="middle" align="left">
		  <input name="Signature_InputAddvice" id="Signature_InputAddvice" style="width:120px"></input>
	  </td>
	</tr>

	<tr>
	  <td height="25" align="left" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">意见位置</td>
	  <td width="120" height="25" valign="middle" align="left">
	      <SELECT name="Signature_Position"  id="Signature_Position" style="width:120px">
              <OPTION value="0">居中</OPTION>
			  <OPTION value="1">下方</OPTION>
		<OPTION value="2">底部</OPTION>
          </SELECT>
	  </td>
	</tr>

	<tr>
	  <td height="25" align="left" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">意见字体</td>
	  <td width="120" height="25" valign="middle" align="left">
	  	  <input name="Signature_Font" id="Signature_Font" style="width:120px"></input>
	  </td>
	</tr>

	<tr>
	  <td height="25" align="left" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">意见字体大小</td>
	  <td width="120" height="25" valign="middle" align="left">
	  	  <input name="Signature_Big" id="Signature_Big" style="width:120px"></input>
	  </td>
	</tr>

	<tr>
	  <td height="25" align="left" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">意见字体颜色</td>
	  <td width="120" height="25" valign="middle" align="left">
	  	  <input name="Signature_Color" id="Signature_Color" style="width:120px"></input>
	  </td>
	</tr>

	<tr>
	  <td height="25" align="left" valign="middle">&nbsp;</td>
	  <td align="left" valign="middle" height="25">是否使用粗体</td>
	  <td valign="middle" height="25" align="left">
	      <input type="checkbox" name="Signature_Bold" id="Signature_Bold"></input>
	  </td>
	</tr>
</table>
</fieldset>

<table align="center">
	<tr>
	  <td height="40" colspan="3" align="center" valign="bottom"><input type="button" class="btnButton" onClick="ParameterSetting();" value="确 定">
	  &nbsp;&nbsp;<input type="button" class="btnButton" onClick="DefaultSetting();" value="默认设置">
	  &nbsp;&nbsp;<input type="button" class="btnButton" onClick="Close();" value="关闭"></td>
    </tr>
	<tr>
	  <td height="5" colspan="3" align="center"></td>
    </tr>
</table>
</body>
</html>