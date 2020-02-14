<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>单位列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript">
        jQuery(function () {


        });

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("#name").val("");
        }
        function importExcel() {

            var myFile = jQuery("#myFile").val();
            if (myFile.length <= 0) {
                jAlert('请选择导入内容','提示',function() {});
                return false;
            }
            jQuery("#form1").submit();
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">单位列表</h1>
        <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示单位列表.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/user/unitList.json" method="get">

                    <div class="tableoptions disIb mb10">
                        <span class="vam">单位名称 &nbsp;</span>
                        <label class="vam">
                            <input id="name" style="width: auto;" name="unit.name" type="text" value="${unit.name}"
                                   placeholder="请输入单位名称">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>
                <%--<form id="form1" class="stdform" method="post" action="${ctx }/admin/jiaowu/user/batchImportUnit.json" enctype="multipart/form-data">--%>
                <%--<div class="">--%>
                        <%--<label>上传</label>--%>
                        <%--<span class="field">--%>
                            <%--<input id="myFile" type="file" value="" name="myFile"/>--%>
                            <%--</span>--%>
                        <%--<button class="radius2" onclick="importExcel()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">--%>
                            <%--提交--%>
                        <%--</button>--%>
                <%--</div>--%>
                <%--</form>--%>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">单位ID</th>
                    <th class="head0 center">单位名称</th>
                    <th class="head0 center">可登录用户名</th>
                    <c:if test="${showUpdatePassword==true}">
                        <th class="head0 center">操作</th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:if test="${unitList!=null&&unitList.size()>0}">
                    <c:forEach items="${unitList}" var="unit" varStatus="index">
                        <tr>
                            <td>${unit.id}</td>
                            <td>${unit.name}</td>
                            <td>${unit.unitNameNo}</td>
                            <c:if test="${showUpdatePassword==true}">
                                <td><a href="${ctx}/admin/jiaowu/user/toUpdateUnitUserPassword.json?id=${unit.id}"
                                       class="stdbtn btn_orange" title="重置密码">重置密码</a></td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>