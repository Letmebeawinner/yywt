<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%@ include file="/popUpBase.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>选择单位</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("input:radio").attr("checked",false);
            jQuery("input:text").val("");
        }


        var myArrayMoveStock = new Array();
        function tijiao(){
            var qstChecked = jQuery(".questionIds:checked");
            if (qstChecked.length == 0) {
                jAlert('请选择单位','提示',function() {});
                return;
            }
            qstChecked.each(function() {
                var unitIdAndName=jQuery(this).val();
                myArrayMoveStock.push(unitIdAndName.split("-")[0]);
                myArrayMoveStock.push(unitIdAndName.split("-")[1]);
            });
            opener.addUnit(myArrayMoveStock);
            window.close();
        }
    </script>
</head>
<body>
<div class="tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">单位列表</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/jiaowu/user/unitListForSelect.json" method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">单位名称 &nbsp;</span>
                        <label class="vam">
                            <input id="name" style="width: auto;" name="unit.name" type="text" class="hasDatepicker" value="${unit.name}" placeholder="请输入单位名称">
                        </label>
                    </div>

                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="tijiao()" class="stdbtn ml10">确定</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1" />
                    <col class="con0"/>
                    <col class="con1" />
                    <col class="con0"/>
                    <col class="con1" />
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">ID</th>
                    <th class="head0 center">单位名称</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${unitList!=null&&unitList.size()>0 }">
                    <c:forEach items="${unitList}" var="unit">
                        <tr>
                            <td style="width: 50%"><input type="radio" value="${unit.id}-${unit.name}" class="questionIds" name="teacherId"/>${unit.id }</td>
                            <td style="width: 50%">${unit.name}</td>
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