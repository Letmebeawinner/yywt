<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>会议室列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delMeeting(id) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/deleteMeeting.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result.code=="0") {
                            window.location.reload();
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
        <h1 class="pagetitle">会议室列表</h1>

        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于会议室信息列表查看；<br>
            2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
            3.编辑：点击<span style="color:red">编辑</span>修改用会议室信息；<br>
            4.删除：点击<span style="color:red">删除</span>，删除会议室信息；<br>
        </div>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/oa/queryAllMeeting.json" method="post">

                    <%--  <div class="disIb ml20 mb10">
                          <span class="vam">ID &nbsp;</span>
                          <label class="vam">
                              <input style="width: auto;" name="meeting.id" type="text" class="hasDatepicker" value="${meeting.id}" placeholder="输入id" onkeyup="value=value.replace(/[^\d]/g,'')">
                          </label>
                      </div>--%>

                    <div class="disIb ml20 mb10">
                        <span class="vam">名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="meeting.name" type="text" class="hasDatepicker" value="${meeting.name}" placeholder="输入名称">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
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
                    <th class="head0 center">会议室名称</th>
                    <th class="head1">会议室容量</th>
                    <th class="head1">会议室地点</th>
                    <th class="head1">会议室配置</th>
                    <th class="head1">会议室状态</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${meetingList}" var="meeting" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${meeting.name}</td>
                        <td>${meeting.amount}</td>
                        <td>${meeting.place}</td>
                        <td>${meeting.configure}</td>
                        <td>
                            <c:if test="${meeting.status == 0}">
                                未使用
                            </c:if>
                            <c:if test="${meeting.status == 0}">
                                已使用
                            </c:if>
                        </td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/toUpdateMeeting.json?id=${meeting.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delMeeting(${meeting.id})">删除</a>
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