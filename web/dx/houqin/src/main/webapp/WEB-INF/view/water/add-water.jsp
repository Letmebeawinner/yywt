<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加用水量</title>
    <style>
        .laydate_table {
            display: none;
        }

        #laydate_hms {
            display: none !important;
        }
    </style>
    <script type="text/javascript">
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#month',
                istoday: false,
                format: 'YYYY-MM',
                choose: function (dates) {
                    getPrevRead()
                }
            });

            jQuery("#typeId").change(function () {
                getPrevRead()
            })
        });

        function getPrevRead() {
            var typeId = jQuery("#typeId").val();
            var month = jQuery("#month").val();
            if (typeId === "0") {
                alert("请选择用水区域")
                jQuery("input:text").val('');
                return
            }

            if (!month){
                return
            }

            jQuery.ajax({
                url: "${ctx}/admin/houqin/prevWater.json",
                data: {
                    "dates": month,
                    "typeId": typeId
                },
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code==="0") {
                        jQuery("#preRead").val(result.data);
                        if(result.data==null){
                            alert("");
                        }
                        calculation();//重新计算
                    } else {
                        alert(result.message);
                        jQuery("input:text").val('');
                    }
                }
            });
        }

        //计算
        function calculation() {
            //上期读数
            var previousDegrees = jQuery("#preRead").val();
            //本期读数
            var currentDegrees = jQuery("#curRead").val();

            if (previousDegrees !== "" && currentDegrees !== "" && parseFloat(currentDegrees)>=parseFloat(previousDegrees)) {//验证是否可以计算用电量
                //用电量
                var tunnage = (parseFloat(currentDegrees) - parseFloat(previousDegrees)).toFixed(2);
                jQuery('#tunnage').html(tunnage);
                jQuery('#tunnageInp').val(tunnage);

                var price=jQuery("#price").val();
                if(price!=""){
                    var eleFee=(parseFloat(tunnage) * parseFloat(price)).toFixed(2);
                    jQuery('#eleFeeInp').val(eleFee);
                    jQuery('#eleFee').html(eleFee);
                }else {
                    jQuery('#eleFeeInp').val("");
                    jQuery('#eleFee').html("");
                }
            } else {
                jQuery('#tunnageInp').val("");
                jQuery('#tunnage').html("");
                jQuery('#eleFeeInp').val("");
                jQuery('#eleFee').html("");
            }
        }

        function clearNoNum(obj) {
            obj.value = obj.value.replace(/[^\d.]/g, "");  //清除“数字”和“.”以外的字符
            obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的
            obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
            obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');//只能输入两个小数
            if (obj.value.indexOf(".") < 0 && obj.value != "") {//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
                obj.value = parseFloat(obj.value);
            }
        }

        function addFormSubmit() {
            var typeId=jQuery("#typeId").val();
            if(typeId==null|| typeId==0){
                alert("请选择用水区域");
                return;
            }
            var tunnage = jQuery("#tunnageInp").val();
            if (tunnage == null || tunnage == '') {
                alert("用水量不能为空!");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/addSaveWater.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllWater.json";
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">添加用水量</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于添加用水量；<br>
            2.添加用水量：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；添加用水量<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">

                <p>
                    <label><em style="color: red;">*</em>用水区域</label>
                    <span class="field">
                         <select name="water.waterType" class="longinput" id="typeId">
                               <option value="0">--请选择--</option>
                                 <c:forEach items="${waterTypeList}" var="waterType">
                                     <option value="${waterType.id}">${waterType.type}</option>
                                 </c:forEach>
                         </select>
                    </span>
                </p>

                <p>
                    <label>月份</label>
                    <span class="field">
                        <input type="text" name="water.monthTime" class="longinput" readonly id="month"/>
                    </span>
                </p>

                <p>
                    <label>上期读数</label>
                    <span class="field">
                        <input type="text" name="water.preRead" class="longinput" id="preRead"
                               onkeyup="clearNoNum(this);calculation();" maxlength="8"/>
                    </span>
                </p>

                <p>
                    <label>本期读数</label>
                    <span class="field">
                        <input type="text" name="water.curRead" class="longinput" id="curRead"
                               onkeyup="clearNoNum(this);calculation();" maxlength="8"/>
                    </span>
                </p>
                <p>
                    <label>单价</label>
                    <span class="field">
                        <input type="text" onkeyup="clearNoNum(this);calculation();" name="water.price" class="longinput" id="price" maxlength="8"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>用水量</label>
                    <span class="field">
                        <span id="tunnage"></span>&nbsp;
                        <input type="hidden" name="water.tunnage" class="longinput"   id="tunnageInp"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>水费</label>
                    <span class="field">
                        <span id="eleFee"></span>&nbsp;
                        <input type="hidden" name="water.eleFee"  class="longinput"   id="eleFeeInp"/>
                    </span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field">
                        <textarea cols="80" rows="5" name="water.context" class="longinput"></textarea>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit(); return false">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>