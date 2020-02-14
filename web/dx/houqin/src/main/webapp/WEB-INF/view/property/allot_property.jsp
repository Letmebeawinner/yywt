<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>资产调拨</title>
    <script type="text/javascript">
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#allotTime',
                format: 'YYYY-MM-DD hh:mm:ss'
            });

            // 对Date的扩展，将 Date 转化为指定格式的String
            // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
            // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
            Date.prototype.Format = function (fmt) { //author: meizz
                var o = {
                    "M+": this.getMonth() + 1, //月份
                    "d+": this.getDate(), //日
                    "H+": this.getHours(), //小时
                    "m+": this.getMinutes(), //分
                    "s+": this.getSeconds(), //秒
                    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                    "S": this.getMilliseconds() //毫秒
                };
                if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
                for (var k in o)
                    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                return fmt;
            }

            var mydate = new Date().Format("yyyy-MM-dd HH:mm:ss");
            jQuery("#handleTime").val(mydate);


        });

        function addFormSubmit() {
            var typeName = jQuery("#typeName").val();
            if (typeName == null || typeName == '') {
                alert("名称不能为空!");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/addPropertyAllot.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/propertyAllotList.json";
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }
    </script>

    <script type="text/javascript">
        var data = data;
        var functionsIds;

        function addproperty() {
            jQuery.ajax({
                type: "post",
                url: "/admin/houqin/selectPropertyList.json",
                data: data,
                dataType: "text",
                async: false,
                success: function (result) {
                    jQuery.alerts._show('选择资产列表', null, null, 'addCont', function (confirm) {
                        if (confirm) {
                            if (functionsIds) {
                                jQuery.ajax({
                                    type: "post",
                                    url: "/admin/houqin/searchProperty.json",
                                    data: {'id': functionsIds},
                                    dataType: "json",
                                    success: function (result) {
                                        if (result.code == "0") {
                                            var name = result.data.name;
                                            var id = result.data.id;
                                            var typeId=result.data.propertyId;
                                            jQuery("#propertyName").html(name);
                                            jQuery("#propertyId").val(id);
                                            jQuery("#propertyTypeId").val(typeId);
                                        } else {
                                            alert(result.message);
                                        }
                                    }
                                });
                            } else {
                                alert("没有选择！");
                            }

                        }
                    });
                    jQuery('#popup_message').html(result);
                    // 修改弹窗的位置。距窗口上边距150px，左边距30%.
                    jQuery('#popup_container').css({
                        top: 50,
                        left: '20%',
                        overflow: 'hidden'
                    });
                    jQuery('#popup_container').css("max-height", "600px");
                    jQuery('#popup_message').css("max-height", "450px");
                    jQuery('#popup_message').css('overflow-y', 'scroll');
                }
            });
        }
    </script>

</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">资产调拨</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于添加资产分类；<br>
            2.添加资产分类：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；添加资产分类<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <input type="hidden" value="${userId}" name="propertyAllot.allotId"/>
                    <label><em style="color: red;">*</em>调入管理员</label>
                    <span class="field">
                        <input type="text" name="propertyAllot.name" class="longinput" id="typeName"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>选择资产</label>
                <td>
                    <input type="hidden" value="" id="propertyId" name="propertyAllot.propertyId">
                    <input type="hidden" value="" id="propertyTypeId" name="propertyAllot.propertyTypeId">
                    <span class="field">
                        <a href="javascript:addproperty()" class="stdbtn btn_orange">选择资产</a><font id="propertyName"></font></span>
                </td>
                </p>

                <p>
                    <label><em style="color: red;">*</em>调入日期</label>
                    <span class="field">
                        <input type="text" name="propertyAllot.allotTime" class="longinput" id="allotTime" readonly/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>处理人</label>
                    <span class="field">
                        <input type="text"  value="${userName}" class="longinput" readonly/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>处理时间</label>
                    <span class="field">
                        <input type="text" name="propertyAllot.handleTime" class="longinput" value="0" id="handleTime"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>调拨说明</label>
                    <span class="field">
                       <textarea rows="10" cols="5" class="longinput" name="propertyAllot.allotContext"></textarea>
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