<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%@ include file="/popUpBase.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>选择教室</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        var classIds = "";
        var classNames = "";

        function tijiao() {
            var qstChecked = jQuery(".questionIds:checked");
            if (qstChecked.length == 0) {
                jAlert('请选择教室', '提示', function () {
                });
                return;
            }
            qstChecked.each(function () {
                var classIdAndName = jQuery(this).val();
                classIds += classIdAndName.split("-")[0];
                classNames += classIdAndName.split("-")[1];
            });
            opener.addClassId(classIds, classNames);
            window.close();
        }

        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("input:radio").attr("checked",false);
            jQuery("input:text").val("");
        }
    </script>
</head>
<body>

<div class="tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">教室列表</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/jiaowu/class/meetList.json" method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">会场名称 &nbsp;</span>
                        <label class="vam">
                            <input id="name" name="name" type="text" value="${name}" placeholder="请输入会场名称">
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
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">ID</th>
                    <th class="head0 center">会场名称</th>
                    <th class="head0 center">可容纳人数</th>
                    <th class="head1">状态</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${meetingList!=null&&meetingList.size()>0 }">
                    <c:forEach items="${meetingList}" var="meet">
                        <tr>
                            <td><input type="radio" value="${meet.id}-${meet.name}" class="questionIds"
                                       name="teacherId"/>${meet.id }</td>
                            <td>${meet.name}</td>
                            <td>${meet.peopleNo}</td>
                            <td>
                                <c:if test="${meet.status==0}">未使用</c:if>
                                <c:if test="${meet.status==1}"><font style="color: red">维修中</font></c:if>
                                <c:if test="${meet.status==2}">使用中</c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>