<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>查看入库记录</title>
    <script type="text/javascript">
        /**
         * 返回前一个页面
         */
        function comeback() {
            window.history.go(-1);
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">查看入库记录</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于查看物品入库信息；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label>经办人</label>
                    <span class="field">
                        ${stock.operatorName} &nbsp;
                    </span>
                </p>
                <p>
                    <label>库房名称</label>
                    <span class="field">
                        ${stock.stockHouseName} &nbsp;
                    </span>
                </p>
                <p>
                    <label>入库单号</label>
                    <span class="field">
                        ${stock.billNum} &nbsp;
                    </span>
                </p>
                <p>
                    <label>商品编号</label>
                    <span class="field">
                        ${stock.goodsCode}&nbsp;
                    </span>
                </p>
                <p>
                    <label>商品名称</label>
                    <span class="field">
                        ${stock.goodsName}&nbsp;
                    </span>
                </p>
                <p>
                    <label>入库数量</label>
                    <span class="field">
                        ${stock.num}&nbsp;
                    </span>
                </p>
                <p>
                    <label>入库单位</label>
                    <span class="field">
                        ${stock.unitName}&nbsp;
                    </span>
                </p>
                <p>
                    <label>价格</label>
                    <span class="field">
                        ${stock.price}&nbsp;元
                    </span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field">
                    ${stock.note}&nbsp;
                    </span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="comeback(); return false">返回</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>