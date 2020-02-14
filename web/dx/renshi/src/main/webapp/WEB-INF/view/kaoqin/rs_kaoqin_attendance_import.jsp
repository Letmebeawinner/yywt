<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>批量导入考勤</title>
    <style>
        .laydate_table {
            display: none;
        }

        #laydate_hms {
            display: none !important;
        }
    </style>
</head>
<body>
<section class="centercontent tables">
    <%--<div class="page_head">--%>
        <%--<h4><em class="icon14 i_01"></em>&nbsp;<span>考勤管理</span> &gt; <span>批量导入</span></h4>--%>
    <%--</div>--%>
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">考勤管理</h1>
            <span>
                <span style="color:red">说明</span><br>
                 1.本页面用于批量导入考勤记录<br/>
                 <%--2.没有打卡记录的教师不会被导出.<br/>--%>
            </span>
    </div><!--pageheader-->
    <div class="mt20">
        <div class="commonWrap">
            <form action="/admin/rs/import/attendanceSheet.json" method="post" id="importO"
                  enctype="multipart/form-data">
                <table width="100%" cellspacing="0" cellpadding="0" border="0" class="commonTab01">
                    <caption>&nbsp;</caption>
                    <tbody>
                    <tr>
                        <td align="center"><font color="red">*</font>&nbsp;信息描述</td>
                        <td>excel模版说明：<br>
                        <span style="color: red;">考勤符号：出勤用“1”表示，迟到、早退用“2”表示，旷工用“3”表示，病假用“4”表示，
                            <br>事假用“5”表示，婚假、产假、丧假和探亲假用“6”表示，
                            <br> 法定节假日、法定休息日用“7”表示，加班日用“8”，调休用“9”。
                        </span><br>
                            <span style="color: red;">注意：不要修改表格结构！</span><br>
                        </td>
                    </tr>
                    <tr>
                        <td width="10%" align="center">上传</td>
                        <td>
					<span class="ml10">
                        <input id="myFile" type="file" value="" name="myFile"/>
                          <div class="tableoptions disIb mb10">
                            <span class="vam">部门 &nbsp;</span>
                                <label class="vam">
                                    <select name="departmentId" class="vam">
                                        <option value="">请选择</option>
                                        <option value="0">全部</option>
                                        <c:forEach items="${departmentList}" var="department">
                                            <c:if test="${department.parentId!=null && department.parentId>0}">
                                                <option value="${department.id}"
                                                        <c:if test="${departmentId==department.id}">selected</c:if>>${department.departmentName}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </label>
                          </div>
                        <form>
                            考勤月份：<input type="text" name="attendanceTime" id="addTime" readonly style="text-align: center;">
                            考勤人员：<input type="text" name="attendanceName" value="" id="attendanceName" style="text-align: center;">
                            <input type="reset" onclick="importExcel()" title="提交" class="reset radius2" value="提交">
                            <input type="reset" onclick="history.go(-1);" title="返回" class="reset radius2" value="返回">
                            <input type="reset" onclick='window.location.href="${ctx}/考勤表.xls"' title="下载模板" value="下载模板"
                                 class="reset radius2">
					    </form>	
                        </span>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>

        </div>
    </div>
</section>
<script type="text/javascript" src="${ctx}/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    $(function () {
        laydate.skin('molv');
        laydate({
            elem: '#addTime',
            istoday: false,
            format: 'YYYY-MM'
        });
        //var name = $("#addTime").val();
        //$("#time").html(name)
    });

    function importExcel() {
        var myFile = $("#myFile").val();
        var attendanceName = $("#attendanceName").val();
        var departmentId = $("#departmentId").val();
        if (myFile.length <= 0) {
            alert("请选择导入内容");
            return false;
        }
        if ("" == departmentId) {
            alert("请选择部门");
            return false;
        }
        if ("" == attendanceName) {
            alert("请输入考勤人姓名");
            return false;
        }
        $("#importO").submit();
    }
</script>
</body>
</html>