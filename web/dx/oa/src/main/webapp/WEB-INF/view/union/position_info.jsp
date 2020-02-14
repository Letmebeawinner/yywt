<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>职位详情</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">职位详情</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updateEmployee">
                <p>
                    <label>名称</label>
                    <span class="field">${position.name}&nbsp;</span>
                </p>
                <p>
                    <label>职责</label>
                    <span class="field">${position.duty}&nbsp;</span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field">${position.remark}&nbsp;</span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <input type="reset" class="reset radius2" value="返 回" onclick="javascript:history.go(-1);"/>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
</body>
</html>