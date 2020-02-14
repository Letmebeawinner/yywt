<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>入库</title>
    <script type="text/javascript" src="${ctx}/static/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        jQuery(function () {

        });

        function num(obj) {
            obj.value = obj.value.replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
            obj.value = obj.value.replace(/^\./g, ""); //验证第一个字符是数字
            obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个, 清除多余的
            obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
            obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
        }

        function save() {
            if (jQuery("input[name='lot.lotPrice']").val() == '') {
                jAlert("价格不能为空", "提示", function () {
                });
                return false
            }

            var messStockIdVal = jQuery("input[name='lot.messStockId']").val()
            if (messStockIdVal == '') {
                jAlert("货物不存在, 请返回上一级选择", "提示", function () {
                });
                return false
            }

            var lotAmountVal = jQuery("input[name='lot.lotAmount']").val()
            if (!Number(lotAmountVal)) {
                jAlert("请输入正确的数量", "提示", function () {
                });
                return false
            }

            var params = jQuery("#saveForm").serialize();
            jQuery.ajax({
                url: '/admin/houqin/lot/save.json',
                data: params,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        window.location.href = "/admin/houqin/lot/list/" + messStockIdVal + ".json";
                    } else {
                        jAlert(result.message, '提示', function () {
                        });
                    }
                },
                error: function (e) {
                    jAlert('添加失败', '提示', function () {
                    });
                }
            });
        }

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">${messStock.name}入库</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来入库<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="saveForm">

                <p>
                    <label><em style="color: red;">*</em>价格</label>
                    <span class="field">
                        <input type="text" name="lot.lotPrice" placeholder="最多两位小数" onkeyup="num(this)" class="longinput"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>数量</label>
                    <span class="field">
                        <input type="text" name="lot.lotAmount" placeholder="请输入数字" class="longinput"/>
                    </span>
                </p>
                <input type="hidden" value="${messStockId}" name="lot.messStockId">
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="save();return false;">添 加</button>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div>
</div>
</body>
</html>