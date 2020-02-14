<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%@ include file="/popUpBase.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>学员列表</title>
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

        var myArrayMoveStock = new Array();
        function tijiao(){
            var qstChecked = jQuery(".questionIds:checked");
            if (qstChecked.length == 0) {
                jAlert('请选择学员','提示',function() {});
                return;
            }
            qstChecked.each(function() {
                var userIdAndName=jQuery(this).val();
                myArrayMoveStock.push(userIdAndName.split("-")[0]);
                myArrayMoveStock.push(userIdAndName.split("-")[1]);
                myArrayMoveStock.push(userIdAndName.split("-")[2]);
            });
            opener.addUser(myArrayMoveStock);
            window.close();
        }
    </script>
</head>
<body>
<div class="tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">学员列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面以弹出框形式展示某班次下学员的列表,供其它页面选择.<br />
					2.可通过学号查询对应的学员.<br />
					3.可点击"确认"按钮,将选中的学员传到另一页面上.<br />
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/jiaowu/user/userListOfOneClassForSelect.json" method="get">


                    <div class="disIb ml20 mb10">
                        <span class="vam">学号 &nbsp;</span>
                        <label class="vam">
                            <input id="studentId" style="width: auto;" name="studentId" type="text" class="hasDatepicker" value="" placeholder="请输入学号">
                        </label>
                    </div>

                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="tijiao()" class="stdbtn btn_orange">确 定</a>
                </div>
                <%--  <div class="disIb ml20 mb10">
                        <span class="vam">总人数: &nbsp;</span>
                        <label class="vam">
                        ${totalNum}人
                        </label>
                    </div> --%>
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
                    <th class="head1 center">学号</th>
                    <th class="head0 center">名称</th>
                    <th class="head1 center">身份证号</th>
                    <th class="head0 center">手机号</th>
                    <th class="head0 center">邮箱</th>
                    <th class="head0 center">性别</th>
                    <th class="head0 center">年龄</th>
                    <th class="head0 center">创建时间</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${userList!=null&&userList.size()>0 }">
                    <c:forEach items="${userList}" var="user">
                        <tr>
                            <td><input type="radio" name="userId" value="${user.id}-${user.name}-${user.studentId}" class="questionIds" />${user.id}</td>
                            <td>${user.classTypeName}</td>
                            <td>${user.className}</td>
                            <td>${user.studentId}</td>
                            <td>${user.name}</td>
                            <td>${user.idNumber}</td>
                            <td>${user.mobile}</td>
                            <td>${user.email}</td>
                            <td>${user.sex}</td>
                            <td>${user.age}</td>
                            <td><fmt:formatDate type="both" value="${user.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
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