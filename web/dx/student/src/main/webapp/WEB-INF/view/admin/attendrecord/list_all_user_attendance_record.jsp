<%@ page import="com.google.gson.Gson" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>打卡记录</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function () {

        });

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function queryNode(id, name) {
            var param = jQuery("#node_" + id).val()
            //创建form表单
            var temp_form = document.createElement("form");
            temp_form.action = "${ctx}/admin/jiaowu/viewRecord.json";
            //如需打开新窗口，form的target属性要设置为'_blank'
            //temp_form.target = "_blank";
            temp_form.method = "post";
            temp_form.style.display = "none";
            //添加参数
            var opt = document.createElement("input");
            opt.name = "node";
            opt.value = param;
            temp_form.appendChild(opt);

            var optName = document.createElement("input");
            optName.name = "name";
            optName.value = name;
            temp_form.appendChild(optName);
            document.body.appendChild(temp_form);
            //提交数据
            temp_form.submit();
        }

        // 导出考勤
        function xlsExcelExport() {
            var totalCount = "${dayDataVOS}"
            if (totalCount === "") {
                alert("暂无数据")
                return false
            }
            window.location.href = "${ctx}/admin/jiaowu/exportAllUserAttend.json";
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">打卡记录</h1>
        <span>
            <span style="color:red">说明</span><br/>
            1.本页面展示打卡记录.<br/>
            2.没有打卡记录的学员不会被导出.<br/>
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="xlsExcelExport()">导出汇总表</a>
                </div>
            </div>
        </div>

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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">卡号</th>
                    <th class="head0 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${dayDataVOS!=null&&dayDataVOS.size()>0 }">
                    <c:forEach items="${dayDataVOS}" var="dv" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${dv.name}</td>
                            <td>${dv.cardNo}</td>
                            <td>
                                <c:set value="${dv.nodes}" var="nodes" scope="request"/>
                                <%
                                    Object str = request.getAttribute("nodes");
                                    Gson gson = new Gson();
                                    String json = gson.toJson(str);
                                %>
                                <input type="hidden" value='<%=json%>' id="node_${status.count}">
                                <a href="javascript: queryNode('${status.count}', '${dv.name}');"
                                   class="stdbtn" title="查看打卡记录">查看打卡记录</a>
                            </td>
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
</body>
</html>