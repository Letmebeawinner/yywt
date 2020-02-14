<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>资产出库详情</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">资产出库详情</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于查看固定资产物品出库信息；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label>出库部门</label>
                    <span class="field">
                        ${obj.departmentName}&nbsp;
                    </span>
                </p>
                <p>
                    <label>出库编号</label>
                    <span class="field">
                        ${obj.serialNo}&nbsp;
                    </span>
                </p>
                <p>
                    <label>商品名称</label>
                    <span class="field">
                        ${obj.outboundItemName}&nbsp;
                    </span>
                </p>

                <p>
                    <label>出库数量</label>
                    <span class="field">
                        ${obj.outboundNumber}&nbsp;
                    </span>
                </p>

                <p>
                    <label>经办人</label>
                    <span class="field">
                        ${obj.manager}&nbsp;
                    </span>
                </p>

                <p>
                    <label>出库人</label>
                    <span class="field">
                        ${obj.outboundPerson}&nbsp;
                    </span>
                </p>

                <p>
                    <label>备注</label>
                    <span class="field">
                      ${obj.context}&nbsp;
                    </span>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>