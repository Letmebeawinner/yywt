<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>房间列表</title>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("select[name='searchFloor']").val(jQuery("input[name='searchOpt']").val())
        })
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
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">房间列表</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <input type="hidden" value="${searchFloor}" name="searchOpt">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/BedRoomList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">地点 &nbsp;</span>
                        <label class="vam">
                            <select name="searchFloor">
                                <option value="">--请选择--</option>
                                <option value="1">1号楼</option>
                                <option value="2">2号楼</option>
                                <option value="3">3号楼</option>
                                <option value="5">5号楼</option>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>
            </div>
        </div>

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th class="head0 center">ID</th>
                    <th class="head0 center">房间名称</th>
                    <th class="head0 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${roomList}" var="room" varStatus="index">
                    <tr>
                        <td>${index.index+1}</td>
                        <td>${room.name}</td>
                        <td class="head0 center">
                            <a href="${ctx}/admin/houqin/passInfoList.json?searchRoom=${room.id}" class="stdbtn" title="房间使用记录">房间使用记录</a>
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
</body>
</html>