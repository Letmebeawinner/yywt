<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>固定资产添加出库</title>
    <script type="text/javascript">
        function addFormSubmit() {
            // 出库数量
            var outboundNumber = jQuery("input[name='outOfPropertyMessage.outboundNumber']").val();
            // 剩余数量
            var amount = jQuery("input[name='amount']").val()

            var manager = jQuery("input[name='outOfPropertyMessage.manager']").val()
            var outboundPerson = jQuery("input[name='outOfPropertyMessage.outboundPerson']").val()

            if (manager.length < 1) {
                alert("请输入经办人")
                return false;
            }

            if (outboundPerson.length < 1) {
                alert("请输入出库人")
                return false;
            }

            if (outboundNumber == null || outboundNumber == '') {
                alert("出库数量不能为空!");
                return;
            }

            if (parseInt(outboundNumber)  > parseInt(amount)) {
                alert("出库数量不能大于总库存量!");
                return;
            }

            // 参数
            var params = jQuery("#addFormSubmit").serialize()
            jQuery.ajax({
                url: "${ctx}/admin/houqin/saveOutPM.json",
                data: params,
                type: "post",
                dataType: "json",
                success: function (result) {
                    alert(result.message);
                    if (result.code === "0") {
                        window.location.href = "/admin/houqin/listOutPM.json";
                    }
                }
            });
        }

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">固定资产物品出库</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于固定资产物品出库信息；<br>
            2.根据要求填写固定资产出库信息；<br>
            3.提交保存：点击<span style="color:red">提交保存</span>修改固定资产库存信息；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>商品名称</label>
                    <span class="field">
                        <input type="text" name="outOfPropertyMessage.outboundItemName"
                               class="longinput"  value="${propertyMessage.name}"
                               readonly="readonly"/>
                        <%--商品id--%>
                        <input type="hidden" name="outOfPropertyMessage.outboundItemId" value="${propertyMessage.id}">
                    </span>
                </p>

                <p>
                    <label>出库部门</label>
                    <span class="field">
                        <select name="outOfPropertyMessage.departmentName">
                            <option>--请选择部门--</option>
                            <c:forEach items="${departmentList}" var="department">
                                <option value="${department.departmentName}">${department.departmentName}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>出库数量</label>
                    <span class="field">
                        <input type="text" name="outOfPropertyMessage.outboundNumber"
                               onkeyup="if(/\D/.test(this.value)){alert('只能输入数字');this.value='';}"
                               class="longinput" />
                        库存剩余:<font style="color: red">${propertyMessage.amount}</font>
                        <input type="hidden" name="amount" value="${propertyMessage.amount}"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>经办人</label>
                    <span class="field">
                        <input type="text" name="outOfPropertyMessage.manager" class="longinput"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>出库人</label>
                    <span class="field">
                        <input type="text" name="outOfPropertyMessage.outboundPerson" class="longinput"/>
                    </span>
                </p>

                <p>
                    <label>备注</label>
                    <span class="field">
                      <textarea cols="80" rows="5" name="outOfPropertyMessage.context" class="longinput"></textarea>
                    </span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>