<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%@ page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Excel文件上传列表</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">教学动态列表</h1>
        <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示Excel文件上传列表.<br />
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">标题</th>
                    <th class="head0 center">文件名称</th>
                    <th class="head0 center">上传时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${uploadExcelHistoryList!=null&&uploadExcelHistoryList.size()>0 }">
                    <c:forEach items="${uploadExcelHistoryList}" var="uploadExcelHistory" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
                            <td>${uploadExcelHistory.title}</td>
                            <td><a href="javascript:void(0)" onclick="openPageOffice(${uploadExcelHistory.id})">${uploadExcelHistory.fileName}</a></td>
                            <td><fmt:formatDate type="both" value="${uploadExcelHistory.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td class="center">
                                <a href="${ctx}/admin/jiaowu/excel/toUpdate/${uploadExcelHistory.id}.json" class="stdbtn" title="修改">修改</a>
                                <a href="javascript:void(0)" onclick="deleteExcelHistoryById(${uploadExcelHistory.id})" class="stdbtn" title="删除">删除</a>
                                <a href="${uploadExcelHistory.fileUrl}" class="stdbtn" title="下载">下载</a>
                            </td>
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
<script type="text/javascript" src="/pageoffice.js" id="po_js_main"></script>
<script>
    function openPageOffice(id) {
        var url = "${ctx}/open/assetsPossessionExcel.json?id=" + id;
        window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";
    }

    function deleteExcelHistoryById(id) {
        jConfirm('您确定要删除吗?', '确认', function (r) {
            if (r) {
                jQuery.ajax({
                    url: '${ctx}/admin/jiaowu/excel/deleteExcelHistoryById.json?id=' + id,
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            window.location.reload();
                        } else {
                            jAlert(result.message, '提示', function () {
                            });
                        }
                    },
                    error: function (e) {
                        jAlert('删除失败', '提示', function () {
                        });
                    }
                });
            }
        });
    }
</script>
</body>
</html>