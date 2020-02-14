<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>发件箱</title>
    <script type="text/javascript" src = "${ctx}/static/js/official/selectUser.js"></script>
    <script type="text/javascript" src="/pageoffice.js" id="po_js_main"></script>
    <script type="text/javascript">
        function openPageOffice(processDefinitionId) {
            var url = "${ctx}/open/oaHistoryWord.json?processDefinitionId=" + processDefinitionId + "&type=2"
            window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";
        }

        function resend(id){
            jQuery.ajax({
                type:"post",
                url:"/admin/oa/ajax/resend.json",
                data:{"id":id},
                dataType:"json",
                async: false,
                success:function (result) {
                    if (result.code=="0") {
                        alert("发送成功");
                        window.location.reload();
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
            jQuery("#sysUserName").val("");
            jQuery("#approvalStatus").val("");
        }
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab">
        <h1 class="pagetitle">发件箱</h1>
    </div>
    <input type="hidden" value="" id="processInstanceId">
    <input type="hidden" value="" id="sysUserIds">
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/oa/process/approvalRecordsList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">公文标题 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" class="longinput" placeholder="输入公文标题" name="oaApproval.title" value="${oaApproval.title}" id = "title"/>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">接收人 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" class="longinput" placeholder="输入接收人" name="oaApproval.sysUserName" value="${oaApproval.sysUserName}" id = "sysUserName">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">状态 &nbsp;</span>
                        <label class="vam">
                            <select name="oaApproval.approvalStatus" id = "approvalStatus">
                                <option value="">请选择</option>
                                <option <c:if test="${oaApproval.approvalStatus==0}"> selected="selected"</c:if> value="0">未读</option>
                                <option <c:if test="${oaApproval.approvalStatus==1}"> selected="selected"</c:if> value="1">已读</option>
                                <option <c:if test="${oaApproval.approvalStatus==2}"> selected="selected"</c:if> value="2">超期未读</option>
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
                    <th class="head0 center">接收人</th>
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
                    <tr <c:if test="${now>oaApproval.effectiveTime}">class="red_tr"</c:if>>
                        <td>${status.count}</td>
                        <td>${oaApproval.sysUserName}</td>
                        <td>${oaApproval.title}</td>
                        <td>
                            <c:if test="${now>oaApproval.effectiveTime}">
                                超期未读
                            </c:if>
                            <c:if test="${oaApproval.effectiveTime==null || now<=oaApproval.effectiveTime}">
                                <c:if test="${oaApproval.approvalStatus==0}">
                                    未读
                                </c:if>
                                <c:if test="${oaApproval.approvalStatus==1}">
                                    已读
                                </c:if>
                            </c:if>
                        </td>
                        <%--<td>${oaApproval.processInstanceId}</td>--%>
                        <td><fmt:formatDate value="${oaApproval.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td colspan="2" align="center">
                            <c:if test="${oaApproval.letterType==1}">
                                <a href="javascript:void(0);"class="stdbtn" onclick="openPageOffice('${oaApproval.timeStamp}')"  class="btn btn-danger">详情</a>
                            </c:if>
                            <c:if test="${oaApproval.letterType==2}">
                                <a href="${ctx}/admin/oa/history/process/info.json?processInstanceId=${oaApproval.processInstanceId}&processDefinitionKey=oa_inner_letter"class="stdbtn" class="btn btn-danger">详情</a>
                            </c:if>
                            <c:if test="${oaApproval.approvalStatus==0}">
                                <a href="javascript:void(0);"class="stdbtn" onclick="resend('${oaApproval.id}')"  class="btn btn-danger">重新发送</a>
                            </c:if>
                            <%--<a href="javascript:void(0);"class="stdbtn"  onclick="selectUser(${oaApproval.processInstanceId})">下发</a>--%>
                            <%--<a href="javascript:void(0);" class="stdbtn"  onclick="senderList(${oaApproval.processInstanceId})">下发记录</a>--%>
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