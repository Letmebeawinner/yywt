<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>收件箱</title>
    <script type="text/javascript" src = "${ctx}/static/js/official/selectUser.js"></script>
    <script type="text/javascript" src="/pageoffice.js" id="po_js_main"></script>
    <script type="text/javascript">
        function openPageOffice(processDefinitionId) {
            var url = "${ctx}/open/oaHistoryWord.json?processDefinitionId=" + processDefinitionId + "&type=2"
            window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";
        }
        function openLetterInfo(processInstanceId,letterId) {
            jQuery.ajax({
                type:"post",
                url:"/admin/oa/ajax/setRead.json",
                data:{"letterId":letterId},
                dataType:"json",
                async: false,
                success:function (result) {
                    if (result.code=="0") {
                        window.location.href = "${ctx}/admin/oa/history/process/info.json?processInstanceId="+ processInstanceId +"&processDefinitionKey=oa_inner_letter";
                    } else {
                        alert(result.message);
                    }
                }
            });
        }

        jQuery(function(){
            jQuery("#pobmodal-dialog").hide()
        })

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        //清空搜索栏
        function emptyForm() {
            jQuery("#title").val("");
            jQuery("#senderName").val("");
            jQuery("#approvalStatus").val("");
        }
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab">
        <h1 class="pagetitle">收件箱</h1>
    </div>
    <input type="hidden" value="" id="processInstanceId">
    <input type="hidden" value="" id="sysUserIds">
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/oa/process/approvalList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">公文标题 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" class="longinput" placeholder="输入公文标题" name="oaApproval.title" value="${oaApproval.title}" id = "title"/>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">发送人 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" class="longinput" placeholder="输入发送人" name="oaApproval.senderName" value="${oaApproval.senderName}" id = "senderName">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">状态 &nbsp;</span>
                        <label class="vam">
                            <select name="oaApproval.approvalStatus" id = "approvalStatus">
                                <option value="">请选择</option>
                                <option <c:if test="${oaApproval.approvalStatus==0}"> selected="selected"</c:if> value="0">未读</option>
                                <option <c:if test="${oaApproval.approvalStatus==1}"> selected="selected"</c:if> value="1">已读</option>
                            </select>
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
                    <th class="head0 center">发送人</th>
                    <th class="head0 center">公文标题</th>
                    <th class="head0 center">状态</th>
                    <%--<th class="head0 center">流程实例id</th>--%>
                    <th class="head0 center">发布时间</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${oaApprovalList}" var="oaApproval" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${oaApproval.senderName}</td>
                        <td>${oaApproval.title}</td>
                        <td>
                            <c:if test="${oaApproval.approvalStatus==0}">
                                未读
                            </c:if>
                            <c:if test="${oaApproval.approvalStatus==1}">
                                已读
                            </c:if>
                        </td>
                        <%--<td>${oaApproval.processInstanceId}</td>--%>
                        <td><fmt:formatDate value="${oaApproval.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td colspan="2" align="center">
                            <c:if test="${oaApproval.letterType==1}">
                                <a href="javascript:void(0);"class="stdbtn" onclick="openPageOffice('${oaApproval.timeStamp}')"  class="btn btn-danger">详情</a>
                            </c:if>
                            <c:if test="${oaApproval.letterType==2}">
                                <a href="javascript:void(0);" onclick="openLetterInfo('${oaApproval.processInstanceId}','${oaApproval.letterId}');" class="stdbtn" class="btn btn-danger">详情</a>
                            </c:if>
                            <c:if test="${isSend}">
                                <a href="javascript:void(0);"class="stdbtn"  onclick="selectUser('${oaApproval.letterId}','<fmt:formatDate type="both" value="${oaApproval.effectiveTime}" pattern="yyyy-MM-dd"/>')">下发</a>
                                <%--<a href="javascript:void(0);" class="stdbtn"  onclick="senderList(${oaApproval.processInstanceId})">下发记录</a>--%>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div><!-- centercontent -->
    </div>
</div>
</body>
</html>