<%@ page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>������ǩ��ϵͳ������������</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<link REL="stylesheet" href="Test.css" type="text/css">
<script language="javascript">
var mParameter = window.dialogArguments;

//������
function Check(){
	if(Signature_Big.value == ""){
		alert("�����С����Ϊ�գ�");
		return false;
	}
	if(Signature_Color.value == ""){
		alert("������ɫ����Ϊ�գ�");
		return false;
	}
	if(Signature_Font.value == ""){
		alert("ǩ�����岻��Ϊ�գ�");
		return false;
	}
	return true;
}

//��������
function ParameterSetting(){
	if(!Check()){
		return;
	}
	alert("�������óɹ���");
	var tmp = Language.value + ";" + (AutoSign.checked?"1":"0") + ";" + CancelOrder.value + ";" + DefaultPassword.value + ";" + 
		(Signature_ShowTime.checked?"1":"0") + ";" + Signature_InputAddvice.value + ";" + 
		Signature_Position.value + ";" + Signature_Font.value + ";" +
		Signature_Big.value + ";" + Signature_Color.value + ";" +  
		(Signature_Bold.checked?"1":"0")+ ";"+ ProtectMethod.value+";"+(AutoLockDoc.checked?"1":"0");
	window.returnValue = tmp;
	window.close();
	window.opener = null;
}

//�ر�
function Close()
{
    window.close();
}

//Ĭ��״̬
function DefaultSetting(){
	Language.value = "1";
	AutoLockDoc.checked = true ;
	AutoSign.checked = true;
	CancelOrder.value = "0";
	DefaultPassword.value = ""; 
	Signature_ShowTime.checked = false;
	Signature_InputAddvice.value = ""; 
	Signature_Position.value = "0";
	Signature_Font.value = "����";
	Signature_Big.value = "11"; 
	Signature_Color.value = "$000000";
	ProtectMethod.value = mParameter[1];
	Signature_Bold.checked = false;    
	ProtectMethod.value = "1";
}

//��ǰ״̬��ֵ
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
	  <td height="40" colspan="3" align="center"><strong>ǩ����ز������ã��ɶ��ο������ã�</strong></td>
	</tr>
	<tr>
	  <td width="30" height="25" align="right" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">��������ʾ</td>
	  <td width="120" height="25" valign="middle" align="left">
	      <SELECT name="Language"  id="Language" style="width:80px">
              <OPTION value="1">��������</OPTION>
			  <OPTION value="2" >��������</OPTION>
			  <OPTION value="3" >English</OPTION>
          </SELECT>
	  </td>
	</tr>
	<tr>
	  <td height="25" align="right" valign="middle">&nbsp;</td>
	  <td align="left" valign="middle" height="25">�Զ������ĵ�</td>
	  <td valign="middle" height="25" align="left">
	      <input type="checkbox" name="AutoLockDoc" id="AutoLockDoc"></input>
	  </td>
	</tr>

	<tr>
	  <td height="25" align="right" valign="middle">&nbsp;</td>
	  <td align="left" valign="middle" height="25">�Զ�����ǩ��</td>
	  <td valign="middle" height="25" align="left">
	      <input type="checkbox" name="AutoSign" id="AutoSign"></input>
	  </td>
	</tr>
		
	<tr>
	  <td width="30" height="25" align="right" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">����˳��ԭ��</td>
	  <td width="120" height="25" valign="middle" align="left">
	      <SELECT name="CancelOrder"  id="CancelOrder" style="width:120px">
              <OPTION value="0">��˳��</OPTION>
			  <OPTION value="1">��ǩ�º���˳��</OPTION>
			  <OPTION value="2">��ǩ���ȳ���˳��</OPTION>
          </SELECT>
	  </td>
	</tr>

	<tr>
	  <td width="30" height="25" align="right" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">����������</td>
	  <td width="120" height="25" valign="middle" align="left">
	      <SELECT name="ProtectMethod"  id="ProtectMethod" style="width:160px">
              <OPTION value="0">������</OPTION>
			  <OPTION value="1">���������ݣ��ɲ���</OPTION>
			  <OPTION value="2">��������ݣ������ܲ���</OPTION>
          </SELECT>
	  </td>
	</tr>

	<tr>
	  <td width="30" height="25" align="right" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">ǩ������Ĭ��</td>
	  <td width="120" height="25" valign="middle" align="left">
		  <input type="password" name="DefaultPassword" id="DefaultPassword" style="width:120px"></input>
	  </td>
	</tr>
</table>

<fieldset align="center" style="padding=8px; width:90%; border : 1px solid #E9E8E8;line-height:2.0;text-align:left;COLOR:#EE3C65;FONT-SIZE: 12px;font-family: Verdana">
<legend align="left">��������</legend>
<table align="left">
	<tr>
	  <td height="25" align="left" valign="middle">&nbsp;</td>
	  <td align="left" valign="middle" height="25">��ʾ����ʱ��</td>
	  <td valign="middle" height="25" align="left">
	      <input type="checkbox" name="Signature_ShowTime" id="Signature_ShowTime" checked></input>
	  </td>
	</tr>

	<tr>
	  <td height="25" align="left" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">ǩ�����</td>
	  <td width="120" height="25" valign="middle" align="left">
		  <input name="Signature_InputAddvice" id="Signature_InputAddvice" style="width:120px"></input>
	  </td>
	</tr>

	<tr>
	  <td height="25" align="left" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">���λ��</td>
	  <td width="120" height="25" valign="middle" align="left">
	      <SELECT name="Signature_Position"  id="Signature_Position" style="width:120px">
              <OPTION value="0">����</OPTION>
			  <OPTION value="1">�·�</OPTION>
		<OPTION value="2">�ײ�</OPTION>
          </SELECT>
	  </td>
	</tr>

	<tr>
	  <td height="25" align="left" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">�������</td>
	  <td width="120" height="25" valign="middle" align="left">
	  	  <input name="Signature_Font" id="Signature_Font" style="width:120px"></input>
	  </td>
	</tr>

	<tr>
	  <td height="25" align="left" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">��������С</td>
	  <td width="120" height="25" valign="middle" align="left">
	  	  <input name="Signature_Big" id="Signature_Big" style="width:120px"></input>
	  </td>
	</tr>

	<tr>
	  <td height="25" align="left" valign="middle">&nbsp;</td>
	  <td width="120" align="left" valign="middle">���������ɫ</td>
	  <td width="120" height="25" valign="middle" align="left">
	  	  <input name="Signature_Color" id="Signature_Color" style="width:120px"></input>
	  </td>
	</tr>

	<tr>
	  <td height="25" align="left" valign="middle">&nbsp;</td>
	  <td align="left" valign="middle" height="25">�Ƿ�ʹ�ô���</td>
	  <td valign="middle" height="25" align="left">
	      <input type="checkbox" name="Signature_Bold" id="Signature_Bold"></input>
	  </td>
	</tr>
</table>
</fieldset>

<table align="center">
	<tr>
	  <td height="40" colspan="3" align="center" valign="bottom"><input type="button" class="btnButton" onClick="ParameterSetting();" value="ȷ ��">
	  &nbsp;&nbsp;<input type="button" class="btnButton" onClick="DefaultSetting();" value="Ĭ������">
	  &nbsp;&nbsp;<input type="button" class="btnButton" onClick="Close();" value="�ر�"></td>
    </tr>
	<tr>
	  <td height="5" colspan="3" align="center"></td>
    </tr>
</table>
</body>
</html>