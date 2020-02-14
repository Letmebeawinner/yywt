<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>清单列表</title>
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

        function delInventory(id) {
            if (confirm("确定删除这个吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delInventory.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/inventoryList.json?meetingId="+result.data;
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
        <h1 class="pagetitle"><b>${meeting.name}</b>:会场清单列表</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <div class="disIb ml20 mb10">
                    <a href="${ctx}/admin/houqin/toImport.json?id=${meeting.id}" class="stdbtn ml10">批量导入清单</a>
                </div>

                <div class="disIb ml20 mb10">
                    <a href="${ctx}/admin/houqin/toAddLnventorylist.json?id=${meeting.id}" class="stdbtn ml10">添加清单</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con1"/>
                    <col class="con1"/>
                    <col class="con1"/>
                    <col class="con1"/>
                    <col class="con1"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">设备设施名称</th>
                    <th class="head0 center">规格型号</th>
                    <th class="head0 center">单位</th>
                    <th class="head0 center">数量</th>
                    <th class="head0 center">验收状态</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${inventoryList}" var="lnventorylist" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${lnventorylist.name}</td>
                        <td>${lnventorylist.type}</td>
                        <td>${lnventorylist.unit}</td>
                        <td>${lnventorylist.count}</td>
                        <td>${lnventorylist.ysStatus}</td>
                        <td class="center">
                            <a href="/admin/houqin/toUpdateLnventorylist.json?id=${lnventorylist.id}" class="stdbtn" title="修改">修改</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delInventory('${lnventorylist.id}')">删除</a>
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