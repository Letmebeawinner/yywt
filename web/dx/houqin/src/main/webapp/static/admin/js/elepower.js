var typeId = 0;
var secTypeId = 0;
jQuery(function () {
    jQuery("select[name='electricity.typeId']").change()
    laydate.skin('molv');
    laydate({
        elem: '#monthTime',
        istoday: false,
        format: 'YYYY-MM'
    });
})
function addFormSubmit() {
    var elePowerId = jQuery("#elePowerId").val();
    //上期读数
    var previousDegrees = jQuery.trim(jQuery("#previousDegrees").val());
    if (!previousDegrees) {
        alert("上期读数不能为空");
        return;
    }
    //本期读数
    var currentDegrees = jQuery.trim(jQuery("#currentDegrees").val());
    if (!currentDegrees) {
        alert("本期读数不能为空");
        return;
    }
    //单价
    var price = jQuery.trim(jQuery("#price").val());
    if (!price) {
        alert("单价不能为空");
        return;
    }
    //倍率
    var rate = jQuery.trim(jQuery("#rate").val());
    if (!rate) {
        alert("倍率不能为空");
        return;
    }
    if (elePowerId != null) {
        jQuery.ajax({
            url: "/admin/houqin/doUpdElePower.json",
            data: jQuery("#addFormSubmit").serialize(),
            type: "post",
            dataType: "json",
            success: function (result) {
                alert(result.message);
                if (result.code === "0") {
                    window.location.href = "/admin/houqin/elePowerList.json";
                }
            }
        });
    } else {
        jQuery.ajax({
            url: "/admin/houqin/doSaveElePower.json",
            data: jQuery("#addFormSubmit").serialize(),
            type: "post",
            dataType: "json",
            success: function (result) {
                alert(result.message);
                if (result.code === "0") {
                    window.location.href = "/admin/houqin/elePowerList.json";
                }
            }
        });
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

/**
 * 二级联动
 * @param obj 下拉列表
 */
function queryPreviousDegrees(obj) {
    var param = jQuery(obj).find("option:selected").attr("value")
    secTypeId = param;
    typeId = param;
    jQuery.getJSON("/admin/houqin/findSecByParentType.json",
        {"typeId": param},
        function (result) {
            var $classStr = jQuery("#secTypeId");
            $classStr.empty()
            if (result.data.length > 0) {
                $classStr.append("<option value='0'>请选择二级区域</option>")
                jQuery.each(result.data, function () {
                    $classStr.append("<option value='" + this.id + "'>" + this.typeName + "</option>")
                })
            } else {
                jQuery("#secTypeId").onclick = null
                $classStr.append("<option value='0'>暂无二级区域</option>")
            }
        })
    getLastPeriod(typeId, 0);
}
function findClassTwo(obj) {
    var secTypeId = jQuery(obj).find("option:selected").attr("value")
    getLastPeriod(typeId, secTypeId);
}
/**
 * 查询上期指数
 * @param typeId
 * @param secTypeId
 */
function getLastPeriod(typeId, secTypeId) {
    var monthTime = jQuery("#monthTime").val();
        if ("" != monthTime) {
            var str_before = monthTime.split("-")[0];
            var str_after = monthTime.split("-")[1];
            if ((parseInt(str_after) - 1) < 10) {
                str_after = "-0" + (parseInt(str_after) - 1)
            } else {
                str_after = "-" + (parseInt(str_after) - 1)
            }
            monthTime = str_before + str_after
        }
        jQuery.ajax({
            url: "/admin/houqin/query/previousDegrees.json",
            data: {"typeId": typeId, "secTypeId": secTypeId, "monthTime": monthTime},
            type: "post",
            dataType: "json",
            success: function (result) {
                if ('0' == result.code) {
                    jQuery("#previousDegrees").val(result.data)
                    /* if ("" == result.data && 0 != typeId) {
                     alert("没有上期指数，请手动填写");
                     }*/
                    calculation();//重新计算
                }
            }
        });
}
//计算
function calculation() {
    //上期读数
    var previousDegrees = jQuery("#previousDegrees").val();
    //本期读数
    var currentDegrees = jQuery("#currentDegrees").val();
    //单价
    var price = jQuery("#price").val();
    //倍率
    var rate = jQuery("#rate").val();

    //验证是否可以计算用电量
    if (previousDegrees !== "" && currentDegrees !== "" && rate !== "" && parseFloat(currentDegrees)>=parseFloat(previousDegrees)) {
        //用电量
        var degrees = parseFloat(currentDegrees) - parseFloat(previousDegrees);
        degrees = degrees.toFixed(2) * rate;
        jQuery('#degrees').html(degrees);
        jQuery('#degreesInp').val(degrees);
        //验证是否可以计算电费
        if (price !== "") {
            var eleFee = degrees * parseFloat(price);
            jQuery('#eleFee').html(eleFee.toFixed(2));
            jQuery('#eleFeeInp').val(eleFee);
        } else {
            jQuery('#eleFee').html("");
            jQuery('#eleFeeInp').val("");
        }
    } else {
        jQuery('#degrees').html("");
        jQuery('#degreesInp').val("");
        jQuery('#eleFee').html("");
        jQuery('#eleFeeInp').val("");
    }
}
