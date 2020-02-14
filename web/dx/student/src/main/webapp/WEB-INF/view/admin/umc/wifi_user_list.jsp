<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>WIFI用户</title>
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

        function resetWifiUser(wifiUserId) {
            if (confirm("是否重置")) {
                jQuery.ajax({
                    url: "${ctx}/admin/jiaowu/wifiuser/reset.json",
                    data: {"wifiUserId": wifiUserId},
                    type:'post',
                    dataType:'json',
                    success: function (rs) {
                        jAlert(rs.message,'提示',function() {
                            window.location.reload()
                        });
                    },
                    error: function () {
                        jAlert('添加失败, 请联系管理员','提示',function() {});
                    }
                })
            }
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">WIFI用户</h1>
        <span>
                    <span style="color:red">说明</span><br/>
					1.本页面展示WIFI用户.<br/>
					2.可通过账号名称查询对应的WIFI用户.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/wifiuser/list.json"
                      method="get">
                    <div class="disIb ml20 mb10">
                        <label class="vam">
                            <span class="vam">&nbsp;&nbsp;姓名&nbsp; </span>
                            <input style="width: auto;" name="wifiUser.username" type="text"
                                   value="${wifiUser.username}" placeholder="请输入WIFI用户姓名">
                            <span class="vam">手机号 &nbsp;</span>
                            <input style="width: auto;" name="wifiUser.account" type="text"
                            value="${wifiUser.account}" placeholder="请输入WIFI用户手机号">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>
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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">账号</th>
                    <th class="head0 center">真实姓名</th>
                    <th class="head0 center">密码</th>
                    <th class="head0 center">账号有效期</th>
                    <th class="head0 center">手机号</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${wifiUserList!=null&&wifiUserList.size()>0 }">
                    <c:forEach items="${wifiUserList}" var="wu" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${wu.account}</td>
                            <td>${wu.username}</td>
                            <td>${wu.password}</td>
                            <td>${wu.overTime}</td>
                            <td>${wu.phone}</td>
                            <td class="center">
                                <a href="javascript:void(0)" onclick="resetWifiUser('${wu.id}')"
                                   class="stdbtn" title="重置密码">重置密码</a>
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