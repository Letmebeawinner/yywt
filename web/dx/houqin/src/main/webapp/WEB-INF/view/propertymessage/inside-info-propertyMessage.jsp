<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>资产入库详细信息</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">资产入库详细信息</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于查看资产信息；<br>
        </div>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label>资产名称</label>
                    <span class="field">
                        ${propertyMessage.name}&nbsp;
                    </span>
                </p>
                <p>
                    <label>规格型号</label>
                    <span class="field">
                       ${propertyMessage.product}&nbsp;
                    </span>
                </p>

                <p>
                    <label>数量</label>
                    <span class="field">
                        ${propertyMessage.amount}&nbsp;
                    </span>
                </p>

                <p>
                    <label>计量单位</label>
                    <span class="field">
                       ${propertyMessage.unit}&nbsp;
                    </span>
                </p>
                <p>
                    <label>金额</label>
                    <span class="field">
                        ${propertyMessage.price}&nbsp;
                    </span>
                </p>
                <p>
                    <label>入库单号</label>
                    <span class="field">
                        ${propertyMessage.insideNumber}&nbsp;
                    </span>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>