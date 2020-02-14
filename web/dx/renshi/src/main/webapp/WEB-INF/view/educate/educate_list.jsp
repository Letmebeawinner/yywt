<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>培训项目列表</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/educate.js"></script>
    <script type="text/javascript">
        /**
         * 时间控件
         */
        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#beginTime',
                format:'YYYY-MM-DD hh:mm:ss'
            });
        });
        function exportExcel() {
            jQuery("#getEducateList").prop("action", "${ctx}/admin/rs/exportEducateExcel.json");
            jQuery("#getEducateList").submit();
            jQuery("#getEducateList").prop("action", "${ctx}/admin/rs/getEducateList.json");
        }
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">培训项目列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新建、编辑、删除培训项目，查看项目需要参与的人员<br>
                2. 新建培训项目：点击搜索框中最后的<span style="color:red">新建</span>按钮添加培训项目；<br>
                3. 编辑培训项目：点击操作列中的<span style="color:red">编辑</span>按钮编辑培训项目的信息；<br>
                4. 删除培训项目：点击操作列中的<span style="color:red">删除</span>按钮删除培训项目的信息；<br>
                5. 查看参与人员：点击操作列中的<span style="color:red">参训人员</span>按钮查看项目有关人员。
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getEducateList" action="${ctx}/admin/rs/getEducateList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">培训名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入培训名称" name="educate.name" value="${educate.name}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">培训开始时间 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" readonly placeholder="选择培训开始时间" name="queryBeginTime" id="beginTime" value="<fmt:formatDate value="${educate.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>">
                        </label>

                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="${ctx}/admin/rs/toAddEducate.json" class="stdbtn ml10" >新 建</a>
                    <a href="javascript:void(0)" onclick="exportExcel()" class="stdbtn ml10">导出Excel</a>
                </div>
            </div>
        </div>

        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0" style="width:10%;"/>
                    <col class="con1"/>
                    <col class="con0" style="width:20%;"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">职称</th>
                    <th class="head0 center">培训名称</th>
                    <th class="head1">培训时间</th>
                    <th class="head1">培训单位</th>
                    <th class="head1">培训总结</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${educateList}" var="educate" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${educate.employeeName}</td>
                        <td>${educate.technical}</td>
                        <td>${educate.name}</td>
                        <td><fmt:formatDate value="${educate.beginTime}"  type="date" dateStyle="long"/>-<fmt:formatDate value="${educate.endTime}"  type="date" dateStyle="long"/></td>
                        <td>${educate.trainingUnit}</td>
                        <td><a href="${educate.summarize}">下载总结文档</a> </td>
                        <td class="center">
                            <a href="${ctx}/admin/rs/toUpdateEducate.json?id=${educate.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="${ctx}/admin/rs/getEducateInfo.json?id=${educate.id}" class="stdbtn" title="查看">查看</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delEducate(${educate.id})">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
    </div>
</div>
</body>
</html>