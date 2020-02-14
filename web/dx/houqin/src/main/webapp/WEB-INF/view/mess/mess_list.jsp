<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>食堂列表</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delMess(id) {
            if (confirm("确定删除这个吗？")) {
            jQuery.ajax({
                    url: "${ctx}/admin/houqin/delMess.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code==0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllMess.json";
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">食堂列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于食堂管理；<br>
            2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
            3.编辑：点击<span style="color:red">编辑</span>修改食堂管理信息；<br>
            4.删除：点击<span style="color:red">删除</span>，删除食堂管理信息；<br>
            5.管理人员：点击<span style="color:red">管理人员</span>，查看管理人员列表；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryAllMess.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">食堂名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="mess.name" type="text" class="hasDatepicker" value="${mess.name}" placeholder="食堂名称">
                        </label>
                    </div>

                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/toAddMess.json"  class="stdbtn ml10">添 加</a>
                    <a href="${ctx}/admin/houqin/updateManageMobile.json"  class="stdbtn ml10">食堂主管电话</a>
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
                    <th class="head0 center">ID</th>
                    <th class="head0 center">食堂名称</th>
                    <th class="head0 center">容纳人数</th>
                    <th class="head0 center">早饭时间</th>
                    <th class="head0 center">午饭时间</th>
                    <th class="head0 center">晚饭时间</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${messList}" var="mess">
                    <tr>
                        <td>${mess.id}</td>
                        <td>${mess.name}</td>
                        <td>${mess.peopleNum}</td>
                        <td>${mess.morning}</td>
                        <td>${mess.noon}</td>
                        <td>${mess.night}</td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/toUpdateMess.json?id=${mess.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delMess('${mess.id}')">删除</a>
                            <a href="${ctx}/admin/houqin/queryAllMessManage.json?id=${mess.id}" class="stdbtn" title="管理人员">管理人员</a>
                            <a href="${ctx}/admin/houqin/queryAllDishes.json?id=${mess.id}" class="stdbtn" title="菜品人员">菜品管理</a>
                        </td>
                    </tr>
                </c:forEach>
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