<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/base.jsp" %>
<html>
<head>
    <title>操作结果</title>
</head>
<body>
<div class="centercontent padding10">
    <div class="errorwrapper error403">
        <div class="errorcontent">
            <h1>
                <c:if test="${success == true}">
                    批量导入成功
                </c:if>
                <c:if test="${success == false}">
                    操作失败
                </c:if>
            </h1>

            <p>
                <c:if test="${fn:length(message) <= 20}">
                    ${message}
                </c:if>
                <c:if test="${fn:length(message) > 20}">
                    系统异常
                </c:if>
                
            </p>
            <br />
            <button class="stdbtn btn_black" onclick="history.back()">返回入库页面</button> &nbsp;
            <button onclick="location.href='${ctx}/admin/houqin/queryAllGoods.json'" class="stdbtn btn_orange">物品管理</button>
        </div><!--errorcontent-->
    </div><!--errorwrapper-->
</div>
</body>
</html>
