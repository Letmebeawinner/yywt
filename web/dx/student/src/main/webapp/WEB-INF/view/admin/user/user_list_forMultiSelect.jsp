<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%@ include file="/popUpBase.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>干部列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function(){
            var studentId="${user.studentId}";
            jQuery("#studentId").val(studentId);
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
            jQuery("input:radio").attr("checked",false);
        }

        var ids="";
        var names="";
        function tijiao(){
            var qstChecked = jQuery(".questionIds:checked");
            if (qstChecked.length == 0) {
                jAlert('请选择干部','提示',function() {});
                return;
            }
            qstChecked.each(function() {
                var userIdAndName=jQuery(this).val();
                ids+=userIdAndName.split("-")[0]+",";
                names+=userIdAndName.split("-")[1]+",";
            });
            opener.addUsers(ids,names);
            window.close();
        }
    </script>
</head>
<body>
<div class="tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">干部列表</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/jiaowu/user/userListForMultiSelect.json" method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">姓名 &nbsp;</span>
                        <label class="vam">
                            <input id="studentId" style="width: auto;" name="name" type="text" class="hasDatepicker" value="" placeholder="请输入姓名">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="tijiao()" class="stdbtn btn_orange">确 定</a>
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
                    <th class="head0 center">班型</th>
                    <th class="head0 center">班次</th>
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">手机号</th>
                    <th class="head0 center">创建时间</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${userList!=null&&userList.size()>0 }">
                    <c:forEach items="${userList}" var="user">
                        <tr>
                            <td width="60px"><input type="checkbox" name="userId" value="${user.id}-${user.name}" class="questionIds" />${user.id}</td>
                            <td width="100px">${user.classTypeName}</td>
                            <td>${user.className}</td>
                            <td width="80px">${user.name}</td>
                            <td>${user.mobile}</td>
                            <td><fmt:formatDate type="both" value="${user.createTime}" pattern="yyyy-MM-dd"/></td>
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