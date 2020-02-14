<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>批次管理</title>
    <script type="text/javascript" src="${ctx}/static/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function () {

        });

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function toSave(messStockId) {
            window.location.href = "/admin/houqin/lot/toSave/" + messStockId + ".json";
        }


        function delMeetingTopic(id) {
            jConfirm('您确定要删除吗?', '确认', function (r) {
                if (r) {
                    jQuery.ajax({
                        url: '/admin/oa/conference/meetingTopic/del.json?id=' + id,
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
                        error: function () {
                            jAlert('删除失败', '提示', function () {
                            });
                        }
                    });
                }
            });
        }


    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">批次管理</h1>
        <span>
                    <span style="color:red">说明</span><br/>
					1.本页面展示批次列表.<br/>
					2.可通过批次列表名称查询对应的批次列表.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="/admin/houqin/lot/list/${messStockId}.json"
                      method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">批次编号 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="searchOpt" type="text"
                                   value="${searchOpt}" placeholder="请输入批次编号">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>

                <div class="disIb ml20 mb10">
                    <label class="vam">
                        <a href="javascript:void(0)" onclick="toSave('${messStockId}')" class="stdbtn btn_orange">入库</a>
                    </label>
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
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">id</th>
                    <th class="head0 center">批次编号</th>
                    <th class="head0 center">批次价格</th>
                    <th class="head0 center">剩余数量</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${lotList!=null&&lotList.size()>0 }">
                    <c:forEach items="${lotList}" var="ll" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${ll.lotNumber}</td>
                            <td>${ll.lotPrice}</td>
                            <td>${ll.lotAmount}</td>
                            <td class="center">
                                <a href="/admin/houqin/lot/toOutbound/${ll.id}.json"
                                   class="stdbtn" title="出库">出库</a>
                                <a href="/admin/houqin/lot/listLotRecord/${ll.id}.json"
                                   class="stdbtn" title="记录">出库记录</a>
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
</body>
</html>