<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>会场管理</title>
    <script type="text/javascript">
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
            if (confirm("确定删除这个会场吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delMeeting.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllMeeting.json";
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }


        function repairMeeting(id, status) {
            jQuery.ajax({
                url: "${ctx}/admin/houqin/repairMeeting.json",
                data: {"id": id, "status": status},
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllMeeting.json";
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }


    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">会场列表</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于显示会场列表<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryAllMeeting.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">会场名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="meeting.name" type="text" class="hasDatepicker"
                                   value="${meeting.name}" placeholder="输入会场名称">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/toAddMeeting.json" class="stdbtn ml10">添加会场</a>
                    <%--<a href="${ctx}/admin/houqin/toApplyMeeting.json" class="stdbtn ml10">使用会场</a>--%>
                    <a href="${ctx}/admin/houqin/to/meetingApply.json" class="stdbtn ml10">使用会场</a>
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
                    <th class="head0 center">会场名称</th>
                    <th class="head0 center">可容纳人数</th>
                    <th class="head0 center">移交时间</th>
                    <th class="head0 center">投入使用时间</th>
                    <th class="head0 center">状态</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${meetingList}" var="meet" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${meet.name}</td>
                        <td>${meet.peopleNo}</td>
                        <td><fmt:formatDate value="${meet.turnTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${meet.useTime}" pattern="yyyy-MM-dd"/></td>
                        <td>
                            <c:if test="${meet.status==0}">正常</c:if>
                            <c:if test="${meet.status==1}"><font style="color: red">维修中</font></c:if>
                            <c:if test="${meet.status==2}">使用中</c:if>
                        </td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/toUpdateMeeting.json?id=${meet.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="${ctx}/admin/houqin/inventoryList.json?meetingId=${meet.id}" class="stdbtn" title="清单列表">清单列表</a>
                            <a href="${ctx}/admin/houqin/queryMeetingRecord.json?meetingId=${meet.id}" class="stdbtn" title="清单列表">会场预约记录</a>
                            <a href="${ctx}/admin/houqin/queryRepairRecordList.json?meetingRepairRecord.meetingId=${meet.id}" class="stdbtn" title="清单列表">会场维护记录</a>
                            <a href="${ctx}/admin/houqin/meetingApplyRecordList.json?meetingId=${meet.id}" class="stdbtn" title="清单列表">会场预约记录(图)</a>
                            <c:if test="${meet.status==0}">
                                <a href="javascript:void(0)" class="stdbtn" title="维修" onclick="repairMeeting('${meet.id}','1')">维修</a>
                            </c:if>
                            <c:if test="${meet.status==1}">
                                <a href="javascript:void(0)" class="stdbtn btn_orange" title="取消维修" onclick="repairMeeting('${meet.id}','0')">取消维修</a>
                            </c:if>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delMeeting('${meet.id}')">删除</a>
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
</div>
</body>
</html>