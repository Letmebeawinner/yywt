<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>批量导入用气量</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">批量导入用气量</h1>
        <span>
            <span style="color:red">说明</span><br>
            1.本页面用于批量导入用气量<br/>
            2.可下载excel模板,参照模板说明,填写用气量信息，批量导入用气量。
        </span>
    </div><!--pageheader-->
    <div class="mt20">
        <div class="commonWrap">
            <form action="/admin/houqin/import/naturalImport.json" method="post" id="importO"
                  enctype="multipart/form-data">
                <table width="100%" cellspacing="0" cellpadding="0" border="0" class="commonTab01">
                    <caption>&nbsp;</caption>
                    <tbody>
                    <tr>
                        <td align="center"><font color="red">*</font>&nbsp;信息描述</td>
                        <td>excel模版说明：<br>
                            <span style="color: red;">
                                月份:列2018-07<br/>
                            </span>
                            <span style="color: red;">
                                用气区域id：在用气区域中选择对应id添加<br/>
                            </span>
                            <span style="color: red;">
                                本期读数需大于上期读数<br/>
                            </span>
                            <span style="color: red;">
                                单价只能为数字<br/>
                            </span>
                            <br>
                            <span style="color: red;">注意：不要修改表格结构！</span><br>
                        </td>
                    </tr>
                    <tr>
                        <td width="10%" align="center">上传</td>
                        <td>
                            <span class="ml10">
                                <input id="myFile" type="file" value="" name="myFile"/>
                                <form>
                                    <input type="reset" onclick="importExcel()" title="提交" class="reset radius2" value="提交">
                                    <input type="reset" onclick="history.go(-1);" title="返回" class="reset radius2" value="返回">
                                    <input type="reset" onclick='window.location.href="${ctx}/用气量导入模板.xls"' title="下载模板" value="下载模板" class="reset radius2">
                                </form>
                            </span>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>

        </div>
    </div>
</div>
<script type="text/javascript">
    function importExcel() {
        var myFile = jQuery("#myFile").val();
        if (myFile.length <= 0) {
            alert("请选择导入内容");
            return false;
        }
        jQuery("#importO").submit();
    }
</script>
</body>
</html>